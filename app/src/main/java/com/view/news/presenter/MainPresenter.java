package com.view.news.presenter;

import android.view.KeyEvent;
import android.view.View;

/**
 * Created by Destiny on 2017/1/24.
 */

public interface MainPresenter {
    void initData();
    void initListener();
    void btnTabMoreClick(View view);
    boolean onKeyDown(int keyCode, KeyEvent event);
}
