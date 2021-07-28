package com.yilan.sdk.sdkdemo.ad.reward;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yilan.sdk.common.util.ToastUtil;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.ad.BaseAdActivity;
import com.yilan.sdk.sdkdemo.util.Utils;
import com.yilan.sdk.ylad.YLAdListener;
import com.yilan.sdk.ylad.constant.YLAdConstants;
import com.yilan.sdk.ylad.engine.IYLAdEngine;
import com.yilan.sdk.ylad.entity.AdState;
import com.yilan.sdk.ylad.manager.YLAdManager;

/**
 * 激励视频广告
 * <p>
 * 1.广告支持两种展现方式：
 * a.直接请求并展示，无需做任何操作，请求完成会自动渲染，但是有一定的卡顿风险
 * b.预请求广告，请求完成后选择合适的时机渲染，渲染速度较快
 * 2.单次广告不支持多次展现。下次展现需要重新请求广告视频素材
 * 3.需要关注广告请求失败的回调onError，并做异常流程处理
 * <p>
 * 文档地址：https://docs.vaas.cn/feed/client/android/ad_sdk
 */
public class RewardVideoActivity extends BaseAdActivity {

    //广告操作接口类
    private IYLAdEngine mRewardEngine;
    private LinearLayout mContainer;
    private TextView mTextLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video);
        mContainer = findViewById(R.id.container);
        mTextLog = findViewById(R.id.text_log);
        //使用广告请求管理类YLAdManager创建广告请求管理类
        mRewardEngine = YLAdManager.with(this).getEngine(YLAdConstants.AdName.REWARD_VIDEO);
        //设置广告的监听
        mRewardEngine.setAdListener(new YLAdListener() {
            @Override
            public void onSuccess(String adType, int source, String reqId, String pid) {
                super.onSuccess(adType, source, reqId, pid);
                succeed = true;
                dialog.dismiss();
                log(mTextLog, "广告请求成功：来源：" + Utils.getSourceName(source));
            }

            @Override
            public void onError(String adType, int source, String reqId, int code, String msg, String pid) {
                super.onError(adType, source, reqId, code, msg, pid);
                succeed = false;
                dialog.dismiss();
                log(mTextLog, msg);
            }


            @Override
            public void onClick(String adType, int source, String reqId, String pid) {
                log(mTextLog, "广告点击");
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
                log(mTextLog, "广告为空，请先在一览云后台配置该广告！");
            }
        });
    }

    /**
     * 直接请求并展示
     *
     * @param view
     */
    public void requestRewardAndShow(View view) {
        ToastUtil.show(RewardVideoActivity.this, "开始请求");
        log(mTextLog, "开始请求");
        dialog.show();
        mRewardEngine.reset();
        mRewardEngine.request(mContainer);
    }

    /**
     * 激励视频广告的预请求
     */
    public void requestReward(View view) {
        ToastUtil.show(RewardVideoActivity.this, "开始请求");
        log(mTextLog, "开始请求");
        dialog.show();
        mRewardEngine.reset();
        mRewardEngine.preRequest(mContainer);
    }

    /**
     * 激励视频渲染，配合mRewardEngine.preRequest(mContainer)使用
     * 需要注意的是，渲染的调用需要再广告请求成功以后调用
     */
    public void renderReward(View view) {
        if (succeed && mRewardEngine.getState() == AdState.SUCCESS) {
            log(mTextLog, "激励视频广告展现");
            mRewardEngine.renderAd();
        } else {
            log(mTextLog, "还没有请求激励视频或请求失败，请尝试重新请求");
        }
    }

}