package com.jsware.loggertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jsware.R;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoggerTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logger_test);
        ButterKnife.bind(this);
        initViews();
    }

    @OnClick(R.id.btn_click)
    public void click(View view) {
        Logger.e("you click me");
    }

    private void initViews(){
        // TODO init views
        Logger.i("initViews");
    }
}
