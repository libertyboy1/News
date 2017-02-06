package com.view.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.view.news.BaseActivity;
import com.view.news.R;
import com.view.news.adapter.NewsVpAdapter;
import com.view.news.anim.DepthPageTransformer;
import com.view.news.fragment.NewsFragment;
import com.view.news.listener.AppBarStateChangeListener;
import com.view.news.model.TabModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;

import static android.R.id.tabs;
import static com.view.news.R.array.taps;
import static com.view.news.R.id.slider;

/**
 * @项目名称：News
 * @包名：com.view.news.activity
 * @版本号：v0.1
 * @创建人：苏奥博
 * @创建时间：2017/2/3 9:22
 * @修改人：
 * @修改时间：
 * @类描述:
 */
public class TextActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener {
    private Toolbar tb_main;
    private ViewPager vpMain;
    private TabLayout tl_title;
    private CollapsingToolbarLayout ctbl_main;
    private SliderLayout slider;
    private AppBarLayout abl_main;
    private ImageView iv_back;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private ArrayList<TabModel> tabModels = new ArrayList<TabModel>();
    private ArrayList<SliderLayout.Transformer> transformers = new ArrayList();

    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapsingtoolbar);

        tb_main = (Toolbar) findViewById(R.id.tb_main);
        vpMain = (ViewPager) findViewById(R.id.vp_main);
        tl_title = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        ctbl_main = (CollapsingToolbarLayout) findViewById(R.id.ctbl_main);
        slider = (SliderLayout) findViewById(R.id.slider);
        abl_main = (AppBarLayout) findViewById(R.id.abl_main);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        initTransformer();
        initBanner();
        initViewPager();
        initListener();
    }

    private void initListener() {

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        abl_main.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    ctbl_main.setTitle("");
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    ctbl_main.setTitle("乐新闻");
                } else {
                    //中间状态
                    ctbl_main.setTitle("");
                }
            }
        });

    }


    private void initViewPager() {
        String[] tabs = getResources().getStringArray(taps);

        for (int position = 0; position < tabs.length; position++) {
            fragments.add(new NewsFragment(tabs[position]));
            TabModel model = new TabModel();
            model.title = tabs[position];
            model.isChecked = position == 0 ? true : false;
            tabModels.add(model);

            tl_title.addTab(tl_title.newTab().setText(tabs[0]));
        }

        NewsVpAdapter vpAdapter = new NewsVpAdapter(getSupportFragmentManager(), fragments, tabModels);
        vpMain.setAdapter(vpAdapter);
        vpMain.setPageTransformer(true, new DepthPageTransformer());

        tl_title.setTabMode(TabLayout.MODE_SCROLLABLE);
        tl_title.setupWithViewPager(vpMain);


        tb_main.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ctbl_main.setTitle("乐新闻");
        ctbl_main.setExpandedTitleColor(Color.WHITE);
        ctbl_main.setCollapsedTitleTextColor(Color.WHITE);
        tb_main.setNavigationIcon(R.mipmap.back);

    }

    private void initBanner() {

        slider.removeAllSliders();

        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("刀锋之影 · 泰隆", "http://att.bbs.duowan.com/forum/201308/29/141904z2q0zp08qjpiywe2.jpg");
        url_maps.put("莲花之令 · 艾瑞莉娅", "http://tupian.enterdesk.com/2015/gha/0800/0802/01.jpg");
        url_maps.put("寒冰射手 · 艾希", "http://pic1.win4000.com/wallpaper/5/516e5cea0e7ea.jpg");
        url_maps.put("战争女神 · 希维尔", "http://tupian.enterdesk.com/2015/gha/0700/63/03.jpg");
        url_maps.put("皮城女警 · 凯瑟琳", "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1212/06/c1/16393449_1354779112183.jpg");
        url_maps.put("琴瑟仙女 · 娑娜", "http://img.tuwandata.com/v2/thumb/all/NzMyZiwwLDAsNCwzLDEsLTEsMSw=/u/www.tuwan.com/uploads/allimg/1407/07/710-140FG50Q7.jpg");


        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Intent intent = new Intent(TextActivity.this, SingleDetailActivity.class);
                            startActivity(intent);
                        }
                    });

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            slider.addSlider(textSliderView);
        }

        slider.setPresetTransformer(transformers.get(random.nextInt(transformers.size())));
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        slider.setCustomAnimation(new DescriptionAnimation());
        slider.setDuration(4000);
        slider.addOnPageChangeListener(this);
    }

    private void initTransformer() {
        transformers.add(SliderLayout.Transformer.Accordion);
        transformers.add(SliderLayout.Transformer.Background2Foreground);
        transformers.add(SliderLayout.Transformer.CubeIn);
        transformers.add(SliderLayout.Transformer.DepthPage);
        transformers.add(SliderLayout.Transformer.Fade);
        transformers.add(SliderLayout.Transformer.FlipHorizontal);
        transformers.add(SliderLayout.Transformer.FlipPage);
        transformers.add(SliderLayout.Transformer.Foreground2Background);
        transformers.add(SliderLayout.Transformer.RotateDown);
        transformers.add(SliderLayout.Transformer.RotateUp);
        transformers.add(SliderLayout.Transformer.Stack);
        transformers.add(SliderLayout.Transformer.Tablet);
        transformers.add(SliderLayout.Transformer.ZoomIn);
        transformers.add(SliderLayout.Transformer.ZoomOut);
        transformers.add(SliderLayout.Transformer.ZoomOutSlide);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int i = random.nextInt(transformers.size());
        slider.setPresetTransformer(transformers.get(i));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
