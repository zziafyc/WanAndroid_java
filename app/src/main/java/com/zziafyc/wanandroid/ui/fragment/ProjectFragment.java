package com.zziafyc.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zziafyc.wanandroid.R;
import com.zziafyc.wanandroid.base.BaseFragment;
import com.zziafyc.wanandroid.base.factory.CreatePresenter;
import com.zziafyc.wanandroid.common.Arouter.RouterUrlManager;
import com.zziafyc.wanandroid.mvp.presenter.ProjectPresenter;
import com.zziafyc.wanandroid.mvp.view.ProjectFragmentView;

@CreatePresenter(ProjectPresenter.class)
@Route(path = RouterUrlManager.FRAGMENT_PROJECT)
public class ProjectFragment extends BaseFragment<ProjectFragmentView, ProjectPresenter>
        implements ProjectFragmentView {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
