package com.yilan.sdk.sdkdemo.ad.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yilan.sdk.data.entity.IAdEngine;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.uibase.ui.adapter.viewholder.ViewHolder;

/**
 * 广告类型的列表holder，只有一个布局用于承载feed广告
 */
class FeedAdViewHolder extends ViewHolder {
    private FrameLayout adContainer;
    public FeedAdViewHolder(Context context, ViewGroup parent) {
        super(context, LayoutInflater.from(context).inflate(R.layout.holder_feed_ad, parent, false));
        initView();
    }

    protected void initView() {
        adContainer = itemView.findViewById(R.id.ad_container);
    }

    public void onBindViewHolder(IAdEngine engine) {
        if (engine != null) {
            engine.request(adContainer);
        }
    }
}
