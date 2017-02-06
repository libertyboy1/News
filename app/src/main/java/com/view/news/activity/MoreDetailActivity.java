package com.view.news.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.view.news.BaseActivity;
import com.view.news.R;
import com.view.news.adapter.MoreDetailVpAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.view.news.R.id.tv_number;


public class MoreDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_number)
    TextView tv_number;

    private String totalCount;
    private ArrayList<ImageView> imgs = new ArrayList<ImageView>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBar();
    }


    private void setToolBar() {
        tb_main.setTitle("详情");
        tb_main.setNavigationIcon(R.mipmap.back);
        tb_main.setBackgroundColor(Color.BLACK);
    }


    public void aaa() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail_more;
    }

    @Override
    public void initListener() {
        super.initListener();
        tb_main.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

        for (int i = 0; i < 6; i++) {
            ImageView sdv = new ImageView(this);
            sdv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            if (i % 2 == 0) {
                Glide.with(this).load("http://up.qqjia.com/z/u/a07/tu23085_2.jpg").into(sdv);
            } else {
                Glide.with(this).load("http://img1.gamedog.cn/2014/05/10/119-1405100931200-L.jpg").into(sdv);
            }

            imgs.add(sdv);
        }

        vpMain.setAdapter(new MoreDetailVpAdapter(imgs));
        vpMain.addOnPageChangeListener(this);

        totalCount = imgs.size() > 10 ? imgs.size() + "" : "0" + imgs.size();

        tv_number.setText("01 / " + totalCount);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (position > 10) {
            tv_number.setText((position + 1) + " / " + totalCount);
        } else {
            tv_number.setText("0" + (position + 1) + " / " + totalCount);
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
