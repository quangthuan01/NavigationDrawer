package com.example.navigationdrawer.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.model.LoaiChi;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;

import java.util.List;

public class LoaiChiAdapter  extends BaseAdapter {
    private List<LoaiChi> data;
    private Context context;

    public LoaiChiAdapter(List<LoaiChi> data, Context context) {
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
        if (view == null) {
            view = View.inflate(parent.getContext(), R.layout.item_list_loaichi, null);

        }
        LoaiChi loaiChi = (LoaiChi) getItem(position);

        TextView textTheLoai = view.findViewById(R.id.item_textLoaiChi);
        TextView textDate = view.findViewById(R.id.item_textDateLoaiChi);

        textTheLoai.setText(loaiChi.getTitleLoaiChi());
        textDate.setText(loaiChi.getDateLoaiChi());

        return view;
    }
}
