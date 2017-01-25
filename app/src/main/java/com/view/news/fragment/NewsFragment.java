package com.view.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.view.news.BaseFragment;
import com.view.news.listener.EndlessRecyclerOnScrollListener;
import com.view.news.R;
import com.view.news.adapter.NewsRvAdapter;
import com.view.news.model.NewsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Destiny on 2017/1/4.
 */

public class NewsFragment extends BaseFragment {
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.srl_main)
    SwipeRefreshLayout srlMain;

    private View view;
    private ArrayList<NewsModel> models = new ArrayList<NewsModel>();
    private String label;

    private NewsRvAdapter adapter;

    private LinearLayoutManager manager;

    public NewsFragment(String label) {
        this.label = label;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recyclerview, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();
        return view;
    }

    private void initData() {

        for (int position = 0; position < 15; position++) {
            NewsModel model = new NewsModel();
            model.label = label;
            models.add(model);
        }

        //设置adapter
        adapter = new NewsRvAdapter(getContext(), models);
        rvMain.setAdapter(adapter);


    }

    private void initListener() {
        srlMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlMain.setRefreshing(false);
            }
        });

        rvMain.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                for (int position = 0; position < 15; position++) {
                    NewsModel model = new NewsModel();
                    model.label = label;
                    models.add(model);
                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void initView() {
        manager = new LinearLayoutManager(getContext());
        //设置布局管理器
        rvMain.setLayoutManager(manager);
        //设置Item增加、移除动画
        rvMain.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (adapter != null)
            adapter.stopBanner(isVisibleToUser);

    }

    public void setTop(){
        rvMain.smoothScrollToPosition(0);
    }

}
