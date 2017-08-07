package com.hyeongpil.overscouter.stage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.hyeongpil.overscouter.BaseActivity;
import com.hyeongpil.overscouter.R;
import com.hyeongpil.overscouter.util.GlobalApplication;
import com.hyeongpil.overscouter.model.Score;

import butterknife.ButterKnife;
import butterknife.OnClick;
import gun0912.tedadhelper.TedAdHelper;
import gun0912.tedadhelper.front.OnFrontAdListener;
import gun0912.tedadhelper.front.TedAdFront;

/**
 * Created by hyeongpil on 2017-01-02.
 */

public class Stage_13_Activity extends BaseActivity {
    final static String TAG = Stage_13_Activity.class.getSimpleName();
    private String tear;
    private double clearTime = 0;
    private boolean isClicked = false;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private String ad_config = "1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        container.setLayoutResource(R.layout.activity_stage13);
        View containView = container.inflate();
        ButterKnife.bind(this);
        isClicked = false;
        configFetch();
    }

    private void configFetch(){
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().build();
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        long cacheExpiration = 3600; // 1 hour in seconds.

        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG,"remote config success");
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            Log.e(TAG,"remote config fail");
                        }
                        ad_config = mFirebaseRemoteConfig.getString("ad_config");
                        Log.e(TAG,"adconfig ="+ad_config);
                    }
                });
    }


    @OnClick(R.id.tv_one)
    void oneClick(){
        addScore(0);
        nextStage();
    }

    @OnClick(R.id.tv_two)
    void twoClick(){
        addScore(10);
        nextStage();
    }

    @OnClick(R.id.tv_three)
    void threeClick(){
        addScore(30);
        nextStage();
    }

    @OnClick(R.id.tv_four)
    void fourClick(){
        addScore(30);
        nextStage();
    }

    @OnClick(R.id.iv_back)
    void backClick(){
        startActivity(new Intent(this,Stage_11_Activity.class));
        backClicked();
    }

    private void nextStage(){
        int score = Score.getInstance().getTotalScore();

        if(score >= 375){
            //그마
            tear = "grand";
        }else if(score < 375 && score >= 355){
            //마스터
            tear = "master";
        }else if(score < 355 && score >= 325){
            //다이아
            tear = "diamond";
        }else if (score < 325 && score >= 275){
            //플레
            tear = "platinum";
        }else if(score < 275 && score >= 200){
            //골드
            tear = "gold";
        }else if (score < 200 && score >= 110){
            //실버
            tear = "silver";
        }else if(score < 110){
            //브론즈
            tear = "bronze";
        }
        clearTime = (System.currentTimeMillis() - GlobalApplication.getInstance().getStartTime()) / 1000.0;
        Log.e(TAG,"시간차이 :"+clearTime);
        if(clearTime < 13){
            tear = "tracer"; // 다 찍은경우
        }

        Log.e(TAG,"tear :"+tear);

        showInterstitial();
    }
    private void showInterstitial() {
        if(!isClicked){
            TedAdFront.showFrontAD(this, getString(R.string.facebook_id), getString(R.string.reword_ad_unit_id), Integer.parseInt(ad_config), new OnFrontAdListener() {
                @Override
                public void onDismissed(int adType) {
                    tearIntent();
                }
                @Override
                public void onError(String errorMessage) {
                    Log.e(TAG,"ad error : "+errorMessage);
                    FirebaseCrash.report(new Exception(errorMessage));
                    tearIntent();
                }
                @Override
                public void onLoaded(int adType) {}
                @Override
                public void onAdClicked(int adType) {}
                @Override
                public void onFacebookAdCreated(com.facebook.ads.InterstitialAd facebookFrontAD) {}
            });
        }else{
            Toast.makeText(this, "측정중입니다. 잠시만 기다려주세요.", Toast.LENGTH_SHORT).show();
        }
        isClicked = true;
    }

    private void tearIntent(){
        Intent intent = new Intent(Stage_13_Activity.this,TearActivity.class);
        intent.putExtra("tear",tear);
        startActivity(intent);
        finish();
        nextAnim();
    }
}
