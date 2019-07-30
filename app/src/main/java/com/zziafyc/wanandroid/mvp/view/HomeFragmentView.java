package com.zziafyc.wanandroid.mvp.view;

import com.zziafyc.wanandroid.base.BaseView;
import com.zziafyc.wanandroid.mvp.model.ArticleModel;

/**
 * @作者 zziafyc
 * @创建日期 2019/7/29
 * @description 接口实现了相关操作的接口回调
 */

public interface HomeFragmentView extends BaseView {
    /**
     * 数据加载成功
     *
     * @param articleModel
     */
    void onLoadSuccess(ArticleModel articleModel);

    /**
     * 加载更多成功
     *
     * @param articleModel
     */
    void onLoadMoreSuccess(ArticleModel articleModel);

    /**
     * 收藏成功
     *
     * @param position
     */
    void onCollectSuccess(int position);

    /**
     * 取消收藏成功
     *
     * @param position
     */
    void onUnCollectSuccess(int position);


}
