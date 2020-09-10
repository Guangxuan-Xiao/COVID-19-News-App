package com.example.covid_news.ui.expert;

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
import com.example.covid_news.MyApplication;
import com.example.covid_news.R;
import com.example.covid_news.ui.base.TabPagerAdapter;
import com.example.covid_news.ui.channel.ChannelActivity;
import com.example.covid_news.ui.channel.ChannelItem;
import com.example.covid_news.ui.channel.ChannelManage;
import com.example.covid_news.ui.expert.ExpertClassFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Comparator;

public class ExpertFragment extends Fragment implements ViewPager.OnPageChangeListener{

    private TabLayout mTabs;
    private ViewPager mViewPager;
    private ImageView mImgView;
    private FloatingActionButton fab;

    private String[] mTitles;
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
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_channel = new Intent(getContext(), ChannelActivity.class);
                startActivityForResult(intent_channel, 20);
            }
        });

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
        userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(MyApplication.getApp().getSQLHelper()).getUserChannel());
        int cnt = userChannelList.size();
        userChannelList.sort(new Comparator<ChannelItem>() {
            @Override
            public int compare(ChannelItem t1, ChannelItem t2) {
                return t1.getOrderId() - t2.getOrderId();
            }
        });
        mTitles = new String[cnt];
        mFragments = new ExpertClassFragment[cnt];
        for (int i = 0; i < cnt; ++i){
            int idx = userChannelList.get(i).getId();
            mTitles[i] = userChannelList.get(i).getName();
            mFragments[i] = ExpertClassFragment.newInstance(idx - 1);
            System.out.println(idx);
            System.out.println(mTitles[i]);
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

