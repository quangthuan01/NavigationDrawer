package com.example.navigationdrawer.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.model.KhoanChi;
import com.example.navigationdrawer.model.KhoanThu;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


public class ThongKeFragment extends Fragment {
    private BarChart barChart;
    private List<KhoanChi> khoanChiList;
    private List<KhoanThu> khoanThuList;
    private DatabaseReference dbReferenceKhoanThu,dbReferenceKhoanChi;
    private FirebaseUser user;
    private String idUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_thongke, container, false);
        barChart = view.findViewById(R.id.barChart);

        //get userID
        user = FirebaseAuth.getInstance().getCurrentUser();
        idUser = user.getUid();

        khoanChiList = new ArrayList<>();
        khoanThuList = new ArrayList<>();

        //get data khoan thu
        dbReferenceKhoanThu.orderByChild("idUserKhoanThu").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        //get data khoan chi
        dbReferenceKhoanChi.orderByChild("idUserKhoanChi").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        //demo thong ke
        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(1, 1000000));
        visitors.add(new BarEntry(2, 1500000));
        visitors.add(new BarEntry(3, 2000000));
        visitors.add(new BarEntry(4, 2500000));
        visitors.add(new BarEntry(5, 4000000));
        visitors.add(new BarEntry(6, 6000000));
        visitors.add(new BarEntry(7, 5000000));
        visitors.add(new BarEntry(8, 4500000));
        visitors.add(new BarEntry(9, 8000000));
        visitors.add(new BarEntry(10, 10000000));
        visitors.add(new BarEntry(11, 2800000));
        visitors.add(new BarEntry(12, 900000));
        //set mau  and name
        BarDataSet barDataSet = new BarDataSet(visitors, "MoneyMobie");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        //set hieu ung
        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar Chart Example");
        barChart.animateY(2000);
        return view;
    }
}
