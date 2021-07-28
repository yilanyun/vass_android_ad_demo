package com.yilan.sdk.sdkdemo.ad.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yilan.sdk.common.util.ToastUtil;
import com.yilan.sdk.data.entity.IAdEngine;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.ad.BaseAdActivity;
import com.yilan.sdk.ylad.YLAdListener;
import com.yilan.sdk.ylad.constant.YLAdConstants;
import com.yilan.sdk.ylad.engine.IYLAdEngine;
import com.yilan.sdk.ylad.manager.YLAdManager;

import java.util.ArrayList;

/**
 * 信息流广告
 * <p>
 * 信息流广告适用于feed流中插入广告，广告的插入首位置&广告的插入间隔等规则
 * 可以再后台配置，配置过程中如有任何疑问可以与我们联系
 * <p>
 * 文档地址：https://docs.vaas.cn/feed/client/android/ad_sdk
 */
public class FeedAdActivity extends BaseAdActivity {
    private RecyclerView recyclerView;
    private ArrayList arrayList;
    private YLAdManager adManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_ad);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();

        //创建广告管理器
        adManager = YLAdManager.with(this);
        adManager.setAdListener(new YLAdListener() {
            @Override
            public void onSuccess(String adType, int source, String reqId, String pid) {
                super.onSuccess(adType, source, reqId, pid);
            }

            @Override
            public void onError(String adType, int source, String reqId, int code, String msg, String pid) {
                super.onError(adType, source, reqId, code, msg, pid);
                ToastUtil.show(context, "广告请求失败: " + msg);
            }
        });
        //模拟原始列表的数据
        for (int i = 0; i < 30; i++) {
            arrayList.add(i + "");
        }

        //通过广告管理器，将广告插入到列表数据中
        adManager.insertEngineByName(YLAdConstants.AdName.FEED, arrayList);

        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new FeedAdapter(arrayList);
        recyclerView.setAdapter(adapter);

    }


    public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public static final int TYPE_DATA = 1;//数据类型
        public static final int TYPE_AD = 2;//广告类型
        private ArrayList arrayList;

        public FeedAdapter(ArrayList arrayList) {
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
            if (type == TYPE_DATA) {
                return new FeedDataViewHolder(viewGroup.getContext(), viewGroup);
            } else {
                //如果是广告类型的数据，则创建广告类型的holder
                return new FeedAdViewHolder(viewGroup.getContext(), viewGroup);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder instanceof FeedAdViewHolder) {
                ((FeedAdViewHolder) viewHolder).onBindViewHolder((IAdEngine) arrayList.get(i));
            } else if (viewHolder instanceof FeedDataViewHolder) {
                ((FeedDataViewHolder) viewHolder).onBindViewHolder(arrayList.get(i));
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (arrayList.get(position) instanceof IYLAdEngine) {
                return TYPE_AD;
            } else {
                return TYPE_DATA;
            }
        }

        @Override
        public int getItemCount() {
            if (arrayList != null) {
                return arrayList.size();
            }
            return 0;
        }
    }
}
