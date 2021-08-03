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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ThongKeFragment extends Fragment {
    private LineChart lineChart;
    private BarChart barChart;
    private List<KhoanChi> khoanChiList;
    private List<KhoanThu> khoanThuList;
    private DatabaseReference dbReferenceKhoanThu,dbReferenceKhoanChi;
    private FirebaseUser user;
    private String idUser;
    private LineDataSet lineDataSet;
    private ArrayList<ILineDataSet> iLineDataSets;
    private LineData lineData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_thongke, container, false);
        barChart = view.findViewById(R.id.barChart);
//        lineChart = view.findViewById(R.id.barChart);

        //get userID
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbReferenceKhoanThu = FirebaseDatabase.getInstance().getReference("KhoanThu");
        dbReferenceKhoanChi = FirebaseDatabase.getInstance().getReference("KhoanChi");
        idUser = user.getUid();
        lineDataSet = new LineDataSet(null,null);
        iLineDataSets = new ArrayList<>();
        khoanChiList = new ArrayList<>();
        khoanThuList = new ArrayList<>();

//        Date date = new Date();
//        date.setMonth()
//        date.setYear()
//        date.setDay()
//        date.setlong currentTime = date.getTime();
//

//        //get data khoan chi
//        dbReferenceKhoanChi.orderByChild("idUserKhoanChi").equalTo(idUser).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull  DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull  DatabaseError error) {
//
//            }
//        });


//        //        get data khoan thu
//        dbReferenceKhoanThu.orderByChild("idUserKhoanThu").equalTo(idUser).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull  DataSnapshot snapshot) {
//                ArrayList<Entry> dataViewLineChart = new ArrayList<Entry>();
//                if (snapshot.hasChildren()){
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                        KhoanThu khoanThu = dataSnapshot.getValue(KhoanThu.class);
//                        khoanThuList.add(khoanThu);
//                        long date = (new Date(khoanThu.getDateKhoanThu())).getTime();
////                        int date = khoanThu.getDateKhoanThu().getTime();
//                        dataViewLineChart.add(new Entry(date, khoanThu.getMoneyKhoanThu()));
//                    }
//                    showChart(dataViewLineChart);
//                }else{
//                    barChart.clear();
//                    barChart.invalidate();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull  DatabaseError error) {
//
//            }
//        });
        //demo thong ke
        ArrayList<BarEntry> dataViewBarChart = new ArrayList<>();
        dataViewBarChart.add(new BarEntry(1, 1000000));
        dataViewBarChart.add(new BarEntry(2, 1500000));
        dataViewBarChart.add(new BarEntry(3, 2000000));
        dataViewBarChart.add(new BarEntry(4, 2500000));
        dataViewBarChart.add(new BarEntry(5, 4000000));
        dataViewBarChart.add(new BarEntry(6, 6000000));
        dataViewBarChart.add(new BarEntry(7, 5000000));
        dataViewBarChart.add(new BarEntry(8, 4500000));
        dataViewBarChart.add(new BarEntry(9, 8000000));
        dataViewBarChart.add(new BarEntry(10, 10000000));
        dataViewBarChart.add(new BarEntry(11, 2800000));
        dataViewBarChart.add(new BarEntry(12, 900000));

//        set mau  and name
        BarDataSet barDataSet = new BarDataSet(dataViewBarChart, "MoneyMobie");
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

    private void showChart(ArrayList<Entry> dataViewLineChart) {

        lineDataSet.setValues(dataViewLineChart);
        lineDataSet.setLabel("Revenue and Expenditure Chart");
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setNoDataTextColor(Color.RED);
        lineChart.setData(lineData);
        lineChart.invalidate();

    }
}
