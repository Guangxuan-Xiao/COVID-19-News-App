package com.java.yangjianke.ui.news;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.*;
import com.java.yangjianke.MyApplication;
import com.java.yangjianke.ui.channel.ChannelActivity;
import com.java.yangjianke.ui.channel.ChannelItem;
import com.java.yangjianke.ui.channel.ChannelManage;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.java.yangjianke.R;
import com.java.yangjianke.ui.base.TabPagerAdapter;
import com.java.yangjianke.ui.news.NewsClassFragment;
import butterknife.ButterKnife;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NewsFragment extends Fragment implements ViewPager.OnPageChangeListener{

    private TabLayout mTabs;
    private ViewPager mViewPager;
    private ImageView mImgView;
    //private SearchView mSearchView;
    private Button mButton;
    private AutoCompleteTextView mSearchView;

    private List<String> searchHistory;
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
        mSearchView = (AutoCompleteTextView) view.findViewById(R.id.searchEdit);
        mButton = (Button) view.findViewById(R.id.buttonSearch);

        mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_channel = new Intent(getContext(), ChannelActivity.class);
                startActivityForResult(intent_channel, 20);
            }
        });

        searchHistory = new ArrayList<String>();

        File f = new File(getContext().getFilesDir(), "search_history.txt");
        try {
            BufferedReader in = new BufferedReader(new FileReader(f));
            String line = null;
            while ((line = in.readLine()) != null){
                searchHistory.add(line);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(String.valueOf(searchHistory.size()) + " records of search history");

//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), NewsHistoryActivity.class);
//                startActivity(intent);
//            }
//        });

//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if (query.isEmpty()){
//                    Toast.makeText(getContext(), "请输入搜索内容！", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Intent intent = new Intent(getActivity(), NewsSearchActivity.class);
//                    intent.putExtra("searchText", query);
//                    startActivity(intent);
//                }
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        ArrayAdapter<String> searchAdapter =
                new ArrayAdapter<String>(getContext(), R.layout.list_item, searchHistory);
        mSearchView.setAdapter(searchAdapter);
        mSearchView.setThreshold(0);
        mSearchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    mSearchView.showDropDown();
                }
            }
        });

        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND || i == EditorInfo.IME_ACTION_DONE
                    || (keyEvent != null && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode()
                        && KeyEvent.ACTION_DOWN == keyEvent.getAction())){
                    String searchText = mSearchView.getText().toString();
                    System.out.println("Search: " + searchText);
                    if (searchText.isEmpty()){
                        Toast.makeText(getContext(), "请输入搜索内容！", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(getActivity(), NewsSearchActivity.class);
                        intent.putExtra("searchText", searchText);
                        searchHistory.add(searchText);
                        ArrayAdapter<String> searchAdapter = new ArrayAdapter<String>
                                (getContext(), R.layout.list_item, searchHistory);
                        mSearchView.setAdapter(searchAdapter);
                        try {
                            FileWriter fw = new FileWriter(new File(getContext().getFilesDir(), "search_history.txt"));
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(searchText);
                            bw.newLine();
                            bw.close();
                            System.out.println(String.valueOf(searchHistory.size()) + " records of search history");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mSearchView.getText().toString();
                System.out.println("Search: " + searchText);
                if (searchText.isEmpty()){
                    Toast.makeText(getContext(), "请输入搜索内容！", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getActivity(), NewsSearchActivity.class);
                    intent.putExtra("searchText", searchText);
                    searchHistory.add(searchText);
                    ArrayAdapter<String> searchAdapter = new ArrayAdapter<String>
                                    (getContext(), R.layout.list_item, searchHistory);
                    mSearchView.setAdapter(searchAdapter);
                    try {
                        FileWriter fw = new FileWriter(new File(getContext().getFilesDir(), "search_history.txt"), true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(searchText);
                        bw.newLine();
                        bw.close();
                        System.out.println(String.valueOf(searchHistory.size()) + " records of search history");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
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

//    @Override
//    public void onDestroy(){
//        System.out.println(String.valueOf(searchHistory.size()) + " records of search history when destroyed");
//        try {
//            FileOutputStream fos = new FileOutputStream(new File(getContext().getFilesDir(), "search_history.txt"));
//            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
//            BufferedWriter b = new BufferedWriter(osw);
//            for (String rec: searchHistory){
//                b.write(rec + "\n");
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        super.onDestroy();
//    }

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

