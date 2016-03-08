package com.jsware.eventtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.eventpass.EventManager;
import com.eventpass.annotation.Receiver;
import com.jsware.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CallerActivity extends AppCompatActivity {

    public static final String TAG = "CallerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller);
        ButterKnife.bind(this);
        EventManager.register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnClick(R.id.caller)
    public void caller() {
        startActivity(new Intent(this,ServerActivity.class));
        EventManager.subcribe(this,EventConstant.CALL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventManager.unRegister(this);
    }

    @Receiver(caller = EventConstant.CALL,server = EventConstant.SERVER)
    public void EventHandle(int resultCode,Intent data){
        if (data != null) {
            Log.i(TAG, data.getStringExtra("data"));
            Log.i(TAG, "result code:" + resultCode);
        }
    }
}
