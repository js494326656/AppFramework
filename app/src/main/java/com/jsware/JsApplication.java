package com.jsware;

import android.app.Application;

import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.Logger;

/**
 * Created by 1 on 2016/3/23.
 */
public class JsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLog();

    }

    private void initLog() {
        Logger.init(getPackageName())
                .methodCount(3)
                .methodOffset(2)
                .logTool(new AndroidLogTool());
    }
}
