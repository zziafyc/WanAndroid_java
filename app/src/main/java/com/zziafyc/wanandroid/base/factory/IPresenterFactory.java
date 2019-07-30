package com.zziafyc.wanandroid.base.factory;

import com.zziafyc.wanandroid.base.BasePresenter;
import com.zziafyc.wanandroid.base.BaseView;

/**
 * @作者 zziafyc
 * @创建日期 2019/7/28
 * @description 创建present的工厂类
 */
public interface IPresenterFactory<V extends BaseView, P extends BasePresenter<V>> {

    /**
     * 创建Presenter
     *
     * @return presenter
     */
    P createPresenter();
}
