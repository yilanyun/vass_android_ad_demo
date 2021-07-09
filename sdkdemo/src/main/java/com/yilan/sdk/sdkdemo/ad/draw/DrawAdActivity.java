package com.yilan.sdk.sdkdemo.ad.draw;

import android.os.Bundle;
import android.view.ViewGroup;

import com.yilan.sdk.common.util.ToastUtil;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.ad.BaseAdActivity;
import com.yilan.sdk.sdkdemo.util.Utils;
import com.yilan.sdk.ylad.YLAdListener;
import com.yilan.sdk.ylad.constant.YLAdConstants;
import com.yilan.sdk.ylad.engine.IYLAdEngine;
import com.yilan.sdk.ylad.manager.YLAdManager;

public class DrawAdActivity extends BaseAdActivity {

    IYLAdEngine drawAd;
    ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_ad);
        container = findViewById(R.id.ad_container);
        drawAd = YLAdManager.with(this).getEngine(YLAdConstants.AdName.FEED_VERTICAL);
        requestDrawAndShow();
    }

    public void requestDrawAndShow() {
        ToastUtil.show(context, "开始请求");
        dialog.show();
        drawAd.reset();
        drawAd.setAdListener(listener);
        drawAd.request(container);
    }

    YLAdListener listener = new YLAdListener() {
        @Override
        public void onSuccess(String adType, int source, String reqId, String pid) {
            super.onSuccess(adType, source, reqId, pid);
            succeed = true;
            dialog.dismiss();
            ToastUtil.show(context, "广告请求成功：来源：" + Utils.getSourceName(source));
        }

        @Override
        public void onError(String adType, int source, String reqId, int code, String msg, String pid) {
            super.onError(adType, source, reqId, code, msg, pid);
            succeed = false;
            dialog.dismiss();
            ToastUtil.show(context, "广告请求失败！");
        }


        @Override
        public void onClick(String adType, int source, String reqId, String pid) {
            ToastUtil.show(context, "广告点击");
        }

        @Override
        public void onSkip(String adType, int source, String reqId, String pid) {
            ToastUtil.show(context, "广告跳过");
        }

        @Override
        public void onTimeOver(String adType, int source, String reqId, String pid) {
            ToastUtil.show(context, "倒计时完毕");

        }

        @Override
        public void onVideoStart(String adType, int source, String reqId, String pid) {
            ToastUtil.show(context, "视频播放开始");
        }

        @Override
        public void onAdEmpty(String adType, int source, String reqId, String pid) {
            dialog.dismiss();
            ToastUtil.show(context, "广告为空，请先在一览云后台配置该广告！");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (drawAd != null) {
            drawAd.onDestroy();
        }
    }
}