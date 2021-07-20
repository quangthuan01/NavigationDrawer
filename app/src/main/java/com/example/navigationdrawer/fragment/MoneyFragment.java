package com.example.navigationdrawer.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.model.KhoanChi;
import com.example.navigationdrawer.model.KhoanThu;
import com.example.navigationdrawer.model.LoaiChi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MoneyFragment extends Fragment {

    private TextView textCash, textUsed, textReceived;
    private FirebaseUser user;
    private String idUser;
    private DatabaseReference databaseReference;
    private KhoanChi khoanChiModel;
    private KhoanThu khoanThuModel;
    private List<KhoanThu> khoanThuList;
    private List<KhoanChi> khoanChiList;
    private String[] totlatvalue = {"1"};
    private String[] totlatvalue1 = {"1"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_money, container, false);

        textCash = view.findViewById(R.id.textCashMoney);
        textUsed = view.findViewById(R.id.textUsedMoney);
        textReceived = view.findViewById(R.id.textReceivedMoney);
        khoanChiModel = new KhoanChi();
        khoanThuModel = new KhoanThu();
        khoanThuList = new ArrayList<>();
        khoanChiList = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        idUser = user.getUid();
        //get all money khoan chi
        databaseReference = FirebaseDatabase.getInstance().getReference("KhoanChi");
        databaseReference.orderByChild("idUserKhoanChi").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int title = 0;

                for (DataSnapshot KhoanChiDatasnaps : snapshot.getChildren()) {
                    KhoanChi khoanChi = KhoanChiDatasnaps.getValue(KhoanChi.class);
                    khoanChiList.add(khoanChi);
                    title += khoanChi.getMoneyKhoanChi();
                     totlatvalue1[0] = String.valueOf(title);
                    textUsed.setText(totlatvalue1[0] + " VND");
                    Log.e("error1",totlatvalue1[0]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //get all money khoan thu
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("KhoanThu");
        dbRef.orderByChild("idUserKhoanThu").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int title = 0;
                for (DataSnapshot KhoanThuDatasnaps : snapshot.getChildren()) {
                    KhoanThu khoanThu = KhoanThuDatasnaps.getValue(KhoanThu.class);
                    khoanThuList.add(khoanThu);
                    title += khoanThu.getMoneyKhoanThu();
                     totlatvalue[0] = String.valueOf(title);
                    textReceived.setText(totlatvalue[0] + " VND");
                    Log.e("error",totlatvalue[0]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Log.e("errorlist", String.valueOf(khoanChiList.size()));
        Log.e("errorlist1", String.valueOf(khoanThuList.size()));
        int cash = Integer.parseInt(totlatvalue1[0])  - Integer.parseInt(totlatvalue[0]);
        String cashs = String.valueOf(cash);
        textCash.setText(cashs);
        return view;
    }

}