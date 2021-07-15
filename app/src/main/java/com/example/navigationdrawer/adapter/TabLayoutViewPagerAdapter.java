package com.example.navigationdrawer.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TabLayoutViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ArrayList<String> stringArrayList = new ArrayList<>();

    public void AddFragment(Fragment fragment, String s){
        //addFragment
        fragmentArrayList.add(fragment);
        //addString
        stringArrayList.add(s);

    }

    public TabLayoutViewPagerAdapter(@NonNull  FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //return fragment position

        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        //return fragment list size
        return fragmentArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //return tab title
      return stringArrayList.get(position);
    }
}
