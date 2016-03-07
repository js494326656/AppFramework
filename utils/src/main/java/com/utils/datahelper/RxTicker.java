package com.utils.datahelper;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 1 on 2016/3/7.
 */
public class RxTicker {

    public static <T,R> Observable<T> tick(Observable<T> src){
//        return src.flatMap((Func1<? super T, ? extends Observable<? extends R>>) (Func1<T, Observable<?>>) t -> {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return Observable.just(t);
//        });

        return src.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .delay(5, TimeUnit.SECONDS).repeat();
    }

}
