package com.zziafyc.wanandroid.adapter;

import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zziafyc.wanandroid.R;
import com.zziafyc.wanandroid.mvp.model.ArticleModel;

import java.util.List;

public class ArticleAdapter extends BaseQuickAdapter<ArticleModel, BaseViewHolder> {

    public ArticleAdapter(List<ArticleModel> data) {
        super(R.layout.item_article, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleModel item) {
        helper.setText(R.id.tv_article_title, Html.fromHtml(item.getTitle()))
                .setText(R.id.tv_article_author, item.getAuthor())
                .setText(R.id.tv_article_date, item.getNiceDate())
                .addOnClickListener(R.id.iv_like)
                .setText(R.id.tv_article_chapterName, item.getChapterName());
        if (item.isCollect()) {
            helper.setImageResource(R.id.iv_like, R.drawable.ic_like);
        } else {
            helper.setImageResource(R.id.iv_like, R.drawable.ic_like_not);
        }
        if (item.getTop() == "1") {
            helper.getView(R.id.tv_article_top).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_article_top).setVisibility(View.GONE);
        }
        if (null != item.getTags() && item.getTags().size() > 0) {
            helper.getView(R.id.tv_article_tag).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_article_tag, item.getTags().get(0).getName());
        } else {
            helper.getView(R.id.tv_article_tag).setVisibility(View.GONE);
        }

    }
}
