package com.view.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Destiny on 2017/1/24.
 */

public class MoreDetailVpAdapter extends PagerAdapter {
    private ArrayList<ImageView> imgs;


    public MoreDetailVpAdapter(ArrayList<ImageView> imgs) {
        this.imgs = imgs;
    }


    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imgs.get(position), 0);//添加页卡
        return imgs.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imgs.get(position));//删除页卡
    }

}
