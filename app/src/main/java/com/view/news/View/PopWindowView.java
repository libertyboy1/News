package com.view.news.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.view.news.R;
import com.view.news.activity.MainActivity;
import com.view.news.adapter.TabRvAdapter;
import com.view.news.fragment.NewsFragment;
import com.view.news.model.TabModel;

import java.util.ArrayList;

import static com.view.news.R.array.taps;

/**
 * Created by Destiny on 2017/1/5.
 */

public class PopWindowView {
    private PopupWindow popupwindow;
    private RecyclerView rv_checked;
    private RecyclerView rv_unchecked;

    private Context mContext;
    private MainActivity mActivity;
    private TabRvAdapter checkAdapter, uncheckAdapter;
    private ItemClickListener itemClickListener;
    private ItemMoveListener itemMoveListener;
    private ArrayList<TabModel> checkTabs = new ArrayList<TabModel>();
    private ArrayList<TabModel> unCheckTabs = new ArrayList<TabModel>();
    private int currentPosition;

    public interface ItemClickListener {
        void onItemClickListener(View view, int position);
    }

    public interface ItemMoveListener {
        void onMove(int currentPosition, int movePosition);
    }

    public PopWindowView(Context mContext) {
        this.mContext = mContext;
        this.mActivity=(MainActivity)mContext;
        initPopupWindowView();
    }

    private void initPopupWindowView() {

        // // 获取自定义布局文件pop.xml的视图
        View customView = LayoutInflater.from(mContext).inflate(R.layout.popwindow, null, false);
        rv_checked = (RecyclerView) customView.findViewById(R.id.rv_checked);
        rv_unchecked = (RecyclerView) customView.findViewById(R.id.rv_unchecked);

        initRecycleListView();

        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);
        popupwindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 自定义view添加触摸事件
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                }
                return false;
            }
        });

    }

    public void show(View v) {
        popupwindow.showAsDropDown(v);
    }

    private void initRecycleListView() {
        //设置布局管理器
        rv_checked.setLayoutManager(new FullyGridLayoutManager(mContext, 4));
        //设置Item增加、移除动画
        rv_checked.setItemAnimator(new DefaultItemAnimator());

        checkTabs.clear();
        unCheckTabs.clear();

        String[] checkTabs = mContext.getResources().getStringArray(taps);
        for (int position = 0; position < checkTabs.length; position++) {
            TabModel model = new TabModel();
            model.title = checkTabs[position];
            model.isChecked = position == currentPosition ? true : false;
            this.checkTabs.add(model);
        }

        checkAdapter = new TabRvAdapter(mContext, this.checkTabs);
        rv_checked.setAdapter(checkAdapter);

        //设置布局管理器
        rv_unchecked.setLayoutManager(new FullyGridLayoutManager(mContext, 4));
        //设置Item增加、移除动画
        rv_unchecked.setItemAnimator(new DefaultItemAnimator());

        for (String title : mContext.getResources().getStringArray(taps)) {
            TabModel model = new TabModel();
            model.title = title;
            model.isChecked = false;
            unCheckTabs.add(model);
        }

        uncheckAdapter = new TabRvAdapter(mContext, unCheckTabs);
        uncheckAdapter.isShowDeleteBtn=false;
        rv_unchecked.setAdapter(uncheckAdapter);

        initRvListener();
    }

    public void initRvListener() {

        checkAdapter.setOnItemClickListener(new TabRvAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (itemClickListener != null) {
                   if (checkAdapter.isShowDeleteBtn){
                       TabModel model=checkTabs.get(position);
                       model.isChecked=false;
                       unCheckTabs.add(model);
                       checkTabs.remove(position);
                       mActivity.getPreseneter().tabModels.remove(position);
                       mActivity.getPreseneter().fragments.remove(position);
                       mActivity.getPreseneter().vpAdapter.notifyDataSetChanged();
                       checkAdapter.notifyItemRemoved(position);
                       uncheckAdapter.notifyItemInserted(unCheckTabs.size()-1);
                   }else{
                       checkTabs.get(currentPosition).isChecked=false;
                       checkTabs.get(position).isChecked=true;
                       checkAdapter.notifyDataSetChanged();
                       itemClickListener.onItemClickListener(view, position);
                   }
                }
            }
        });
        checkAdapter.setOnItemLongClickListener(new TabRvAdapter.MyItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                checkAdapter.isShowDeleteBtn=true;
                checkAdapter.notifyDataSetChanged();
            }
        });

        uncheckAdapter.setOnItemClickListener(new TabRvAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                checkTabs.add(unCheckTabs.get(position));
                mActivity.getPreseneter().fragments.add(new NewsFragment(unCheckTabs.get(position).title));
                mActivity.getPreseneter().tabModels.add(unCheckTabs.get(position));

                mActivity.getPreseneter().vpAdapter.notifyDataSetChanged();
                unCheckTabs.remove(position);
                uncheckAdapter.notifyItemRemoved(position);
                checkAdapter.notifyItemInserted(checkTabs.size()-1);
            }
        });
        uncheckAdapter.setOnItemLongClickListener(new TabRvAdapter.MyItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        //为RecycleView绑定触摸事件
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //首先回调的方法 返回int表示是否监听该方向
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//拖拽
                int swipeFlags = 0;//侧滑删除
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                if (itemMoveListener != null) {
                    itemMoveListener.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                }

                //滑动事件
                TabModel model = checkTabs.get(viewHolder.getAdapterPosition());
                checkTabs.remove(model);
                checkTabs.add(target.getAdapterPosition(), model);
                checkAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public boolean isLongPressDragEnabled() {
                //是否可拖拽
                return true;
            }
        });
        helper.attachToRecyclerView(rv_checked);

    }

    public boolean isShow() {
        return popupwindow.isShowing();
    }

    public void dismiss() {
        popupwindow.dismiss();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemMoveListener(ItemMoveListener itemMoveListener) {
        this.itemMoveListener = itemMoveListener;
    }

    public void setCurrentPosition(int currentPosition) {
        checkTabs.get(this.currentPosition).isChecked=false;
        this.currentPosition = currentPosition;
        checkTabs.get(currentPosition).isChecked=true;
        checkAdapter.notifyDataSetChanged();
    }

    public TabRvAdapter getCheckedAdapter(){
        return checkAdapter;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener){
        popupwindow.setOnDismissListener(onDismissListener);
    }

}
