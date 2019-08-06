package com.zziafyc.wanandroid.common.Arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.zziafyc.wanandroid.common.Constants;

/**
 * Creat by chen on 2018/10/10
 * Describe:登录的拦截器
 *
 * @author chenbo
 */
@Interceptor(priority = 8, name = Constants.LOGIN_INTERCEPTOR)
public class LoginInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (postcard.getExtra() == Constants.LOGIN_EXTRA) {
            //判断是否登录。
            boolean isLogin = postcard.getExtras().getBoolean(Constants.IS_LOGIN);
            if (isLogin) {
                callback.onContinue(postcard);
            } else {
                //如果没有登录，那么跳转到登录界面。
                RouterUtil.goToActivity(RouterUrlManager.LOGIN);
            }
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {

    }
}
