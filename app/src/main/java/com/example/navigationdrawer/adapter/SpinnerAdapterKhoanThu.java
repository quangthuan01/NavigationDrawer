package com.example.navigationdrawer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.model.LoaiThu;

import java.util.List;

public class SpinnerAdapterKhoanThu extends BaseAdapter {
    private List<LoaiThu> data;
    private Context context;
    public SpinnerAdapterKhoanThu(List<LoaiThu> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = View.inflate(parent.getContext(), R.layout.spinner_item_khoanthu, null);
        }
        TextView spn_loaichi = ((TextView) view.findViewById(R.id.spinner_adapterKhoanThu));
        LoaiThu loaiThu = (LoaiThu) getItem(position);
        spn_loaichi.setText(loaiThu.getTitleLoaiThu());
        return view;
    }
}
