package com.zziafyc.wanandroid.common.Arouter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zziafyc.wanandroid.R;
import com.zziafyc.wanandroid.common.Constants;
import com.zziafyc.wanandroid.utils.SharePreferenceUtil;

/**
 * Creat by chen on 2018/10/10
 * Describe:
 *
 * @author chenbo
 */
public class RouterUtil {
    /**
     * 通过url跳转页面
     *
     * @param url
     */
    public static void goToActivity(String url) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.IS_LOGIN, SharePreferenceUtil.getBoolean(Constants.IS_LOGIN, false));
        ARouter.getInstance().build(url).with(bundle).setProvider(new LoginInterceptor()).navigation();
    }

    /**
     * 通过url,bundle跳转页面
     *
     * @param url
     * @param bundle
     */
    public static void goToActivity(String url, Bundle bundle) {
        bundle.putBoolean(Constants.IS_LOGIN, SharePreferenceUtil.getBoolean(Constants.IS_LOGIN, false));
        ARouter.getInstance().build(url).with(bundle).setProvider(new LoginInterceptor()).navigation();
    }

    /**
     * startActivityForResult
     *
     * @param
     */
    public static void goToActivity(String url, Activity activity, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.IS_LOGIN, SharePreferenceUtil.getBoolean(Constants.IS_LOGIN, false));
        ARouter.getInstance().build(url).with(bundle).setProvider(new LoginInterceptor()).navigation(activity, requestCode);
}

    /**
     * 获取Fragment
     *
     * @return
     */
    public static Fragment getFragment(String url) {
        return (Fragment) ARouter.getInstance().build(url).navigation();
    }

    @SuppressLint("ResourceAsColor")
    public static boolean checkLoginState(View view) {
        String userPhone = SharePreferenceUtil.getString(Constants.USER_PHONE, "");
        if (!TextUtils.isEmpty(userPhone)) {
            return true;
        } else {
            Snackbar.make(view, "请先登录哦~", Snackbar.LENGTH_LONG)
                    .setAction("去登录", v -> RouterUtil.goToActivity(RouterUrlManager.LOGIN))
                    .setActionTextColor(R.color.Yellow)
                    .setDuration(3000).show();

            return false;
        }
    }
}
