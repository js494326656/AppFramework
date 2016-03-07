package com.jsware.netedgetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.edge.proxy.NetProxy;
import com.edge.reader.NetBuilder;
import com.jsware.R;


public class WelcomeActivity extends AppCompatActivity implements IWelcomeEdge {

    public static final String TAG = "WelcomeActivity";

    NetProxy mProxy;
    IWelcomeEdge mNet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mProxy = new NetProxy(new NetBuilder().register(new NetBuilder.NetEdge.INetListener() {
            @Override
            public void netBegin() {
                Log.i(TAG, "netBegin!!");
            }

            @Override
            public void netEnd() {
                Log.i(TAG, "netEnd!!");
            }
        }).build());
        mNet = (IWelcomeEdge) mProxy.createProxyInstance(this);
        mNet.welcomeReq();
    }

    @Override
    public void welcomeReq() {
        // TODO: 2016/3/7 网络请求
        mNet.welcomeRes("response data");
    }

    @Override
    public void welcomeRes(String response) {
        // TODO: 2016/3/7 网络应答
    }

    @Override
    public void netErr() {
        // TODO: 2016/3/7 网络失败
    }
}
