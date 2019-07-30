package com.zziafyc.wanandroid.mvp.presenter;

import android.content.Context;

import com.zziafyc.wanandroid.base.BasePresenter;
import com.zziafyc.wanandroid.http.ApiScheduler;
import com.zziafyc.wanandroid.http.Exception.FuncObservableException;
import com.zziafyc.wanandroid.http.Exception.HandleFuc;
import com.zziafyc.wanandroid.http.subscriber.LodeMoreObserverSubscriber;
import com.zziafyc.wanandroid.mvp.model.ArticleModel;
import com.zziafyc.wanandroid.mvp.model.BaseModel;
import com.zziafyc.wanandroid.mvp.view.HomeFragmentView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HomePresenter extends BasePresenter<HomeFragmentView> {

    /**
     * 获取首页banner和推荐数据
     *
     * @param context
     * @param page
     */
    public void getHomeData(Context context, int page) {
        mApiUtils.getArticleList(page)
                .compose(ApiScheduler.getObservableScheduler())
                .onErrorResumeNext(new FuncObservableException<>())
                .subscribe(new Observer<BaseModel<ArticleModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseModel<ArticleModel> model) {
                        getView().onLoadSuccess(model.data);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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

        mApiUtils.getArticleList(page)
                .compose(ApiScheduler.getObservableScheduler())
                .onErrorResumeNext(new FuncObservableException<>())
                .map(new HandleFuc<>())
                .subscribe(new LodeMoreObserverSubscriber<ArticleModel>(context) {
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
