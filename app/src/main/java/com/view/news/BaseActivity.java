package com.view.news;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Destiny on 2017/1/4.
 */

public class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @BindView(R.id.tb_main)
    public Toolbar tb_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initView();
        initData();
        initListener();
    }

    public void initView() {
        tb_main.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tb_main);
    }

    public void initData() {

    }

    public void initListener() {
        tb_main.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
