package com.zziafyc.wanandroid.mvp.presenter;

import android.content.Context;

import com.zziafyc.wanandroid.base.BasePresenter;
import com.zziafyc.wanandroid.http.ApiRetrofit;
import com.zziafyc.wanandroid.http.ApiScheduler;
import com.zziafyc.wanandroid.http.Exception.FuncObservableException;
import com.zziafyc.wanandroid.http.Exception.HandleFuc;
import com.zziafyc.wanandroid.http.subscriber.ApiSubscriberObserver;
import com.zziafyc.wanandroid.mvp.model.ArticleListModel;
import com.zziafyc.wanandroid.mvp.model.BannerModel;
import com.zziafyc.wanandroid.mvp.model.HomeModel;
import com.zziafyc.wanandroid.mvp.view.HomeFragmentView;

import java.util.ArrayList;

import io.reactivex.functions.BiFunction;

public class HomePresenter extends BasePresenter<HomeFragmentView> {

    /**
     * 获取首页banner和推荐数据
     *
     * @param context
     * @param page
     */
    public void getHomeData(Context context, int page) {
        // banner数据和文章列表数据
        mApiUtils.getBanner()
                .compose(ApiScheduler.getObservableScheduler())
                .onErrorResumeNext(new FuncObservableException<>())
                .map(new HandleFuc<>())
                .zipWith(mApiUtils.getArticleList(page)
                                .compose(ApiScheduler.getObservableScheduler())
                                .onErrorResumeNext(new FuncObservableException<>())
                                .map(new HandleFuc<>())
                        , new BiFunction<ArrayList<BannerModel>, ArticleListModel, HomeModel>() {
                            @Override
                            public HomeModel apply(ArrayList<BannerModel> bannerModels, ArticleListModel articleModel) throws Exception {
                                return new HomeModel(bannerModels, articleModel);
                            }
                        }
                )
                .subscribe(new ApiSubscriberObserver<HomeModel>(context) {
                    @Override
                    public void onSuccess(HomeModel homeModel) {
                        getView().onLoadSuccess(homeModel);
                    }
                });
    }

    /**
     * 获取更多推荐数据
     *
     * @param context
     * @param page
     */
    public void getMoreData(Context context, int page) {
        ApiRetrofit.setObservableSubscribe(mApiUtils.getArticleList(page), new ApiSubscriberObserver<ArticleListModel>(context) {
            @Override
            public void onSuccess(ArticleListModel articleModel) {
                getView().onLoadMoreSuccess(articleModel);
            }
        });
    }

    /**
     * 收藏文章
     *
     * @param context
     * @param id
     * @param position
     */

    public void collectArticles(Context context, int id, int position) {

    }


    /**
     * 取消收藏文章
     *
     * @param context
     * @param id
     * @param position
     */
    public void cancleCollect(Context context, int id, int position) {

    }
}
