package com.zziafyc.wanandroid.base.factory;

import com.zziafyc.wanandroid.base.BasePresenter;
import com.zziafyc.wanandroid.base.BaseView;

public class PresenterFactoryImpl<V extends BaseView, P extends BasePresenter<V>> implements IPresenterFactory<V, P> {
    /**
     * 需要创建的Presenter的类型
     */
    private final Class<P> mPresenterClass;

    public PresenterFactoryImpl(Class<P> presenterClass) {
        this.mPresenterClass = presenterClass;
    }

    @Override
    public P createPresenter() {
        try {
            return mPresenterClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Presenter create failed!，" +
                    "please check if declaration @CreatePresenter(xxx.class) anotation or not");
        }
    }

    /**
     * 根据注解创建Presenter的工厂实现类
     *
     * @param clazz 需要创建Presenter的V层实现类
     * @param <V>   当前View实现的接口类型
     * @param <P>   当前要创建的Presenter类型
     * @return 工厂类
     */
    public static <V extends BaseView, P extends BasePresenter<V>> PresenterFactoryImpl<V, P> createFactory(Class<?> clazz) {
        CreatePresenter annotation = clazz.getAnnotation(CreatePresenter.class);
        Class<P> aClass = null;
        if (annotation != null) {
            aClass = (Class<P>) annotation.value();
        }
        return aClass == null ? null : new PresenterFactoryImpl<>(aClass);
    }
}
