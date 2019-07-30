package com.zziafyc.wanandroid.base.proxy;

import com.zziafyc.wanandroid.base.BasePresenter;
import com.zziafyc.wanandroid.base.BaseView;
import com.zziafyc.wanandroid.base.factory.IPresenterFactory;

public interface IPresenterProxy<V extends BaseView, P extends BasePresenter<V>> {
    /**
     * 设置创建presenter的工厂
     *
     * @param presenterFactory 工厂类型
     */

    void setPresenterFactory(IPresenterFactory<V, P> presenterFactory);

    /**
     * 获取presenter的工厂类
     *
     * @return IPresenterFactory
     */

    IPresenterFactory<V, P> getPresenterFactory();

    /**
     * 获取创建的presenter
     *
     * @return 指定类型的presenter
     */

    P getPresenter();

}
