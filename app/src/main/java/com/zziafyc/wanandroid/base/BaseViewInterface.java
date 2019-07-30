package com.zziafyc.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @作者 zziafyc
 * @创建日期 2019/7/28
 * @description
 */

public interface BaseViewInterface {
    /**
     * get布局id
     *
     * @return
     */
    int getLayoutId();

    /**
     * 初始化布局
     *
     * @return
     */
    void initView();

    /**
     * 初始化数据
     */

    void initData(@Nullable Bundle savedInstanceState);
}
