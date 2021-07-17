package com.example.navigationdrawer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.model.KhoanThu;

import java.util.List;

public class KhoanThuAdapter extends RecyclerView.Adapter<KhoanThuAdapter.MyViewHolder> {
    private List<KhoanThu> data;
    private Context context;

    public KhoanThuAdapter(List<KhoanThu> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
         return new KhoanThuAdapter.MyViewHolder(

                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_khoanthu, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull  KhoanThuAdapter.MyViewHolder holder, int position) {
        //set du lieu len recyclerview
        KhoanThu khoanThu =data.get(position);
        holder.inputTitle.setText(khoanThu.getTitleKhoanThu());
        holder.textDetail.setText(khoanThu.getOldTitle());
        holder.inputMoney.setText(khoanThu.getMoneyKhoanThu());
        holder.textDate.setText(khoanThu.getDateKhoanThu());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView textDate,inputTitle,textDetail,inputMoney;


        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);

            inputTitle = itemView.findViewById(R.id.item_TheLoaiKhoanThu);
            textDetail = itemView.findViewById(R.id.item_DetailTheLoaiKhoanThu);
            inputMoney = itemView.findViewById(R.id.item_textSoTienThu);
            textDate = itemView.findViewById(R.id.item_textDateKhoanThu);

        }
    }
}
