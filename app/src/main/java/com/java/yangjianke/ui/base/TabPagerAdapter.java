package com.example.covid_news.ui.base;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[];
    private Fragment[] fragments;

    public TabPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public void setTabTitles(@NonNull String[] tabTitles) {
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position % fragments.length];
    }

    @Override
    public int getCount() {
        if (fragments == null || fragments.length == 0)
            return 0;
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (tabTitles == null)
            return "";
        return tabTitles[position];
    }


}
