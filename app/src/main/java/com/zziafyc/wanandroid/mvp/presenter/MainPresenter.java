package com.zziafyc.wanandroid.mvp.presenter;

import android.content.Context;

import com.zziafyc.wanandroid.base.BasePresenter;
import com.zziafyc.wanandroid.http.ApiRetrofit;
import com.zziafyc.wanandroid.http.subscriber.ApiSubscriberObserver;
import com.zziafyc.wanandroid.mvp.view.MainActivityView;

/**
 * @作者 zziafyc
 * @创建日期 2019/8/4
 * @description
 */
public class MainPresenter extends BasePresenter<MainActivityView> {

    //退出账号
    public void Logout(Context context) {
        ApiRetrofit.setObservableBooleanSubscribe(mApiUtils.logout(), new ApiSubscriberObserver<Boolean>(context) {
            @Override
            public void onSuccess(Boolean aBoolean) {
                getView().onLogOutSuccess(aBoolean);
        }
        });

    }

}
