package com.yilan.sdk.sdkdemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.squareup.leakcanary.LeakCanary;
import com.yilan.sdk.ylad.IAdExtraDataListener;
import com.yilan.sdk.ylad.YLAdInit;
import com.yilan.sdk.ylad.config.YLAdConfig;
import com.yilan.sdk.ylad.constant.YLAdConstants;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        YLAdInit.getInstance()
                .setCrashOpen(false)
                .setApplication(this)
                .setAccessKey("")
                .setAccessToken("")
                .logEnable(true)
                .build();
        YLAdConfig.getInstance().registerAdExtraDataListener(new IAdExtraDataListener() {
            @Override
            public String getExtraData(int source) {
                if (source == YLAdConstants.ALLI_TOUTIAO_STENCIL) {
                    return "穿山甲的额外数据。。。。。";
                } else if (source == YLAdConstants.ALLI_GDT_EXPRESS) {
                    return "广点通的额外数据。。。。。";
                }
                return null;
            }

            @Override
            public String getUserId(int source) {
                return null;
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
