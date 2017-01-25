package com.view.news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.view.news.R;


/**
 * Created by Destiny on 2017/1/6.
 */

public class BottomView extends FrameLayout implements View.OnClickListener{
    private View view;
    private ImageView iv_comment;
    private ImageView iv_collect;
    private ImageView iv_share;

    public BottomView(Context context) {
        super(context);
        initView();
    }

    public BottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_view, null);
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        iv_comment= (ImageView) view.findViewById(R.id.iv_comment);
        iv_collect= (ImageView) view.findViewById(R.id.iv_collect);
        iv_share= (ImageView) view.findViewById(R.id.iv_share);
        initListener();
        addView(view);
    }

    private void initListener(){
        iv_comment.setOnClickListener(this);
        iv_collect.setOnClickListener(this);
        iv_share.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_comment:
                Log.e("---","点击评论按钮");
                break;
            case R.id.iv_collect:
                Log.e("---","点击收藏按钮");
                break;
            case R.id.iv_share:
                Log.e("---","点击分享按钮");
                break;
        }
    }
}
