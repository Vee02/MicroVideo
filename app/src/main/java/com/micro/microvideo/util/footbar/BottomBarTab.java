package com.micro.microvideo.util.footbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.micro.microvideo.R;


/**
 * Created by YoKeyword on 16/6/3.
 */
public class BottomBarTab extends FrameLayout {
    private ImageView mImageView;
    private TextView mTvTitle;
    private Context mContext;
    private int mTabPosition = -1;
    private int mIcon;
    private int mUnIcon;


    private TextView mTvUnreadCount;



    public BottomBarTab(Context context, @DrawableRes int icon, @DrawableRes int unIcon, CharSequence title) {
        this(context, null, icon, unIcon, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int icon, int unIcon,CharSequence title) {
        this(context, attrs, 0, icon,unIcon, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon,int unIcon, CharSequence title) {
        super(context, attrs, defStyleAttr);
        init(context, icon, unIcon, title);
    }

    private void init(Context context, int icon,int unIcon, CharSequence title) {
        mContext = context;
        mIcon = icon;
        mUnIcon = unIcon;

        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackgroundDrawable(drawable);
        typedArray.recycle();

        LinearLayout lLContainer = new LinearLayout(context);
        lLContainer.setOrientation(LinearLayout.VERTICAL);
        lLContainer.setGravity(Gravity.CENTER);
        LayoutParams paramsContainer = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsContainer.gravity = Gravity.CENTER;
        lLContainer.setLayoutParams(paramsContainer);

        mImageView = new ImageView(context);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        mImageView.setImageResource(unIcon);
        mImageView.setLayoutParams(params);
//        mIcon.setColorFilter(ContextCompat.getColor(context, R.color.tab_unselect));
        lLContainer.addView(mImageView);

        mTvTitle = new TextView(context);
        mTvTitle.setText(title);
        LinearLayout.LayoutParams paramsTv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTv.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        mTvTitle.setTextSize(15);
        mTvTitle.setTextColor(ContextCompat.getColor(context, R.color.tab_unselect));
        mTvTitle.setLayoutParams(paramsTv);
        lLContainer.addView(mTvTitle);

        addView(lLContainer);

        int min = dip2px(context, 20);
        int padding = dip2px(context, 2);
        mTvUnreadCount = new TextView(context);
        mTvUnreadCount.setBackgroundResource(R.drawable.bg_bar_msg);
        mTvUnreadCount.setMinWidth(min);
        mTvUnreadCount.setTextColor(Color.WHITE);
        mTvUnreadCount.setPadding(padding, 0, padding, 0);
        mTvUnreadCount.setGravity(Gravity.CENTER);
        LayoutParams tvUnReadParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, min);
        tvUnReadParams.gravity = Gravity.CENTER;
        tvUnReadParams.leftMargin = dip2px(context, 17);
        tvUnReadParams.bottomMargin = dip2px(context, 14);
        mTvUnreadCount.setLayoutParams(tvUnReadParams);
        mTvUnreadCount.setVisibility(GONE);

        addView(mTvUnreadCount);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, mIcon));
            mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_red));
        } else {
            mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, mUnIcon));
            mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unselect));
        }
    }

    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }

    /**
     * 设置未读数量
     */
    public void setUnreadCount(int num) {
        if (num <= 0) {
            mTvUnreadCount.setText(String.valueOf(0));
            mTvUnreadCount.setVisibility(GONE);
        } else {
            mTvUnreadCount.setVisibility(VISIBLE);
            if (num > 99) {
                mTvUnreadCount.setText("99+");
            } else {
                mTvUnreadCount.setText(String.valueOf(num));
            }
        }
    }

    /**
     * 获取当前未读数量
     */
    public int getUnreadCount() {
        int count = 0;
        if (TextUtils.isEmpty(mTvUnreadCount.getText())) {
            return count;
        }
        if (mTvUnreadCount.getText().toString().equals("99+")) {
            return 99;
        }
        try {
            count = Integer.valueOf(mTvUnreadCount.getText().toString());
        } catch (Exception ignored) {
        }
        return count;
    }

    private int dip2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
