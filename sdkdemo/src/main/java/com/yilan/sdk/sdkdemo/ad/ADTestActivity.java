package com.yilan.sdk.sdkdemo.ad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yilan.sdk.sdkdemo.BuildConfig;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.ad.banner.BannerAdActivity;
import com.yilan.sdk.sdkdemo.ad.draw.DrawAdActivity;
import com.yilan.sdk.sdkdemo.ad.feed.FeedAdActivity;
import com.yilan.sdk.sdkdemo.ad.interstitial.InterstitialAdActivity;
import com.yilan.sdk.sdkdemo.ad.reward.RewardVideoActivity;
import com.yilan.sdk.sdkdemo.ad.splash.SplashAdActivity;

public class ADTestActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ADTestActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_test);
        TextView sdkVersion = findViewById(R.id.sdk_version);
        sdkVersion.setText(String.format("聚合广告SDK\n%s  ", BuildConfig.VERSION_NAME));
    }

    public void testSplash(View view) {
        startAct(SplashAdActivity.class);
    }

    public void testReward(View view) {
        startAct(RewardVideoActivity.class);
    }

    public void testBanner(View view) {
        startAct(BannerAdActivity.class);
    }

    public void testFeedList(View view) {
        startAct(FeedAdActivity.class);
    }

    public void testInterstitial(View view) {
        startAct(InterstitialAdActivity.class);
    }

    public void testDrawAd(View view) {
        startAct(DrawAdActivity.class);
    }

    private void startAct(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

}