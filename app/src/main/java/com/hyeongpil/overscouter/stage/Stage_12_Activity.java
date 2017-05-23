package com.hyeongpil.overscouter.stage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hyeongpil.overscouter.BaseActivity;
import com.hyeongpil.overscouter.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hyeongpil on 2017-01-02.
 */

public class Stage_12_Activity extends BaseActivity {
    final static String TAG = Stage_12_Activity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        container.setLayoutResource(R.layout.activity_stage12);
        View containView = container.inflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_one)
    void oneClick(){
        addScore(0);
        nextStage();
    }

    @OnClick(R.id.tv_two)
    void twoClick(){
        addScore(20);
        nextStage();
    }

    @OnClick(R.id.tv_three)
    void threeClick(){
        addScore(10);
        nextStage();
    }

    @OnClick(R.id.tv_four)
    void fourClick(){
        addScore(25);
        nextStage();
    }

    @OnClick(R.id.tv_five)
    void fiveClick(){
        addScore(30);
        nextStage();
    }

    @OnClick(R.id.iv_back)
    void backClick(){
        startActivity(new Intent(this,Stage_11_Activity.class));
        backClicked();
    }

    private void nextStage(){
        startActivity(new Intent(this,Stage_13_Activity.class));
        finish();
        nextAnim();
    }

}
