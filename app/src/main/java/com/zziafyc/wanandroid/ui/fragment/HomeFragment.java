package com.zziafyc.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zziafyc.wanandroid.R;
import com.zziafyc.wanandroid.adapter.ArticleAdapter;
import com.zziafyc.wanandroid.adapter.viewholder.BannerViewHolder;
import com.zziafyc.wanandroid.base.BaseFragment;
import com.zziafyc.wanandroid.base.factory.CreatePresenter;
import com.zziafyc.wanandroid.common.Arouter.RouterUrlManager;
import com.zziafyc.wanandroid.mvp.model.ArticleModel;
import com.zziafyc.wanandroid.mvp.model.ArticleListModel;
import com.zziafyc.wanandroid.mvp.model.BannerModel;
import com.zziafyc.wanandroid.mvp.model.HomeModel;
import com.zziafyc.wanandroid.mvp.presenter.HomePresenter;
import com.zziafyc.wanandroid.mvp.view.HomeFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@CreatePresenter(HomePresenter.class)
@Route(path = RouterUrlManager.FRAGMENT_HOME)
public class HomeFragment extends BaseFragment<HomeFragmentView, HomePresenter> implements HomeFragmentView {
    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;
    private List<ArticleModel> mArticleModels;
    private MZBannerView<BannerModel> mBannerView;
    private List<BannerModel> mBannerModelList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ArticleAdapter(mArticleModels);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("weburl", ((ArticleModel) adapter.getData().get(position)).getLink());
            bundle.putString("title", ((ArticleModel) (adapter.getData().get(position))).getTitle());
        });
        View headView = getLayoutInflater().inflate(R.layout.banner, null);
        mBannerView = headView.findViewById(R.id.banner);
        mAdapter.addHeaderView(headView);

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getPresenter().getHomeData(getActivity(), currentPage);


    }

    @Override
    public void onLoadSuccess(HomeModel homeModel) {
        //数据加载成功后回调
        mArticleModels = homeModel.getArticleListModels().getDatas();
        super.onLoadSuccess(mArticleModels);
        mAdapter.setNewData(mArticleModels);
        //展示轮播图
        mBannerModelList.clear();
        mBannerModelList.addAll(homeModel.getBannerModels());
        mBannerView.setPages(mBannerModelList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        mBannerView.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBannerView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBannerView.pause();//暂停轮播

    }

    @Override
    protected void onLoadMoreData(RefreshLayout refreshLayout) {
        super.onLoadMoreData(refreshLayout);
        getPresenter().getMoreData(getActivity(), currentPage);
    }

    @Override
    public void onLoadMoreSuccess(ArticleListModel articleModel) {
        //加载更多成功后回调
        List<ArticleModel> moreArticleModel = articleModel.getDatas();
        super.onLoadMoreSuccess(moreArticleModel);
        mAdapter.addData(moreArticleModel);
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

