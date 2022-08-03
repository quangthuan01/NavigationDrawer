package com.example.navigationdrawer.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.navigationdrawer.R;

import me.ibrahimsn.lib.OnItemReselectedListener;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class ThongKeFragment extends Fragment {

    private SmoothBottomBar smoothBottomBar;
//    private Fragment fragment = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_thongke, container, false);

        smoothBottomBar = view.findViewById(R.id.bottomBarThongke);

        replace(new Fragment_ThongKe_KhoanChi_LineChart())    ;
        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i){
                    case 0:
                        replace(new Fragment_ThongKe_KhoanChi_LineChart());
                        break;
                    case 1:
                        replace(new Fragment_ThongKe_KhoanThu_BarChart());
                        break;
                }
                return false;
            }
        });

        smoothBottomBar.setOnItemReselectedListener(new OnItemReselectedListener() {
            @Override
            public void onItemReselect(int i) {
                switch (i){
                    case 0:
                        Log.d(TAG, "onItemReselect: 0");
                        break;
                    case 1:
                        Log.d(TAG, "onItemReselect: 1");
                        break;
                }
            }
        });

        return view;
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()   ;
        transaction.replace(R.id.frame_container_thongke,fragment);
        transaction.commit();
    }

}
