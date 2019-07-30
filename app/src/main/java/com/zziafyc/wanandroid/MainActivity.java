package com.zziafyc.wanandroid;

import android.os.Bundle;

import com.zziafyc.wanandroid.base.BaseActivity;
import com.zziafyc.wanandroid.base.factory.CreatePresenter;
import com.zziafyc.wanandroid.mvp.presenter.HomePresenter;
import com.zziafyc.wanandroid.mvp.view.HomeFragmentView;
import com.zziafyc.wanandroid.mvp.view.MainActivityView;

/**
 * @作者 zziafyc
 * @创建日期 2019/7/27 0027
 * @description
 */
@CreatePresenter(HomePresenter.class)
public class MainActivity extends BaseActivity<HomeFragmentView, HomePresenter> implements MainActivityView {


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onLogoutSuccess(Boolean boo) {

    }
}
