package com.view.news.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.view.news.BaseActivity;
import com.view.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Destiny on 2017/1/24.
 */

public class SingleDetailActivity extends BaseActivity {
    @BindView(R.id.wv_main)
    public WebView wv_main;

    private String strUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail_single;
    }

    @Override
    public void initData() {
        super.initData();

        tb_main.setTitle("详情");
        tb_main.setNavigationIcon(R.mipmap.back);

        WebSettings settings = wv_main.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(false);  //将图片调整到适合webview的大小
        settings.setLoadsImagesAutomatically(true);
        wv_main.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv_main.requestFocusFromTouch();

        wv_main.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                strUrl = url;
                Log.e("SingleDetailActivity", strUrl);
                view.loadUrl(url);
                return true;
            }

        });

        wv_main.loadUrl("http://news.cri.cn/20170125/4c7fcc87-e10d-79da-3596-2bfa502afa25.html");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!"http://news.cri.cn/2017-01-25/4c7fcc87-e10d-79da-3596-2bfa502afa25.html".equals(strUrl)) {
                Log.e("SingleDetailActivity", "返回");
                wv_main.goBack();
            }else{
                Log.e("SingleDetailActivity", "结束");
                finish();
            }
        }
        return true;
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
