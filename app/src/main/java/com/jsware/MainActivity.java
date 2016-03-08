package com.jsware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jsware.eventtest.CallerActivity;
import com.jsware.netedgetest.WelcomeActivity;
import com.jsware.tickertest.TickerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.event_demo)
    public void eventDemo() {
        startActivity(new Intent(this, CallerActivity.class));
    }

    @OnClick(R.id.net_edge_demo)
    public void netEdgeDemo() {
        startActivity(new Intent(this, WelcomeActivity.class));
    }

    @OnClick(R.id.ticker_demo)
    public void tickerDemo() {
       startActivity(new Intent(this, TickerActivity.class));
    }
}
