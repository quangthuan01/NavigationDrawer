package com.example.navigationdrawer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.model.LoaiChi;
import com.example.navigationdrawer.model.LoaiThu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SpinnerKhoanThuAdapter extends BaseAdapter {
    private List<LoaiThu> data;
    private Context context;
    public SpinnerKhoanThuAdapter(List<LoaiThu> data, Context context) {
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String idUser = user.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LoaiThu");
        databaseReference.orderByChild("idUserLoaiThu").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot LoaiThuSp : snapshot.getChildren()){
                    LoaiThu loaiThu =  LoaiThuSp.getValue(LoaiThu.class);
                    data.add(loaiThu);
                }
                LoaiThu loaiThu = (LoaiThu)getItem(position);
                spn_loaichi.setText(loaiThu.getTitleLoaiThu());
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        return view;
    }
}
