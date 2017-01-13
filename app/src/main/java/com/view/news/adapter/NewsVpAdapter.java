package com.view.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.view.news.model.TabModel;

import java.util.ArrayList;

/**
 * Created by Destiny on 2017/1/4.
 */

public class NewsVpAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> list;
    private ArrayList<TabModel> tabModels;

    private int mChildCount;

    public NewsVpAdapter(FragmentManager fm, ArrayList<Fragment> list, ArrayList<TabModel> tabModels) {
        super(fm);
        this.list = list;
        this.tabModels = tabModels;
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return FragmentPagerAdapter.POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabModels.get(position).title;
    }
}
