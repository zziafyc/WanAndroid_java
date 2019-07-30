package com.zziafyc.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zziafyc.wanandroid.base.factory.IPresenterFactory;
import com.zziafyc.wanandroid.base.factory.PresenterFactoryImpl;
import com.zziafyc.wanandroid.base.proxy.IPresenterProxy;
import com.zziafyc.wanandroid.base.proxy.PresenterProxyImpl;

/**
 * @作者 zziafyc
 * @创建日期 2019/7/28
 * @description baseActivity
 */

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity
        implements IPresenterProxy<V, P>, BaseViewInterface {
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    private String TAG = this.getClass().getName();
    private PresenterProxyImpl<V, P> mPresenterProxy = new PresenterProxyImpl<>
            (PresenterFactoryImpl.<V, P>createFactory(getClass()));

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY, mPresenterProxy.onSaveInstanceState());
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenterProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        mPresenterProxy.onCreate((V) this);
        initView();
        initData(savedInstanceState);
    }


    @Override
    public void setPresenterFactory(IPresenterFactory<V, P> presenterFactory) {
        mPresenterProxy.setPresenterFactory(presenterFactory);

    }

    @Override
    public IPresenterFactory<V, P> getPresenterFactory() {
        return mPresenterProxy.getPresenterFactory();
    }

    @Override
    public P getPresenter() {
        return mPresenterProxy.getPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterProxy.onDestroy();
    }

}
