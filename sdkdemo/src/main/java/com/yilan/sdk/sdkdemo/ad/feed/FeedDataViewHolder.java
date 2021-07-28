package com.yilan.sdk.sdkdemo.ad.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.uibase.ui.adapter.viewholder.ViewHolder;
import com.yilan.sdk.ylad.engine.IYLAdEngine;

/**
 * 数据列表的holder
 */
class FeedDataViewHolder extends ViewHolder {

    private TextView textView;
    public FeedDataViewHolder(Context context, ViewGroup parent) {
        super(context, LayoutInflater.from(context).inflate(R.layout.holder_feed_data, parent, false));
        initView();
    }

    protected void initView() {
        textView = itemView.findViewById(R.id.feed_text);
    }

    public void onBindViewHolder(Object data) {
        textView.setText((String) data);
    }
}
