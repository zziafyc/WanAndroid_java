package com.zziafyc.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zziafyc.wanandroid.http.ApiFactory;
import com.zziafyc.wanandroid.http.ApiUtils;
import com.zziafyc.wanandroid.utils.LogUtil;

public abstract class BasePresenter<V extends BaseView> {
    private V baseView;
    private String TAG = this.getClass().getName();
    public ApiUtils mApiUtils;

    /**
     * presenter初始化
     */
    public BasePresenter() {
        mApiUtils = ApiFactory.getApiUtil();
    }

    /**
     * Presenter创建的时候使用
     *
     * @param savedState 被意外销毁后重建后的Bundle
     */
    public void onCreatePresenterBundle(@Nullable Bundle savedState) {
    }

    /**
     * 绑定view
     */
    public void onBindView(V view) {
        this.baseView = view;

    }

    /**
     * 解绑view
     */
    public void onUnBindView() {
        this.baseView = null;
    }

    /**
     * presenter销毁时的操作
     */
    public void OnDestroyPresenter() {

    }

    /**
     * presenter 意外销毁时，它的调用时机和Activity、Fragment、View中的onSaveInstanceState
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        LogUtil.d(TAG, " presenter onSaveInstanceState");
    }

    /**
     * 获取当前view
     *
     * @return 获取view
     */

    public V getView() {
        return this.baseView;
    }
}
