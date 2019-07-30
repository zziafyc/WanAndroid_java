package com.zziafyc.wanandroid.mvp.model;

import java.util.ArrayList;

/**
 * @author chenbo
 */
public class HomeModel {

    private ArrayList<BannerModel> bannerModels;
    private ArticleModel articleListModels;

    public HomeModel(ArrayList<BannerModel> bannerModels, ArticleModel articleListModels) {
        this.bannerModels = bannerModels;
        this.articleListModels = articleListModels;
    }

    public ArrayList<BannerModel> getBannerModels() {
        return bannerModels;
    }

    public ArticleModel getArticleListModels() {
        return articleListModels;
    }

}
