package com.zziafyc.wanandroid.adapter;

import android.support.annotation.NonNull;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zziafyc.wanandroid.R;
import com.zziafyc.wanandroid.mvp.model.ArticleListModel;

import java.util.List;

public class ArticleAdapter extends BaseQuickAdapter<ArticleListModel, BaseViewHolder> {

    public ArticleAdapter(List<ArticleListModel> data) {
        super(R.layout.item_article, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleListModel item) {
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

    }
}
