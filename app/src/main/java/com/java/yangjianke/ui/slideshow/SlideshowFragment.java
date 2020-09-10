package com.java.yangjianke.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import butterknife.ButterKnife;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.java.yangjianke.R;
import com.java.yangjianke.ui.base.TabPagerAdapter;
import com.java.yangjianke.ui.channel.ChannelActivity;
import com.java.yangjianke.ui.news.NewsClassFragment;
import com.java.yangjianke.ui.news.NewsSearchActivity;

import java.io.*;
import java.util.ArrayList;

public class SlideshowFragment extends Fragment implements ViewPager.OnPageChangeListener{
    private TabLayout mTabs;
    private ViewPager mViewPager;
    private String[] mTitles = {"临床症状", "传染模型", "致病机理", "病毒检测", "国际新闻", "世卫组织", "中国力量"};
    private EventClassFragment[] mFragments;
    private TabPagerAdapter mAdapter;
    private int currentPosition = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        mTabs = (TabLayout) view.findViewById(R.id.tab_layout_cluster);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPagerCluster);
        int cnt = 7;
        mFragments = new EventClassFragment[cnt];
        for (int i = 0; i < cnt; ++i){
            mFragments[i] = EventClassFragment.newInstance(i);
        }
        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mAdapter = new TabPagerAdapter(getChildFragmentManager(), mFragments);
        mAdapter.setTabTitles(mTitles);
        mViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);

        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}