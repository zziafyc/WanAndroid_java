package com.zziafyc.wanandroid.base.proxy;

import android.os.Bundle;

import com.zziafyc.wanandroid.base.BasePresenter;
import com.zziafyc.wanandroid.base.BaseView;
import com.zziafyc.wanandroid.base.factory.IPresenterFactory;

public class PresenterProxyImpl<V extends BaseView, P extends BasePresenter<V>> implements IPresenterProxy<V, P> {
    /**
     * 获取onSaveInstanceState中bundle的key
     */
    private static final String PRESENTER_KEY = "presenter_key";
    private P mPresenter;
    private Bundle mBundle;
    private boolean mIsBindView;

    private IPresenterFactory<V, P> mFactory;

    public PresenterProxyImpl(IPresenterFactory<V, P> factory) {
        this.mFactory = factory;
    }

    @Override
    public void setPresenterFactory(IPresenterFactory<V, P> presenterFactory) {
        if (mPresenter != null) {
            throw new IllegalArgumentException("这个方法只能在getPresenter()之前调用，如果Presenter已经创建则不能再修改");
        }
        this.mFactory = presenterFactory;
    }

    @Override
    public IPresenterFactory<V, P> getPresenterFactory() {
        return mFactory;
    }

    /**
     * 获取创建的Presenter
     *
     * @return 指定类型的Presenter
     * 如果之前创建过，而且是以外销毁则从Bundle中恢复
     */
    @Override
    public P getPresenter() {
        if (mFactory != null) {
            if (mPresenter == null) {
                mPresenter = mFactory.createPresenter();
                mPresenter.onCreatePresenterBundle(mBundle);
            }
        }
        return mPresenter;
    }

    /**
     * 绑定presenter和view
     *
     * @param view
     */
    public void onCreate(V view) {
        getPresenter();
        if (mPresenter != null && !mIsBindView && view != null) {
            mPresenter.onBindView(view);
            mIsBindView = true;
        }
    }

    /**
     * 销毁presenter创建的view
     */
    public void onUnbindView() {
        if (mPresenter != null && mIsBindView) {
            mPresenter.onUnBindView();

        }
    }

    /**
     * 销毁presenter
     */
    public void onDestroy() {
        if (mPresenter != null) {
            onUnbindView();
            mPresenter.OnDestroyPresenter();
            mPresenter = null;

        }
    }

    /**
     * 意外销毁的时候调用，存入回调给Presenter的Bundle和当前Presenter的id
     *
     * @return
     */
    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        getPresenter();
        if (mPresenter != null) {
            Bundle presenterBundle = new Bundle();
            mPresenter.onSaveInstanceState(presenterBundle);
            bundle.putBundle(PRESENTER_KEY, presenterBundle);
        }
        return bundle;
    }

    /**
     * 意外关闭恢复Presenter
     *
     * @param savedInstanceState 意外关闭时存储的Bundler
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mBundle = savedInstanceState;
    }
}
