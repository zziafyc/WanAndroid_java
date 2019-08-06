package com.zziafyc.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zziafyc.wanandroid.R;
import com.zziafyc.wanandroid.base.BaseFragment;
import com.zziafyc.wanandroid.base.factory.CreatePresenter;
import com.zziafyc.wanandroid.common.Arouter.RouterUrlManager;
import com.zziafyc.wanandroid.mvp.presenter.NavigationPresenter;
import com.zziafyc.wanandroid.mvp.view.NavigationFragmentView;

@CreatePresenter(NavigationPresenter.class)
@Route(path = RouterUrlManager.FRAGMENT_NAVIGATION)
public class NavigationFragment extends BaseFragment<NavigationFragmentView, NavigationPresenter>
        implements NavigationFragmentView {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
