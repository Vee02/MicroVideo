package com.micro.microvideo.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.micro.microvideo.R;
import com.micro.microvideo.base.SimpleFragment;
import com.micro.microvideo.util.footbar.BottomBar;
import com.micro.microvideo.util.footbar.BottomBarTab;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by hboxs006 on 2017/10/18.
 */

public class MainFragment extends SimpleFragment {
    private final int FIRST = 0;
    private final int SECOND = 1;
    private final int THIRD = 2;
    private final int FOUR = 3;
    private final int FIVE = 4;

    private SupportFragment[] mFragments = new SupportFragment[5];

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i("json", "token : " + token);
        if (savedInstanceState == null) {
            mFragments[FIRST] = MemberFragment.newInstance();
            mFragments[SECOND] = MemberFragment.newInstance();
            mFragments[THIRD] = MemberFragment.newInstance();
            mFragments[FOUR] = MemberFragment.newInstance();
            mFragments[FIVE] = MemberFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOUR],
                    mFragments[FIVE]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findChildFragment(MemberFragment.class);
            mFragments[SECOND] = findChildFragment(MemberFragment.class);
            mFragments[THIRD] = findChildFragment(MemberFragment.class);
            mFragments[FOUR] = findChildFragment(MemberFragment.class);
            mFragments[FIVE] = findChildFragment(MemberFragment.class);
        }
        return mView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initEventAndData(View view) {
        bottomBar.addItem(new BottomBarTab(mContext, R.drawable.ic_main_home, R.drawable.ic_main_nav_home, "首页"))
                .addItem(new BottomBarTab(mContext, R.drawable.ic_main_integral, R.drawable.ic_main_nav_integral, "积分商城"))
                .addItem(new BottomBarTab(mContext, R.drawable.ic_main_member, R.drawable.ic_main_nav_member, "个人中心"))
                .addItem(new BottomBarTab(mContext, R.drawable.ic_main_member, R.drawable.ic_main_nav_member, "个人中心"))
                .addItem(new BottomBarTab(mContext, R.drawable.ic_main_member, R.drawable.ic_main_nav_member, "个人中心"));
        bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }
}
