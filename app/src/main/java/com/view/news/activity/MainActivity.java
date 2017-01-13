package com.view.news.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.view.news.BaseActivity;
import com.view.news.R;
import com.view.news.View.PopWindowView;
import com.view.news.adapter.NewsVpAdapter;
import com.view.news.anim.DepthPageTransformer;
import com.view.news.fragment.NewsFragment;
import com.view.news.model.TabModel;
import com.view.news.utils.RotateUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.UserDictionary.Words.APP_ID;
import static com.view.news.R.array.taps;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.vp_main)
    public ViewPager vpMain;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.iv_tab_more)
    ImageView ivTabMore;
    @BindView(R.id.tab_FindFragment_title)
    TabLayout tl_title;
    @BindView(R.id.fl_tab_more)
    FrameLayout fl_tab_more;

    private PopWindowView pop;
    public ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private int currentPosition = 0;
    public NewsVpAdapter vpAdapter;
    public ArrayList<TabModel> tabModels = new ArrayList<TabModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        tb_main.setTitle("资讯");
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();

        pop = new PopWindowView(this);
        String[] tabs = getResources().getStringArray(taps);

        for (int position = 0; position < tabs.length; position++) {
            fragments.add(new NewsFragment(tabs[position]));
            TabModel model = new TabModel();
            model.title = tabs[position];
            model.isChecked = position == 0 ? true : false;
            tabModels.add(model);

            tl_title.addTab(tl_title.newTab().setText(tabs[0]));
        }

        vpAdapter = new NewsVpAdapter(getSupportFragmentManager(), fragments, tabModels);
        vpMain.setAdapter(vpAdapter);
        vpMain.setPageTransformer(true, new DepthPageTransformer());

        tl_title.setTabMode(TabLayout.MODE_SCROLLABLE);
        tl_title.setupWithViewPager(vpMain);

        pop.setItemClickListener(new PopWindowView.ItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (currentPosition == position) {
                    return;
                }
                tabModels.get(currentPosition).isChecked = false;
                vpMain.setCurrentItem(position);
            }
        });



    }

    @Override
    public void initListener() {
        super.initListener();
        vpMain.addOnPageChangeListener(this);

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

                int itemX = tl_title.getChildAt(0).getWidth() / tabModels.size() ;
                int selectX=itemX * tl_title.getSelectedTabPosition();

                if (tl_title.getChildAt(0).getWidth() > getWindowManager().getDefaultDisplay().getWidth()) {
                    tl_title.scrollTo(selectX-tl_title.getWidth()/2+itemX/2, 0);
                }

            }
        });

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                RotateUtils.rotateArrow(ivTabMore, true);
            }
        });

    }

    @OnClick(R.id.fl_tab_more)
    public void btnTabMoreClick(View view) {
        if (pop.isShow()) {
            RotateUtils.rotateArrow(ivTabMore, true);
            pop.dismiss();
        } else {
            RotateUtils.rotateArrow(ivTabMore, false);
            pop.show(line);
        }
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

    public void setChooseCurrentTab(int position) {
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (pop.isShow()) {
            if (pop.getCheckedAdapter().isShowDeleteBtn) {
                pop.getCheckedAdapter().isShowDeleteBtn = false;
                pop.getCheckedAdapter().notifyDataSetChanged();
            } else {
                pop.dismiss();
            }
        } else {
            finish();
        }

        return true;
    }
}
