package com.zziafyc.wanandroid.mvp.model;

import java.util.ArrayList;

/**
 * @author chenbo
 */
public class HomeModel {

    private ArrayList<BannerModel> bannerModels;
    private ArticleListModel articleListModels;

    public HomeModel(ArrayList<BannerModel> bannerModels, ArticleListModel articleListModels) {
        this.bannerModels = bannerModels;
        this.articleListModels = articleListModels;
    }

    public ArrayList<BannerModel> getBannerModels() {
        return bannerModels;
    }

    public ArticleListModel getArticleListModels() {
        return articleListModels;
    }

}
