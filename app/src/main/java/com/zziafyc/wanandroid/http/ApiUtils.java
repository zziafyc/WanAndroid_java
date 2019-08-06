package com.zziafyc.wanandroid.http;

import com.zziafyc.wanandroid.mvp.model.ArticleModel;
import com.zziafyc.wanandroid.mvp.model.BaseModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * @author chen
 * @date 2017/12/17
 */

public interface ApiUtils {
    String GANK_HOST = "use_host:gank";
    String WAN_HOST = "use_host:wan";

    /**
     * 首页文章列表
     *
     * @param page
     * @return ArrayList<BannerModel>
     */
    @Headers(WAN_HOST)
    @GET(UrlManager.ARTICLE_LIST)
    Observable<BaseModel<ArticleModel>> getArticleList(@Path("page") int page);

    @Headers(WAN_HOST)
    @GET(UrlManager.LOGOUT)
    Observable<BaseModel> logout();

}
