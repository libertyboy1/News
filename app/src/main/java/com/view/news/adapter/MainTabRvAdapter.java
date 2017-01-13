package com.view.news.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.view.news.R;
import com.view.news.model.TabModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Destiny on 2017/1/5.
 */

public class MainTabRvAdapter extends RecyclerView.Adapter<MainTabRvAdapter.MyViewHolder> {
    private ArrayList<TabModel> tabs;
    private Context mContext;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void itemClick(View view,int position);
    }

    public MainTabRvAdapter(Context mContext, ArrayList<TabModel> tabs) {
        this.tabs = tabs;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main_tab, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_title.setText(tabs.get(position).title);
        if (tabs.get(position).isChecked){
            holder.tv_title.setTextColor(Color.parseColor("#87CEEB"));
        }else{
            holder.tv_title.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        if (tabs!=null)
            return tabs.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_title)
        public TextView tv_title;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener!=null){
                itemClickListener.itemClick(v,getPosition());
            }
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

}
