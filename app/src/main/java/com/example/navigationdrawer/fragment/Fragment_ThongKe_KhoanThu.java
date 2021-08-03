package com.example.navigationdrawer.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_ThongKe_KhoanThu extends Fragment {

    private BarChart barChart;
    private List<KhoanThu> khoanThuList;
    private DatabaseReference dbReferenceKhoanThu;
    private FirebaseUser user;
    private String idUser;
    private List<String> labelsName;
    private List<BarEntry> barEntryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_thongke_khoanthu, container, false);
        barChart = view.findViewById(R.id.barChart);

        //get userID
        user = FirebaseAuth.getInstance().getCurrentUser();

        dbReferenceKhoanThu = FirebaseDatabase.getInstance().getReference("KhoanThu");
        idUser = user.getUid();

        barEntryList = new ArrayList<>();
        khoanThuList = new ArrayList<>();
        labelsName = new ArrayList<>();


        //        get data khoan thu
        dbReferenceKhoanThu.orderByChild("idUserKhoanThu").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    KhoanThu khoanThu = dataSnapshot.getValue(KhoanThu.class);
                    khoanThuList.add(khoanThu);
                    Log.i("chuoi", String.valueOf(khoanThuList.size()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < khoanThuList.size(); i++) {
                    String month = khoanThuList.get(i).getDateKhoanThu();
                    int  money = khoanThuList.get(i).getMoneyKhoanThu();
                    barEntryList.add(new BarEntry(i,money));
                    labelsName.add(month);
                }


                BarDataSet barDataSet = new BarDataSet(barEntryList,"Money");
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(16f);
                Description description = new Description();
                description.setText("Months");
                barChart.setDescription(description);
                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);
                //formater value
                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsName));

                xAxis.setPosition(XAxis.XAxisPosition.TOP);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
                xAxis.setGranularity(1f);
                xAxis.setLabelCount(labelsName.size());
                xAxis.setLabelRotationAngle(270);
                barChart.animateY(3000);
                barChart.invalidate();


            }
        },2000);



//        //demo thong ke
//        ArrayList<BarEntry> dataViewBarChart = new ArrayList<>();
//        dataViewBarChart.add(new BarEntry(1, 1000000));
//        dataViewBarChart.add(new BarEntry(2, 1500000));
//        dataViewBarChart.add(new BarEntry(3, 2000000));
//        dataViewBarChart.add(new BarEntry(4, 2500000));
//        dataViewBarChart.add(new BarEntry(5, 4000000));
//        dataViewBarChart.add(new BarEntry(6, 6000000));
//        dataViewBarChart.add(new BarEntry(7, 5000000));
//        dataViewBarChart.add(new BarEntry(8, 4500000));
//        dataViewBarChart.add(new BarEntry(9, 8000000));
//        dataViewBarChart.add(new BarEntry(10, 10000000));
//        dataViewBarChart.add(new BarEntry(11, 2800000));
//        dataViewBarChart.add(new BarEntry(12, 900000));
//
////        set mau  and name
//        BarDataSet barDataSet = new BarDataSet(dataViewBarChart, "MoneyMobie");
//        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//        barDataSet.setValueTextColor(Color.BLACK);
//        barDataSet.setValueTextSize(16f);
//        //set hieu ung
//        BarData barData = new BarData(barDataSet);
//        barChart.setFitBars(true);
//        barChart.setData(barData);
//        barChart.getDescription().setText("Bar Chart Example");
//        barChart.animateY(2000);
        return  view;
    }
}
