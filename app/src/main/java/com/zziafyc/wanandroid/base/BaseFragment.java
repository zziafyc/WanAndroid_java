package com.zziafyc.wanandroid.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zziafyc.wanandroid.R;
import com.zziafyc.wanandroid.base.factory.IPresenterFactory;
import com.zziafyc.wanandroid.base.factory.PresenterFactoryImpl;
import com.zziafyc.wanandroid.base.proxy.IPresenterProxy;
import com.zziafyc.wanandroid.base.proxy.PresenterProxyImpl;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>> extends Fragment
        implements IPresenterProxy<V, P>, BaseViewInterface {
    private String TAG = this.getClass().getName();
    private static final String PRESENTER_SAVE_KEY_FRAGMENT = "presenter_save_key_fragment";
    private PresenterProxyImpl<V, P> mPresenterProxy = new PresenterProxyImpl<>
            (PresenterFactoryImpl.<V, P>createFactory(getClass()));
    private View view;
    /**
     * Fragment的View加载完毕的标记
     */
    private boolean isViewCreated;
    /**
     * Fragment对用户可见的标记
     */
    private boolean isDataLoaded;
    public int currentPage;
    public SmartRefreshLayout smartRefresh;
    private Unbinder binder;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY_FRAGMENT, mPresenterProxy.onSaveInstanceState());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPresenterProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY_FRAGMENT));
        }
        mPresenterProxy.onCreate((V) this);
        currentPage = 0;
        view = inflater.inflate(getLayoutId(), container, false);
        binder = ButterKnife.bind(this, view);
        /**
         * 设置smartRefresh属性
         */
        smartRefresh = view.findViewById(R.id.smart_refresh);
        if (smartRefresh != null) {
            smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    onLoadMoreData(refreshLayout);
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    onRefreshData(refreshLayout);
                }
            });
            if (!useLoadMore()) {
                smartRefresh.setEnableLoadMore(false);
            }
        }
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        prepareInitData(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && isViewCreated && !isDataLoaded) {
            prepareInitData(null);
            isDataLoaded = true;
        }
    }

    public void prepareInitData(@Nullable Bundle savedInstanceState) {
        if (getUserVisibleHint() && isViewCreated && (!isDataLoaded)) {
            initData(savedInstanceState);
            isDataLoaded = true;
        }
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

    /**
     * 子类自己实现，是否使用加载更多,默认使用
     *
     * @return
     */
    protected boolean useLoadMore() {
        return true;
    }

    /**
     * 子类自己实现，数据加载成功onLoadSucess
     *
     * @param mList
     */
    protected void onLoadSuccess(List mList) {
        smartRefresh.finishRefresh();
        currentPage++;
    }

    /**
     * 子类不必实现，自动调用initData刷新数据
     */
    protected void onRefreshData(RefreshLayout refreshLayout) {
        currentPage = 0;
        initData(null);
    }

    protected void onRefreshSuccess() {

    }

    /**
     * 子类自己实现，onLoadMoreData
     *
     * @param
     */
    protected void onLoadMoreData(RefreshLayout refreshLayout) {

    }

    /**
     * 子类自己实现，加载更多成功后的操作
     *
     * @param mlist
     */
    protected void onLoadMoreSuccess(List mlist) {
        currentPage++;
        if (smartRefresh != null) {
            if (mlist == null) {
                return;
            }
            if (mlist.size() == 0) {
                smartRefresh.finishLoadMoreWithNoMoreData();
            } else {
                smartRefresh.finishLoadMore();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterProxy.onDestroy();
        binder.unbind();

    }
}



























