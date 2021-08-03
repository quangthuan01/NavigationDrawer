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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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

public class Fragment_ThongKe_KhoanChi extends Fragment {
    private LineChart lineChart;
    private List<KhoanChi> khoanChiList;
    private DatabaseReference dbReferenceKhoanChi;
    private FirebaseUser user;
    private String idUser;
    private List<String> labelsName;
    private List<Entry> barEntryList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_thongke_khoanchi, container, false);

        lineChart = view.findViewById(R.id.lineChart);

        khoanChiList = new ArrayList<>();
        dbReferenceKhoanChi = FirebaseDatabase.getInstance().getReference("KhoanChi");
        user= FirebaseAuth.getInstance().getCurrentUser();
        idUser = user.getUid();
        barEntryList = new ArrayList<>();
        labelsName = new ArrayList<>();

        dbReferenceKhoanChi.orderByChild("idUserKhoanChi").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    KhoanChi khoanChi = dataSnapshot.getValue(KhoanChi.class);
                    khoanChiList.add(khoanChi);
                    Log.i("chuoi", String.valueOf(khoanChiList.size()));
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
                for (int i = 0; i < khoanChiList.size(); i++) {
                    String month = khoanChiList.get(i).getDateKhoanChi();
                    int  money = khoanChiList.get(i).getMoneyKhoanChi();
                    barEntryList.add(new BarEntry(i,money));
                    labelsName.add(month);
                }


                LineDataSet lineDataSet = new LineDataSet(barEntryList,"Money");
                lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                lineDataSet.setValueTextColor(Color.BLACK);
                lineDataSet.setValueTextSize(16f);
                lineDataSet.setLineWidth(2f);
                lineDataSet.setHighLightColor(ColorTemplate.getHoloBlue());
                Description description = new Description();
                description.setText("Months");
                lineChart.setDescription(description);
                LineData lineData = new LineData(lineDataSet);
                lineChart.setData(lineData);
                //formater value
                XAxis xAxis = lineChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsName));

                xAxis.setPosition(XAxis.XAxisPosition.TOP);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
                xAxis.setGranularity(1f);
                xAxis.setLabelCount(labelsName.size());
                xAxis.setLabelRotationAngle(270);
                lineChart.animateY(3000);
                lineChart.invalidate();


            }
        },2000);


        return view;
    }
}
