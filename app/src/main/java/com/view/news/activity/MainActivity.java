package com.view.news.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.view.news.BaseActivity;
import com.view.news.R;
import com.view.news.presenter.MainPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.vp_main)
    public ViewPager vpMain;
    @BindView(R.id.line)
    public View line;
    @BindView(R.id.iv_tab_more)
    public ImageView ivTabMore;
    @BindView(R.id.tab_FindFragment_title)
    public TabLayout tl_title;
    @BindView(R.id.fl_tab_more)
    public FrameLayout fl_tab_more;
    @BindView(R.id.iv_top)
    ImageView iv_top;

    private MainPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new MainPresenterImpl(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        tb_main.setTitle("资讯");
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
        presenter.initData();
    }

    @Override
    public void initListener() {
        super.initListener();
        presenter.initListener();
    }

    @OnClick({R.id.fl_tab_more, R.id.iv_top})
    public void btnTabMoreClick(View view) {
        presenter.btnTabMoreClick(view);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return presenter.onKeyDown(keyCode, event);
    }

    public MainPresenterImpl getPreseneter() {
        return presenter;
    }
}
