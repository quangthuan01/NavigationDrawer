package com.example.navigationdrawer.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.adapter.TabLayoutViewPager2Adapter;
import com.example.navigationdrawer.adapter.TabLayoutViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ChiFragment extends Fragment {

    private TabLayout tabLayout;
//    private ViewPager viewPager;
    private ViewPager2 viewPager2;

//    private TabLayoutViewPagerAdapter tabLayoutViewPagerAdapter;
    private TabLayoutViewPager2Adapter viewPager2Adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_chi, container, false);

        tabLayout = view.findViewById(R.id.tablayout_Chi);
        viewPager2 = view.findViewById(R.id.view_pager2Chi);

//        tabLayoutViewPagerAdapter = new TabLayoutViewPagerAdapter(getParentFragmentManager());
        viewPager2Adapter = new TabLayoutViewPager2Adapter(getActivity());
        viewPager2.setAdapter(viewPager2Adapter);
        //add fragment
        tabLayout.addTab(tabLayout.newTab().setText("Expenditures"));
        tabLayout.addTab(tabLayout.newTab().setText("Spending money"));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

//        tabLayoutViewPagerAdapter.AddFragment(new KhoanChi_ChiFragment(), "Expenditures");
//        tabLayoutViewPagerAdapter.AddFragment(new LoaiChi_ChiFragment(), "Spending money");
        //setAdapter
//        viewPager.setAdapter(tabLayoutViewPagerAdapter);
        //connect tablayout with viewPager
//        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}