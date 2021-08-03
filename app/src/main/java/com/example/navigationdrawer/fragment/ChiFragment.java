package com.example.navigationdrawer.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.adapter.TabLayoutViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ChiFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayoutViewPagerAdapter tabLayoutViewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_chi, container, false);

        tabLayout = view.findViewById(R.id.tablayout_Chi);
        viewPager = view.findViewById(R.id.view_pagerChi);

        tabLayoutViewPagerAdapter = new TabLayoutViewPagerAdapter(getParentFragmentManager());
        //add fragment
        tabLayoutViewPagerAdapter.AddFragment(new KhoanChi_ChiFragment(), "Expenditures");
        tabLayoutViewPagerAdapter.AddFragment(new LoaiChi_ChiFragment(), "Spending money");
        //setAdapter
        viewPager.setAdapter(tabLayoutViewPagerAdapter);
        //connect tablayout with viewPager
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}