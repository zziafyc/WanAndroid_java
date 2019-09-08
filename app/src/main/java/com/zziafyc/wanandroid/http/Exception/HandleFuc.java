package com.zziafyc.wanandroid.http.Exception;


import com.zziafyc.wanandroid.mvp.model.BaseModel;

import io.reactivex.functions.Function;

/**
 * Create by chen on 2018/9/30
 * Describe:
 *
 * @author chenbo
 */
public class HandleFuc<T> implements Function<BaseModel<T>, T> {
    @Override
    public T apply(BaseModel<T> response) {
        if (!response.isOk()) {
            throw new ServerException(response.errorCode, response.errorMsg);
        }
        return response.data;
    }
}