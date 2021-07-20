package com.example.navigationdrawer.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.model.KhoanChi;

import java.util.List;

public class KhoanChiAdapter extends BaseAdapter {
    private List<KhoanChi> data;
    private Context context;

    public KhoanChiAdapter(List<KhoanChi> data, Context context) {
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
            view = View.inflate(parent.getContext(), R.layout.item_list_khoanchi, null);
        }
        KhoanChi khoanChi = (KhoanChi) getItem(position);
        TextView title = (TextView) view.findViewById(R.id.item_TheLoaiKhoanChi);
        TextView moNey = (TextView) view.findViewById(R.id.item_textSoTienChi);
        TextView datetime = (TextView) view.findViewById(R.id.item_textDateKhoanChi);
        TextView oldtitle = (TextView) view.findViewById(R.id.item_OldTheLoaiKhoanChi);

        title.setText(khoanChi.getTitleKhoanChi());
        oldtitle.setText(khoanChi.getOldKhoanChi());
        moNey.setText(khoanChi.getMoneyKhoanChi() + " VND");
        datetime.setText(khoanChi.getDateKhoanChi());
        return view;
    }
}

