package com.yilan.sdk.sdkdemo.ad.splash;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.ad.BaseAdActivity;
import com.yilan.sdk.sdkdemo.util.Utils;
import com.yilan.sdk.ylad.YLAdListener;
import com.yilan.sdk.ylad.constant.YLAdConstants;
import com.yilan.sdk.ylad.engine.IYLAdEngine;
import com.yilan.sdk.ylad.manager.YLAdManager;

/**
 * 开屏广告
 * <p>
 * 开屏广告建议在闪屏页进行展示，开屏广告的宽度和高度取决于容器的宽高，都是会撑满广告容器；
 * 开屏广告的高度必须大于等于屏幕高度（手机屏幕完整高度，包括状态栏之类）的75%，推荐设置为全屏，
 * 否则可能会影响收益计费（广点通的开屏甚至会影响跳过按钮的回调）。
 * <p>
 * 文档地址：https://docs.vaas.cn/feed/client/android/ad_sdk
 */
public class SplashAdActivity extends BaseAdActivity {

    private IYLAdEngine mSplashAd;
    private ViewGroup mContainer;
    private TextView mTextLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ad);
        mContainer = findViewById(R.id.root_container);
        mTextLog = findViewById(R.id.text_log);
        //创建开屏广告的实例
        mSplashAd = YLAdManager.with(this).getEngine(YLAdConstants.AdName.SPLASH);
        //设置监听，建议您在开屏广告倒计时结束（回调到onTimeOver）
        //或者在开屏广告用户点击跳过（回调到onSkip）里跳过闪屏界面
        mSplashAd.setAdListener(new YLAdListener() {
            @Override
            public void onSuccess(String adType, int source, String reqId, String pid) {
                super.onSuccess(adType, source, reqId, pid);
                dialog.dismiss();
                log(mTextLog, "广告请求成功：来源：" + Utils.getSourceName(source));
            }

            @Override
            public void onError(String adType, int source, String reqId, int code, String msg, String pid) {
                super.onError(adType, source, reqId, code, msg, pid);
                dialog.dismiss();
                log(mTextLog, "广告请求失败：" + msg);
            }


            @Override
            public void onClick(String adType, int source, String reqId, String pid) {
                log(mTextLog, "广告点击");
            }

            @Override
            public void onSkip(String adType, int source, String reqId, String pid) {
                log(mTextLog, "广告跳过");
            }

            @Override
            public void onTimeOver(String adType, int source, String reqId, String pid) {
                log(mTextLog, "倒计时完毕");
            }

            @Override
            public void onVideoStart(String adType, int source, String reqId, String pid) {
                log(mTextLog, "视频播放开始");
            }

            @Override
            public void onAdEmpty(String adType, int source, String reqId, String pid) {
                dialog.dismiss();
                log(mTextLog, "广告为空，请先在一览云后台配置该广告");
            }
        });
    }

    /**
     * 请求并展示
     */
    public void requestSplashAndShow(View view) {
        log(mTextLog, "开始请求");
        dialog.show();
        mSplashAd.reset();
        mSplashAd.request(mContainer);
    }

}