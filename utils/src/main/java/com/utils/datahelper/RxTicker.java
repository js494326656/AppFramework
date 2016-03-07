package com.utils.datahelper;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 1 on 2016/3/7.
 */
public class RxTicker {

    public static <T> Observable<T> tick(Observable<T> src){
        return src.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .delay(5, TimeUnit.SECONDS);
    }

}
