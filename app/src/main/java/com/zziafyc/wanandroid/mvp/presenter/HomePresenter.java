package com.zziafyc.wanandroid.mvp.presenter;

import android.content.Context;

import com.zziafyc.wanandroid.base.BasePresenter;
import com.zziafyc.wanandroid.http.ApiRetrofit;
import com.zziafyc.wanandroid.http.subscriber.ApiSubscriberObserver;
import com.zziafyc.wanandroid.mvp.model.ArticleModel;
import com.zziafyc.wanandroid.mvp.view.HomeFragmentView;

public class HomePresenter extends BasePresenter<HomeFragmentView> {

    /**
     * 获取首页banner和推荐数据
     *
     * @param context
     * @param page
     */
    public void getHomeData(Context context, int page) {
        ApiRetrofit.setObservableSubscribe(mApiUtils.getArticleList(page), new ApiSubscriberObserver<ArticleModel>(context) {
            @Override
            public void onSuccess(ArticleModel articleModel) {
                getView().onLoadSuccess(articleModel);
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
        ApiRetrofit.setObservableSubscribe(mApiUtils.getArticleList(page), new ApiSubscriberObserver<ArticleModel>(context) {
            @Override
            public void onSuccess(ArticleModel articleModel) {
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
