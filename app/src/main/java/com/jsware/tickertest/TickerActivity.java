package com.jsware.tickertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jsware.R;
import com.utils.datahelper.RxTicker;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class TickerActivity extends AppCompatActivity {

    public static final String TAG = "TickerActivity";
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start_ticker)
    public void startTicker() {
        subscription = RxTicker.tick(Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("test");
                subscriber.onCompleted();
            }
        })).subscribe(this::handleMsg);
    }

    @OnClick(R.id.stop_ticker)
    public void stopTicker(){
        if (subscription == null) {
            Log.e(TAG, "subscription is null");
            return;
        }
        RxTicker.cancelTick(subscription);
    }

    private void handleMsg(String msg){
        Log.i(TAG, msg);
    }
}
