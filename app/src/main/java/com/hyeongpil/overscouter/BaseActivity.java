package com.hyeongpil.overscouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewStub;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.hyeongpil.overscouter.model.Score;
import com.hyeongpil.overscouter.util.GlobalApplication;

import gun0912.tedadhelper.backpress.OnBackPressListener;
import gun0912.tedadhelper.backpress.TedBackPressDialog;

public class BaseActivity extends AppCompatActivity {
    private final String TAG = BaseActivity.class.getSimpleName();
    protected ViewStub container;
    private Score t_score = Score.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        container = (ViewStub) findViewById(R.id.container);
    }

    protected void addScore(int score){
        t_score.setTotalScore(t_score.getTotalScore()+score);
        t_score.getBeforeScore().push(score);
        Log.e(TAG,"now total :"+t_score.getTotalScore());
    }


    protected void removeScore(int score){
        t_score.setTotalScore(t_score.getTotalScore()-score);
        Log.e(TAG,"now total :"+t_score.getTotalScore());
    }

    protected void nextAnim(){
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    protected void backClicked(){
        if(!t_score.getBeforeScore().isEmpty()){
            removeScore(t_score.getBeforeScore().pop());
        }
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    @Override
    public void onBackPressed() {
        TedBackPressDialog.startFacebookDialog(this, getString(R.string.app_name), getString(R.string.facebook_nativeid), new OnBackPressListener() {
            @Override
            public void onReviewClick() {
            }

            @Override
            public void onFinish() {
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "ad error : " + errorMessage);
                FirebaseCrash.report(new Exception(errorMessage));
                finish();
            }

            @Override
            public void onLoaded(int adType) {

            }

            @Override
            public void onAdClicked(int adType) {

            }
        });
    }
}
