package com.zziafyc.wanandroid.mvp.model;

import java.util.ArrayList;

/**
 * @author chenbo
 */
public class HomeModel {

    private ArrayList<BannerModel> bannerModels;
    private ArticleListModel articleListModels;
    private ArticleListModel topArticleListModels;

    public HomeModel(ArrayList<BannerModel> bannerModels, ArticleListModel articleListModels) {
        this.bannerModels = bannerModels;
        this.articleListModels = articleListModels;
    }

    public HomeModel(ArrayList<BannerModel> bannerModels, ArticleListModel articleListModels, ArticleListModel topArticleListModels) {
        this.bannerModels = bannerModels;
        this.articleListModels = articleListModels;
        this.topArticleListModels = topArticleListModels;
    }

    public ArrayList<BannerModel> getBannerModels() {
        return bannerModels;
    }

    public void setBannerModels(ArrayList<BannerModel> bannerModels) {
        this.bannerModels = bannerModels;
    }

    public ArticleListModel getArticleListModels() {
        return articleListModels;
    }

    public void setArticleListModels(ArticleListModel articleListModels) {
        this.articleListModels = articleListModels;
    }

    public ArticleListModel getTopArticleListModels() {
        return topArticleListModels;
    }

    public void setTopArticleListModels(ArticleListModel topArticleListModels) {
        this.topArticleListModels = topArticleListModels;
    }
}
