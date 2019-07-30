package com.zziafyc.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.zziafyc.wanandroid.R;
import com.zziafyc.wanandroid.adapter.ArticleAdapter;
import com.zziafyc.wanandroid.base.BaseFragment;
import com.zziafyc.wanandroid.base.factory.CreatePresenter;
import com.zziafyc.wanandroid.mvp.model.ArticleListModel;
import com.zziafyc.wanandroid.mvp.model.ArticleModel;
import com.zziafyc.wanandroid.mvp.presenter.HomePresenter;
import com.zziafyc.wanandroid.mvp.view.HomeFragmentView;

import java.util.List;

import butterknife.BindView;

@CreatePresenter(HomePresenter.class)
public class HomeFragment extends BaseFragment<HomeFragmentView, HomePresenter> implements HomeFragmentView {
    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;
    private List<ArticleListModel> articleListModels;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ArticleAdapter(articleListModels);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("weburl", ((ArticleListModel) adapter.getData().get(position)).getLink());
            bundle.putString("title", ((ArticleListModel) (adapter.getData().get(position))).getTitle());
            Toast.makeText(getActivity(), ((ArticleListModel) (adapter.getData().get(position))).getTitle(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getPresenter().getHomeData(getActivity(), currentPage);

    }

    @Override
    public void onLoadSuccess(ArticleModel articleModel) {
        Toast.makeText(getActivity(), "数据加载成功啦！！", Toast.LENGTH_SHORT).show();
        //数据加载成功后回调
        articleListModels = articleModel.getDatas();
        super.onLoadSuccess(articleListModels);
        mAdapter.setNewData(articleListModels);

    }

    @Override
    protected void onLoadMoreData(RefreshLayout refreshLayout) {
        super.onLoadMoreData(refreshLayout);
        getPresenter().getMoreData(getActivity(), currentPage);
    }

    @Override
    public void onLoadMoreSuccess(ArticleModel articleModel) {
        Toast.makeText(getActivity(), "加载更多数据啦！！", Toast.LENGTH_SHORT).show();
        //加载更多成功后回调
        List<ArticleListModel> moreArticleListModel = articleModel.getDatas();
        super.onLoadMoreSuccess(moreArticleListModel);
        mAdapter.addData(moreArticleListModel);
    }

    @Override
    public void onCollectSuccess(int position) {
        //收藏成功后回调

    }

    @Override
    public void onUnCollectSuccess(int position) {
        //取消收藏成功后回调

    }

    @Override
    protected boolean useLoadMore() {
        return true;
    }
}
