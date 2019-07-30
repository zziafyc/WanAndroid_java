package com.zziafyc.wanandroid.mvp.view;

import com.zziafyc.wanandroid.base.BaseView;

/**
 * @author chen
 * @date 2017/12/17
 */

public interface MainActivityView extends BaseView {
    /**
     * 退出账号成功
     *
     * @param boo
     */
    void onLogoutSuccess(Boolean boo);
}
