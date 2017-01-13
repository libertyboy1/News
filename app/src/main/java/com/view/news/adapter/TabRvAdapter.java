package com.view.news.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.view.news.R;
import com.view.news.model.TabModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Destiny on 2017/1/4.
 */

public class TabRvAdapter extends RecyclerView.Adapter<TabRvAdapter.NoViewHolder> {
    private ArrayList<TabModel> tabs;
    private Context mContext;
    public boolean isShowDeleteBtn = false;

    private MyItemClickListener mListener;
    private MyItemLongClickListener mLongClickListener;

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface MyItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public TabRvAdapter(Context mContext, ArrayList<TabModel> tabs) {
        this.tabs = tabs;
        this.mContext = mContext;
    }


    @Override
    public NoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_tab, parent, false));
    }

    @Override
    public void onBindViewHolder(NoViewHolder holder, int position) {
        holder.tv_tab.setText(tabs.get(position).title);
        if (tabs.get(position).isChecked) {
            holder.tv_tab.setBackgroundResource(R.drawable.btn_tab_checked_bg);
            holder.tv_tab.setTextColor(Color.WHITE);
        } else {
            holder.tv_tab.setBackgroundResource(R.drawable.btn_tab_bg);
            holder.tv_tab.setTextColor(Color.BLACK);
        }

        if (isShowDeleteBtn) {
            holder.iv_delete.setVisibility(View.VISIBLE);
        } else {
            holder.iv_delete.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if (tabs != null)
            return tabs.size();
        return 0;
    }

    public class NoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.tv_tab)
        public TextView tv_tab;
        @BindView(R.id.iv_delete)
        public ImageView iv_delete;

        public NoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            iv_delete.setOnClickListener(this);
        }

        /**
         * 点击监听
         */
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }

        /**
         * 长按监听
         */
        @Override
        public boolean onLongClick(View arg0) {
            if (mLongClickListener != null) {
                mLongClickListener.onItemLongClick(arg0, getPosition());
            }
            return true;
        }

    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mListener = listener;
    }

    public void setOnItemLongClickListener(MyItemLongClickListener listener) {
        this.mLongClickListener = listener;
    }

}
