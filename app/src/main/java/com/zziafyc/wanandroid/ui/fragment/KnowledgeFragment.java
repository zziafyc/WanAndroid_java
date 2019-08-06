package com.zziafyc.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zziafyc.wanandroid.R;
import com.zziafyc.wanandroid.base.BaseFragment;
import com.zziafyc.wanandroid.base.factory.CreatePresenter;
import com.zziafyc.wanandroid.common.Arouter.RouterUrlManager;
import com.zziafyc.wanandroid.mvp.presenter.KnowledgePresenter;
import com.zziafyc.wanandroid.mvp.view.KnowledgeFragmentView;

@CreatePresenter(KnowledgePresenter.class)
@Route(path = RouterUrlManager.FRAGMENT_KNOWLEDGE)
public class KnowledgeFragment extends BaseFragment<KnowledgeFragmentView, KnowledgePresenter> implements KnowledgeFragmentView {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
