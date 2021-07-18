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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;


public class ThongKeFragment extends Fragment {
    private BarChart barChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_thongke, container, false);
        barChart = view.findViewById(R.id.barChart);


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

        BarDataSet barDataSet = new BarDataSet(visitors, "Visitors");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar Chart Example");
        barChart.animateY(2000);
        return view;
    }
}
