package com.view.news.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.view.news.R;

import java.util.ArrayList;


/**
 * Created by Destiny on 2017/1/12.
 */

public class MainActivity2 extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private LinearLayout ll_tab;
    private ViewPager vp_main;
    private HorizontalScrollView hsv_main;

    private String tabs[] = new String[]{"热点", "精选", "军事", "娱乐", "糗事", "美女", "体育", "国际", "科技", "美术", "视频", "图片"};
    private ArrayList<TextView> tvs = new ArrayList<TextView>();

    private int offsetPixels;
    private int currentPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ll_tab = (LinearLayout) findViewById(R.id.ll_tab);
        vp_main = (ViewPager) findViewById(R.id.vp_main);
        hsv_main = (HorizontalScrollView) findViewById(R.id.hsv_main);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dip2px(35));

        TextView first = new TextView(this);
        first.setLayoutParams(params);
        first.setText(tabs[0]);
        first.setPadding(dip2px(10), 0, dip2px(10), 0);
        first.setVisibility(View.INVISIBLE);
        first.setTextSize(14);
        ll_tab.addView(first);

        for (int i = 0; i < tabs.length; i++) {
            TextView tab = new TextView(this);
            tab.setLayoutParams(params);
            tab.setText(tabs[i]);
            tab.setGravity(Gravity.BOTTOM);
            tab.setPadding(dip2px(10), 0, dip2px(10), 0);
            if (i == 0) {
                tab.setTextSize(22);
            } else {
                tab.setTextSize(14);
            }

            tab.setOnClickListener(this);

            ll_tab.addView(tab);


            TextView data = new TextView(this);
            data.setText(i + "");
            data.setTextSize(30);
            tvs.add(data);
        }

        TextView last = new TextView(this);
        last.setLayoutParams(params);
        last.setText(tabs[0]);
        last.setPadding(dip2px(10), 0, dip2px(10), 0);
        last.setVisibility(View.INVISIBLE);
        last.setTextSize(14);
        ll_tab.addView(last);


        vp_main.setAdapter(new MyViewPagerAdapter());
        vp_main.addOnPageChangeListener(this);

        hsv_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });

    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        /*********计算随着手指滑动距离，TAB控件所移动的偏移量***********/
        offsetPixels = (int) (ll_tab.getChildAt(0).getWidth() * positionOffset);

        /*********计算当前已选中TAB的位置***********/
        currentPixels = ll_tab.getChildAt(0).getWidth() * position;

        /*********移动视图***********/
        hsv_main.smoothScrollTo(currentPixels + offsetPixels, 0);

        /*********根据手指滑动距离，动态改变字体大小***********/
        ((TextView) ll_tab.getChildAt(position + 1)).setTextSize(22 - 8 * positionOffset);
        ((TextView) ll_tab.getChildAt(position + 2)).setTextSize(14 + 8 * positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
    }


    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return tvs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(tvs.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(tvs.get(position));
            return tvs.get(position);
        }

    }


}
