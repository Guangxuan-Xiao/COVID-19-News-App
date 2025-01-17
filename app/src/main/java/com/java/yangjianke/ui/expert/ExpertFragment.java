package com.java.yangjianke.ui.expert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.ButterKnife;
import com.java.yangjianke.MyApplication;
import com.java.yangjianke.R;
import com.java.yangjianke.ui.base.TabPagerAdapter;
import com.java.yangjianke.ui.channel.ChannelActivity;
import com.java.yangjianke.ui.channel.ChannelItem;
import com.java.yangjianke.ui.channel.ChannelManage;
import com.java.yangjianke.ui.expert.ExpertClassFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Comparator;

public class ExpertFragment extends Fragment implements ViewPager.OnPageChangeListener{

    private TabLayout mTabs;
    private ViewPager mViewPager;
    private ImageView mImgView;

    private String[] mTitles = {"高关注学者", "追忆学者"};
    private ExpertClassFragment[] mFragments;
    private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
    private TabPagerAdapter mAdapter;
    private int currentPosition = 0;

    public static ExpertFragment newInstance() {
        ExpertFragment fragment = new ExpertFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert, container, false);
        ButterKnife.bind(this, view);
        mTabs = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mImgView = (ImageView) view.findViewById(R.id.More_Column);

        setView();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 20) {
            if (resultCode == 21) {
                setView();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setView(){
        int cnt = 2;
        mFragments = new ExpertClassFragment[cnt];
        for (int i = 0; i < cnt; ++i){
            mFragments[i] = ExpertClassFragment.newInstance(i);
        }
        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mAdapter = new TabPagerAdapter(getChildFragmentManager(), mFragments);
        mAdapter.setTabTitles(mTitles);
        mViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
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

