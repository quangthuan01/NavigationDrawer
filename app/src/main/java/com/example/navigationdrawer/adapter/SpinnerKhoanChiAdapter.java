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

public class SpinnerKhoanChiAdapter extends BaseAdapter {
    List<LoaiChi> data;
    Context context;

    public SpinnerKhoanChiAdapter(List<LoaiChi> data, Context context) {
        this.data = data;
        this.context = context;
    }
//    public  int getPosition(String IdKhoanChi) {
//        int index  = -1;
//        for (int i  = 0; i<data.size(); i++){
//            LoaiChi loaiChi = data.get(i);
//            if (loaiChi.getIdLoaiChi().equals(IdKhoanChi)){
//                index = i;
//                break;
//            }
//        }
//        return index;
//    }

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
        view = View.inflate(parent.getContext(), R.layout.spinner_item_khoanchi, null);
        TextView textViewSpn = view.findViewById(R.id.spinner_adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String idUser = user.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LoaiChi");
        databaseReference.orderByChild("idUserLoaiChi").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot LoaiChiSp : snapshot.getChildren()) {
                    LoaiChi loaiChi = LoaiChiSp.getValue(LoaiChi.class);
                    data.add(loaiChi);
                }
                LoaiChi loaiChi = (LoaiChi) getItem(position);
                textViewSpn.setText(loaiChi.getTitleLoaiChi());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
//    public static class ViewHolder{
//        final TextView textSpinner;
//        ViewHolder (TextView _textSpinner){
//            textSpinner = _textSpinner;
//        }
//    }
}
