package com.zziafyc.wanandroid;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

public class App extends Application {
    public static Context mContext;

    static {
        //设置全局的基本设置构建器
        SmartRefreshLayout.setDefaultRefreshInitializer((context, refreshLayout) -> {
            //取消内容不满一页时开启上拉加载功能
            refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
            //是否在刷新的时候禁止列表的操作
            refreshLayout.setDisableContentWhenRefresh(true);
            //是否在加载的时候禁止列表的操作
            refreshLayout.setDisableContentWhenLoading(true);
        });
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            //全局设置主题颜色
            layout.setPrimaryColorsId(R.color.color_60white, R.color.color_212121);
            //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            return new ClassicsHeader(context);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    public static Context getmContext() {
        return mContext;
    }
}
