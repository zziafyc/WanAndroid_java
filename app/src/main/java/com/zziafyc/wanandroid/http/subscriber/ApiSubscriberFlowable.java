package com.zziafyc.wanandroid.http.subscriber;

import android.content.Context;

import com.zziafyc.wanandroid.http.Exception.ApiException;
import com.zziafyc.wanandroid.utils.NetworkHelper;
import com.zziafyc.wanandroid.utils.ToastUtil;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @作者 zziafyc
 * @创建日期 2
 * @description z采用rxjava2的背压策略
 */
public abstract class ApiSubscriberFlowable<T> extends ResourceSubscriber<T> {
    private Context context;

    public ApiSubscriberFlowable(Context context) {
        this.context = context;
    }


    @Override
    public void onNext(T t) {
        if (t == null) {
            return;
        }
        onSuccess(t);
    }

    @Override
    public void onError(Throwable t) {
        if (!NetworkHelper.isNetworkAvailable(context)) {
            ToastUtil.showToast(context, "当前无网络连接，请先设置网络!");
        } else {
            ApiException.handleException(context, t);
            ToastUtil.showToast(context, t.getMessage());
        }
        dispose();
    }

    @Override
    public void onComplete() {
        dispose();

    }

    /**
     * 加载成功
     *
     * @return
     */
    public abstract void onSuccess(T t);
}
