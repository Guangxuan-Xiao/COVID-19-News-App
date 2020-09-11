package com.java.yangjianke.ui.slideshow;

import android.content.Intent;
import android.content.res.AssetManager;
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
import com.java.yangjianke.data.Event;
import com.java.yangjianke.data.Parser;
import com.java.yangjianke.ui.base.TabPagerAdapter;
import com.java.yangjianke.ui.channel.ChannelActivity;
import com.java.yangjianke.ui.news.NewsClassFragment;
import com.java.yangjianke.ui.news.NewsSearchActivity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment implements ViewPager.OnPageChangeListener{
    private TabLayout mTabs;
    private ViewPager mViewPager;
    private ImageView mImg;
    private String[] mTitles = {"聚类结果", "临床症状", "传染模型", "致病机理", "病毒检测", "国际新闻", "世卫组织", "中国力量"};
    private Fragment[] mFragments;
    private TabPagerAdapter mAdapter;
    private int currentPosition = 0;
    public List<List<Event>> eventList;

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
        AssetManager assetManager = getActivity().getAssets();
        List<Event> allEvent = new ArrayList<Event>();
        eventList = new ArrayList<List<Event>>();
        try {
            InputStream is = assetManager.open("events_list.json");
            String jsonData = readDataFromInputStream(is);
            Parser p = new Parser();
            allEvent = p.parseEventList(jsonData);
            for (int i = 0; i < cnt; ++i){
                eventList.add(new ArrayList<Event>());
            }
            for (Event e: allEvent){
                eventList.get(e.community-1).add(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mFragments = new Fragment[cnt+1];
        mFragments[0] = new ClusterResultFragment();
        for (int i = 0; i < cnt; ++i){
            mFragments[i+1] = EventClassFragment.newInstance(i, eventList.get(i));
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

    public String readDataFromInputStream(InputStream is) {
        BufferedInputStream bis = new BufferedInputStream(is);

        int c = 0;
        byte[] buf = new byte[64];
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                c = bis.read(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (c == -1)
                break;
            else {
                try {
                    sb.append(new String(buf, 0, c, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}