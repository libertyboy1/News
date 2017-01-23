package com.view.news.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.view.news.R;
import com.view.news.model.NewsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.M;
import static com.view.news.R.id.tv_title;

/**
 * Created by Destiny on 2017/1/4.
 */

public class NewsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<NewsModel> newsModels;
    private Context mContext;
    private final int BANNER = 0;
    private final int SINGLE_IMAGE = 1;
    private final int MORE_IMAGE = 2;
    private final int NO_IMAGE = 3;

    private ArrayList<SliderLayout.Transformer> transformers = new ArrayList();
    private Random random = new Random();

    private BannerViewHolder holder;


    public NewsRvAdapter(Context mContext, ArrayList<NewsModel> newsModels) {
        this.newsModels = newsModels;
        this.mContext = mContext;
        initTransformer();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return BANNER;
        } else if (position % 5 == 0) {
            return NO_IMAGE;
        } else if (position % 3 == 0) {
            return SINGLE_IMAGE;
        } else {
            return MORE_IMAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            holder = new BannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_new_banner, parent, false));
            return holder;
        } else if (viewType == NO_IMAGE) {
            return new NoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news_no, parent, false));
        } else if (viewType == MORE_IMAGE) {
            return new SingleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news_single, parent, false));
        } else if (viewType == SINGLE_IMAGE) {
            return new MoreViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news_more, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof BannerViewHolder) {

        } else if (holder instanceof SingleViewHolder) {
            ((SingleViewHolder) holder).tv_title.setText(newsModels.get(position).title);
            ((SingleViewHolder) holder).tv_source.setText(newsModels.get(position).label);
            ((SingleViewHolder) holder).tv_time.setText(newsModels.get(position).time);
            Glide.with(mContext).load(newsModels.get(position).image).into(((SingleViewHolder) holder).iv_image);
        } else if (holder instanceof MoreViewHolder) {
            ((MoreViewHolder) holder).tv_title.setText(newsModels.get(position).title);
            ((MoreViewHolder) holder).tv_source.setText(newsModels.get(position).label);
            ((MoreViewHolder) holder).tv_time.setText(newsModels.get(position).time);
            Glide.with(mContext).load(newsModels.get(position).image).into(((MoreViewHolder) holder).iv_01);
            Glide.with(mContext).load(newsModels.get(position).image).into(((MoreViewHolder) holder).iv_02);
            Glide.with(mContext).load(newsModels.get(position).image).into(((MoreViewHolder) holder).iv_03);
        } else if (holder instanceof NoViewHolder) {
            ((NoViewHolder) holder).tv_title.setText(newsModels.get(position).title);
            ((NoViewHolder) holder).tv_source.setText(newsModels.get(position).label);
            ((NoViewHolder) holder).tv_time.setText(newsModels.get(position).time);
        }
    }

    @Override
    public int getItemCount() {
        if (newsModels != null)
            return newsModels.size();
        return 0;
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        public TextView tv_title;
        @BindView(R.id.tv_source)
        public TextView tv_source;
        @BindView(R.id.tv_time)
        public TextView tv_time;
        @BindView(R.id.iv_image)
        public ImageView iv_image;

        public SingleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class MoreViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        public TextView tv_title;
        @BindView(R.id.tv_source)
        public TextView tv_source;
        @BindView(R.id.tv_time)
        public TextView tv_time;
        @BindView(R.id.iv_01)
        public ImageView iv_01;
        @BindView(R.id.iv_02)
        public ImageView iv_02;
        @BindView(R.id.iv_03)
        public ImageView iv_03;

        public MoreViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class NoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        public TextView tv_title;
        @BindView(R.id.tv_source)
        public TextView tv_source;
        @BindView(R.id.tv_time)
        public TextView tv_time;

        public NoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder implements ViewPagerEx.OnPageChangeListener {
        @BindView(R.id.slider)
        public SliderLayout slider;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            HashMap<String, String> url_maps = new HashMap<String, String>();
            url_maps.put("刀锋之影 · 泰隆", "http://att.bbs.duowan.com/forum/201308/29/141904z2q0zp08qjpiywe2.jpg");
            url_maps.put("莲花之令 · 艾瑞莉娅", "http://tupian.enterdesk.com/2015/gha/0800/0802/01.jpg");
            url_maps.put("寒冰射手 · 艾希", "http://pic1.win4000.com/wallpaper/5/516e5cea0e7ea.jpg");
            url_maps.put("战争女神 · 希维尔", "http://tupian.enterdesk.com/2015/gha/0700/63/03.jpg");
            url_maps.put("皮城女警 · 凯瑟琳", "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1212/06/c1/16393449_1354779112183.jpg");
            url_maps.put("琴瑟仙女 · 娑娜", "http://img.tuwandata.com/v2/thumb/all/NzMyZiwwLDAsNCwzLDEsLTEsMSw=/u/www.tuwan.com/uploads/allimg/1407/07/710-140FG50Q7.jpg");


            for (String name : url_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(mContext);
                textSliderView
                        .description(name)
                        .image(url_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {
                                Toast.makeText(mContext, slider.getBundle().getString("extra"), Toast.LENGTH_SHORT).show();
                            }
                        });

                //add your extra information
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

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int i = random.nextInt(transformers.size());
            Log.e("BannerViewHolder", "i:" + i);
            slider.setPresetTransformer(transformers.get(i));

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
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

    public void stopBanner(boolean isStop) {
        if (isStop){
            holder.slider.startAutoCycle();
        }else{
            holder.slider.stopAutoCycle();
        }
    }

}
