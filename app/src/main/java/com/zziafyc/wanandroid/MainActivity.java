package com.zziafyc.wanandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zziafyc.wanandroid.adapter.FragmentPageAdapter;
import com.zziafyc.wanandroid.base.BaseActivity;
import com.zziafyc.wanandroid.base.factory.CreatePresenter;
import com.zziafyc.wanandroid.common.Arouter.RouterUrlManager;
import com.zziafyc.wanandroid.common.Arouter.RouterUtil;
import com.zziafyc.wanandroid.mvp.presenter.MainPresenter;
import com.zziafyc.wanandroid.mvp.view.MainActivityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @作者 zziafyc
 * @创建日期 2019/7/27 0027
 * @description
 */
@Route(path = RouterUrlManager.MAIN)
@CreatePresenter(MainPresenter.class)
public class MainActivity extends BaseActivity<MainActivityView, MainPresenter> implements MainActivityView,
        NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.navigationView)
    NavigationView mNavigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    private long exitTime = 0;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mToolbar.setTitle(getResources().getString(R.string.home));
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, 0, 0);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(this);
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.action_home:
                    clickTab(0);
                    //返回true,否则tab按钮不变色,未被选中
                    return true;
                case R.id.action_knowledge_system:
                    clickTab(1);
                    return true;
                case R.id.action_wechat:
                    clickTab(2);
                    return true;
                case R.id.action_navigation:
                    clickTab(3);
                    return true;
                case R.id.action_project:
                    clickTab(4);
                    return true;
                default:
                    break;
            }
            return false;
        });
        FragmentPageAdapter fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager(), initFragment());
        mViewPager.setAdapter(fragmentPageAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                changePage(position);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    private void changePage(int position) {
        switch (position) {
            case 0:
                mBottomNavigationView.setSelectedItemId(R.id.action_home);
                getSupportActionBar().setTitle(getResources().getString(R.string.home));
                break;
            case 1:
                mBottomNavigationView.setSelectedItemId(R.id.action_knowledge_system);
                getSupportActionBar().setTitle(getResources().getString(R.string.knowledge_system));
                break;
            case 2:
                mBottomNavigationView.setSelectedItemId(R.id.action_wechat);
                getSupportActionBar().setTitle(getResources().getString(R.string.wechat));
                break;
            case 3:
                mBottomNavigationView.setSelectedItemId(R.id.action_navigation);
                getSupportActionBar().setTitle(getResources().getString(R.string.navigation));
                break;
            case 4:
                mBottomNavigationView.setSelectedItemId(R.id.action_project);
                getSupportActionBar().setTitle(getResources().getString(R.string.project));
                break;
            default:
                break;

        }
    }

    private List<Fragment> initFragment() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(RouterUtil.getFragment(RouterUrlManager.FRAGMENT_HOME));
        mFragments.add(RouterUtil.getFragment(RouterUrlManager.FRAGMENT_KNOWLEDGE));
        mFragments.add(RouterUtil.getFragment(RouterUrlManager.FRAGMENT_CHAT));
        mFragments.add(RouterUtil.getFragment(RouterUrlManager.FRAGMENT_NAVIGATION));
        mFragments.add(RouterUtil.getFragment(RouterUrlManager.FRAGMENT_PROJECT));
        return mFragments;
    }

    private void clickTab(int index) {
        //为防止隔页切换时,滑过中间页面的问题,去除页面切换缓慢滑动的动画效果
        mViewPager.setCurrentItem(index, false);
        supportInvalidateOptionsMenu();

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onLogOutSuccess(Boolean success) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            int anInt = 2000;
            if ((System.currentTimeMillis() - exitTime) > anInt) {

                Snackbar.make(mCoordinatorLayout, "再按一次退出程序", Snackbar.LENGTH_LONG).show();

                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
