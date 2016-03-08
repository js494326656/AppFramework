package com.jsware.eventtest;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eventpass.EventManager;
import com.jsware.R;

import java.lang.reflect.InvocationTargetException;

public class ServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data", "server return some data");
        try {
            EventManager.post(0,EventConstant.SERVER,intent);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }
}
