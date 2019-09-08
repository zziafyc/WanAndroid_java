package com.zziafyc.wanandroid.adapter.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhouwei.mzbanner.holder.MZViewHolder;
import com.zziafyc.wanandroid.R;
import com.zziafyc.wanandroid.mvp.model.BannerModel;

public class BannerViewHolder implements MZViewHolder<BannerModel> {
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        mImageView = view.findViewById(R.id.banner_iv);
        return view;
    }

    @Override
    public void onBind(Context context, int i, BannerModel model) {
        //设置图片
        Glide.with(context).load(model.getImagePath()).into(mImageView);
    }
}