package com.example.navigationdrawer.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.navigationdrawer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import me.ibrahimsn.lib.OnItemReselectedListener;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;


public class ThuFragment extends Fragment {

    private SmoothBottomBar smoothBottomBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_thu, container, false);
        //this line hide Actionbar
//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        smoothBottomBar = view.findViewById(R.id.bottomNav);
        //set fragment default
        replace(new KhoanThu_ThuFragment());
        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        replace(new KhoanThu_ThuFragment());
                        break;
                    case 1:
                        replace(new LoaiThu_ThuFragment());
                        break;
                    case 2:
                        replace(new MoneyFragment());
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
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerThu, fragment);
        transaction.commit();
    }

}
