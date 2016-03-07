package com.jsware.tickertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jsware.R;
import com.utils.datahelper.RxTicker;

import rx.Observable;
import rx.Subscriber;

public class TickerActivity extends AppCompatActivity {

    public static final String TAG = "TickerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker);
        RxTicker.tick(Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("test");
                subscriber.onCompleted();
            }
        })).subscribe(this::handleMsg);
    }

    private void handleMsg(String msg){
        Log.i(TAG, msg);
    }
}
