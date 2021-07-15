package com.example.navigationdrawer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.model.LoaiChi;

import java.util.List;

public class SpinnerKhoanChiAdapter extends BaseAdapter {
    List<LoaiChi> data;
    Context context;

    public SpinnerKhoanChiAdapter(List<LoaiChi> data, Context context) {
        this.data = data;
        this.context = context;
    }
    public  int getPosition(String IdKhoanChi) {
        int index  = -1;
        for (int i  = 0; i<data.size(); i++){
            LoaiChi loaiChi = data.get(i);
            if (loaiChi.getIdLoaiChi().equals(IdKhoanChi)){
                index = i;
                break;
            }
        }
        return index;
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
        view = View.inflate(parent.getContext(),R.layout.spinner_item_khoanchi,null);
        TextView textViewSpn = view.findViewById(R.id.spinner_adapter);
        ViewHolder viewHolder = new ViewHolder(textViewSpn);
        view.setTag(viewHolder);
        LoaiChi loaiChi = new LoaiChi();
        textViewSpn.setText(loaiChi.getTitleLoaiChi());
        return view;
    }
    public static class ViewHolder{
        final TextView textSpinner;
        ViewHolder (TextView _textSpinner){
            textSpinner = _textSpinner;
        }
    }
}
