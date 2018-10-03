package com.projects.darknight.linkapp;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.projects.darknight.linkapp.fragment.HistoryTab;
import com.projects.darknight.linkapp.fragment.TestTab;

public class MyPageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public MyPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TestTab();
            case 1:
                return new HistoryTab();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "TEST";
            case 1:
                return "HISTORY";
            default:
                return null;
        }
    }
}
