package com.example.covid_news.ui.news;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.*;
import com.example.covid_news.MyApplication;
import com.example.covid_news.ui.channel.ChannelActivity;
import com.example.covid_news.ui.channel.ChannelItem;
import com.example.covid_news.ui.channel.ChannelManage;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.example.covid_news.R;
import com.example.covid_news.ui.base.TabPagerAdapter;
import com.example.covid_news.ui.news.NewsClassFragment;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.Comparator;

public class NewsFragment extends Fragment implements ViewPager.OnPageChangeListener{

    private TabLayout mTabs;
    private ViewPager mViewPager;
    private ImageView mImgView;
    private SearchView mSearchView;
    private FloatingActionButton fab;

    private String[] mTitles;
    private NewsClassFragment[] mFragments;
    private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
    private TabPagerAdapter mAdapter;
    private int currentPosition = 0;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        mTabs = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mImgView = (ImageView) view.findViewById(R.id.More_Column);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        mSearchView = (SearchView) view.findViewById(R.id.searchEdit);

        mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_channel = new Intent(getContext(), ChannelActivity.class);
                startActivityForResult(intent_channel, 20);
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()){
                    Toast.makeText(getContext(), "请输入搜索内容！", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getActivity(), NewsSearchActivity.class);
                    intent.putExtra("searchText", query);
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
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
        mFragments = new NewsClassFragment[cnt];
        for (int i = 0; i < cnt; ++i){
            int idx = userChannelList.get(i).getId();
            mTitles[i] = userChannelList.get(i).getName();
            mFragments[i] = NewsClassFragment.newInstance(idx - 1);
        }
        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mAdapter = new TabPagerAdapter(getChildFragmentManager(), mFragments);
        mAdapter.setTabTitles(mTitles);
        mViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
    }

    private void showDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("是否前去github给个star?");
        builder.setMessage("给作者一个star表示鼓励");
        builder.setPositiveButton("前去", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("https://github.com/HuRuWo/YiLan");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
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

