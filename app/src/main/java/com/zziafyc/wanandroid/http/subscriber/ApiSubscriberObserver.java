package com.zziafyc.wanandroid.http.subscriber;

import android.content.Context;

import com.zziafyc.wanandroid.http.Exception.ApiException;
import com.zziafyc.wanandroid.utils.NetworkHelper;
import com.zziafyc.wanandroid.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author chen
 * @date 2017/12/17
 */

public abstract class ApiSubscriberObserver<T> implements Observer<T> {
    private Context context;
    private Disposable disposable;

    public ApiSubscriberObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
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
        disposable.dispose();
    }

    @Override
    public void onComplete() {
        disposable.dispose();

    }

    /**
     * 加载成功
     *
     * @return
     */
    public abstract void onSuccess(T t);
}
