package com.view.news.presenter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.PopupWindow;

import com.view.news.R;
import com.view.news.view.PopWindowView;
import com.view.news.activity.MainActivity;
import com.view.news.adapter.NewsVpAdapter;
import com.view.news.anim.DepthPageTransformer;
import com.view.news.fragment.NewsFragment;
import com.view.news.model.TabModel;
import com.view.news.utils.RotateUtils;

import java.util.ArrayList;

import static com.view.news.R.array.taps;

/**
 * Created by Destiny on 2017/1/24.
 */

public class MainPresenterImpl implements MainPresenter, ViewPager.OnPageChangeListener {
    private MainActivity mainActivity;

    public ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    public int currentPosition = 0;
    public NewsVpAdapter vpAdapter;
    public ArrayList<TabModel> tabModels = new ArrayList<TabModel>();
    public PopWindowView pop;

    public MainPresenterImpl(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void initData() {
        pop = new PopWindowView(mainActivity);
        String[] tabs = mainActivity.getResources().getStringArray(taps);

        for (int position = 0; position < tabs.length; position++) {
            fragments.add(new NewsFragment(tabs[position]));
            TabModel model = new TabModel();
            model.title = tabs[position];
            model.isChecked = position == 0 ? true : false;
            tabModels.add(model);

            mainActivity.tl_title.addTab(mainActivity.tl_title.newTab().setText(tabs[0]));
        }

        vpAdapter = new NewsVpAdapter(mainActivity.getSupportFragmentManager(), fragments, tabModels);
        mainActivity.vpMain.setAdapter(vpAdapter);
        mainActivity.vpMain.setPageTransformer(true, new DepthPageTransformer());

        mainActivity.tl_title.setTabMode(TabLayout.MODE_SCROLLABLE);
        mainActivity.tl_title.setupWithViewPager(mainActivity.vpMain);

        pop.setItemClickListener(new PopWindowView.ItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (currentPosition == position) {
                    return;
                }
                tabModels.get(currentPosition).isChecked = false;
                mainActivity.vpMain.setCurrentItem(position);
            }
        });
    }

    @Override
    public void initListener() {
        mainActivity.vpMain.addOnPageChangeListener(this);

        pop.setItemMoveListener(new PopWindowView.ItemMoveListener() {
            @Override
            public void onMove(int currentPosition, int movePosition) {
                TabModel model = tabModels.get(currentPosition);
                tabModels.remove(model);
                tabModels.add(movePosition, model);

                Fragment mFragment = fragments.get(currentPosition);
                fragments.remove(mFragment);
                fragments.add(movePosition, mFragment);

                vpAdapter.notifyDataSetChanged();

                int itemX = mainActivity.tl_title.getChildAt(0).getWidth() / tabModels.size();
                int selectX = itemX * mainActivity.tl_title.getSelectedTabPosition();

                if (mainActivity.tl_title.getChildAt(0).getWidth() > mainActivity.getWindowManager().getDefaultDisplay().getWidth()) {
                    mainActivity.tl_title.scrollTo(selectX - mainActivity.tl_title.getWidth() / 2 + itemX / 2, 0);
                }

            }
        });

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                RotateUtils.rotateArrow(mainActivity.ivTabMore, true);
            }
        });
    }

    @Override
    public void btnTabMoreClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top:
                ((NewsFragment) fragments.get(currentPosition)).setTop();
                break;
            case R.id.fl_tab_more:
                if (pop.isShow()) {
                    RotateUtils.rotateArrow(mainActivity.ivTabMore, true);
                    pop.dismiss();
                } else {
                    RotateUtils.rotateArrow(mainActivity.ivTabMore, false);
                    pop.show(mainActivity.line);
                }
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (pop.isShow()) {
            if (pop.getCheckedAdapter().isShowDeleteBtn) {
                pop.getCheckedAdapter().isShowDeleteBtn = false;
                pop.getCheckedAdapter().notifyDataSetChanged();
            } else {
                pop.dismiss();
            }
        } else {
            mainActivity.finish();
        }
        return true;
    }

    private void setChooseCurrentTab(int position) {
        currentPosition = position;
        pop.setCurrentPosition(currentPosition);

        if (position > 0) {
            tabModels.get(position - 1).isChecked = false;
        }
        if (position < tabModels.size() - 1) {
            tabModels.get(position + 1).isChecked = false;
        }
        tabModels.get(position).isChecked = true;

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setChooseCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
