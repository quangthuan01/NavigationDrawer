package com.example.navigationdrawer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.navigationdrawer.R;

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

        replace(new Fragment_ThongKe_KhoanChi())    ;
        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int i) {
                switch (i) {

                    case 0:
                        replace(new Fragment_ThongKe_KhoanChi());
                        break;
                    case 1:
                        replace(new Fragment_ThongKe_KhoanThu());
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
