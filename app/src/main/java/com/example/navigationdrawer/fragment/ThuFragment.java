package com.example.navigationdrawer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.navigationdrawer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ThuFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_thu, container, false);
        //this line hide Actionbar
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bottomNavigationView = view.findViewById(R.id.bottomNav);
        //set fragment default
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerThu, new MoneyFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_money);
        //set oclick tung item ->> fragment
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new KhoanThu_ThuFragment();
                        break;
                    case R.id.nav_work:
                        fragment = new LoaiThu_ThuFragment();
                        break;
                    case R.id.nav_money:
                        fragment = new MoneyFragment();
                        break;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerThu, fragment).commit();
                return true;
            }
        });
        return view;
    }
}
