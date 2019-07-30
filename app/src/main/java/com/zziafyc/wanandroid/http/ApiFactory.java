package com.zziafyc.wanandroid.http;

/**
 *
 * @author chen
 * @date 2017/12/17
 */

public class ApiFactory {
    protected static final Object MONITOR = new Object();
    private static ApiUtils apiUtil = null;
    public static ApiUtils getApiUtil(){
        synchronized (MONITOR){
            if (apiUtil == null) {
                apiUtil=new ApiRetrofit().getApi();
            }
            return apiUtil;
        }
    }
}
