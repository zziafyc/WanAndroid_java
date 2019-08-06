package com.zziafyc.wanandroid.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zziafyc.wanandroid.common.Arouter.RouterUrlManager;
import com.zziafyc.wanandroid.common.Arouter.RouterUtil;

/**
 * @author chenbo
 */
public class SplashActivity extends AppCompatActivity {

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        )
                .subscribe(granted -> {
                    if (granted) {
                        RouterUtil.goToActivity(RouterUrlManager.MAIN);
                        finish();
                    }
                });

    }
}
