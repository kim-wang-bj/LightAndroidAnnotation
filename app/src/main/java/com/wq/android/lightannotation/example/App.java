package com.wq.android.lightannotation.example;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by qwang on 2016/8/5.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
