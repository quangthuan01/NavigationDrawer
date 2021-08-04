package com.example.navigationdrawer.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.navigationdrawer.fragment.KhoanChi_ChiFragment;
import com.example.navigationdrawer.fragment.LoaiChi_ChiFragment;

public class TabLayoutViewPager2Adapter extends FragmentStateAdapter {


    public TabLayoutViewPager2Adapter(@NonNull  FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new KhoanChi_ChiFragment();
            case 1:
                return new LoaiChi_ChiFragment();

        }
        return new LoaiChi_ChiFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
