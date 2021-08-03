package com.example.navigationdrawer.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.adapter.KhoanThuAdapter;
import com.example.navigationdrawer.adapter.SpinnerKhoanThuAdapter;
import com.example.navigationdrawer.model.KhoanChi;
import com.example.navigationdrawer.model.KhoanThu;
import com.example.navigationdrawer.model.LoaiThu;
import com.example.navigationdrawer.model.User;
import com.example.navigationdrawer.notification.Notification_DiaLog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class KhoanThu_ThuFragment extends Fragment {

    private List<KhoanThu> khoanThuList;
    private List<LoaiThu> loaiThuList;
    private KhoanThu khoanThuModel;
    private KhoanThuAdapter khoanThuAdapter;
    private Notification_DiaLog notificationDiaLog;
    private FloatingActionButton fabKhoanThu;
    private RecyclerView recyclerViewKhoanThu;
    private SpinnerKhoanThuAdapter spinnerKhoanThuAdapter;
    private DatabaseReference DbRef, dataUser, dataMoney;
    private String select;
    private FirebaseUser firebaseUser;
    private String idUser;
    private TextView textMoney, textName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_khoan_thu__thu, container, false);
        //Assign varible
        textName = view.findViewById(R.id.textNameKhoanThu);
        textMoney = view.findViewById(R.id.textMoneyKhoanThu);
        fabKhoanThu = view.findViewById(R.id.fabKhoanThu);
        recyclerViewKhoanThu = view.findViewById(R.id.recyclerViewKhoanThu);
        recyclerViewKhoanThu.setLayoutManager(new LinearLayoutManager(getActivity()));
        //get userID
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        idUser = firebaseUser.getUid();
        //getUserandMoney
        getDataUserandMoney();
        //thong bao
        notificationDiaLog = new Notification_DiaLog(getActivity());
        //khoi tao arraylist
        khoanThuList = new ArrayList<>();
        loaiThuList = new ArrayList<>();

        //khoi tao adapter
        spinnerKhoanThuAdapter = new SpinnerKhoanThuAdapter(loaiThuList, getActivity());
        //create coletion
        DbRef = FirebaseDatabase.getInstance().getReference().child("KhoanThu");
        //get all data
        getDataFireBase();

        //insert
        fabKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertKhoanThu(Gravity.CENTER);
            }
        });

        return view;
    }

    private void getDataUserandMoney() {
        //get userID
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        idUser = firebaseUser.getUid();
        dataUser = FirebaseDatabase.getInstance().getReference("Users");
        dataUser.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    String name = user.name;
                    textName.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dataMoney = FirebaseDatabase.getInstance().getReference("KhoanThu");
        dataMoney.orderByChild("idUserKhoanThu").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int total = 0;
                for (DataSnapshot KhoanThuDatasnaps : snapshot.getChildren()) {
                    KhoanThu khoanThu = KhoanThuDatasnaps.getValue(KhoanThu.class);
                    khoanThuList.add(khoanThu);
                    total += khoanThu.getMoneyKhoanThu();
                    String totalKhoanChi = String.valueOf(total);
                    Double d = Double.valueOf(totalKhoanChi);
                    textMoney.setText(String.format("%,.1f", d) + " $");
                    Log.e("errorKhoanThu", totalKhoanChi);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void insertKhoanThu(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setWindowAnimations(R.style.AnimationDownUpDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_bottom_sheet_khoanthu);
        Window window = dialog.getWindow();
        //check
        if (window == null) {
            return;
        }
        // xu ly vi tri dia log center
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        //check
        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);//co the tat dialog khi click ben ngoai
        } else {
            dialog.setCancelable(false);//ko the tat dialog khi click ben ngoai
        }
        TextInputEditText textTitle = (TextInputEditText) dialog.findViewById(R.id.inputTitleKhoanThu);
        TextInputEditText textMoney = (TextInputEditText) dialog.findViewById(R.id.inputMoneyKhoanThu);
        TextView dateKhoanThu = (TextView) dialog.findViewById(R.id.textDateKhoanThu);
        Spinner spinnerKhoanThu = (Spinner) dialog.findViewById(R.id.spnKhoanThu);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancelKhoanThu);
        Button btn_save = (Button) dialog.findViewById(R.id.btn_saveKhoanThu);

        //getdata firebase ->> spinner
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("LoaiThu");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loaiThuList.clear();
                for (DataSnapshot LoaiThuDatasnaps : snapshot.getChildren()) {
                    LoaiThu loaiThu = LoaiThuDatasnaps.getValue(LoaiThu.class);
                    loaiThuList.add(loaiThu);
                }
                spinnerKhoanThuAdapter = new SpinnerKhoanThuAdapter(loaiThuList, getActivity());
                spinnerKhoanThu.setAdapter(spinnerKhoanThuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinnerKhoanThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select = ((LoaiThu) spinnerKhoanThuAdapter.getItem(position)).getTitleLoaiThu();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DAY_OF_MONTH);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dateKhoanThu.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            }
                        }, nam, thang, ngay);
                datePickerDialog.show();

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _idKhoanThu = UUID.randomUUID().toString();
                String _spn = select;
                String _title = textTitle.getText().toString();
                int _money = Integer.parseInt(textMoney.getText().toString());
                String _date = dateKhoanThu.getText().toString();


                if (_title.isEmpty()) {
                    notificationDiaLog.showWarning(Gravity.CENTER);
                    textTitle.requestFocus();
                    textTitle.setError("Please enter Type!");
                    return;
                } else if (_money < 5) {
                    notificationDiaLog.showError(Gravity.CENTER);
                    textMoney.requestFocus();
                    textMoney.setError("Minimum is 5.0$!");
                    return;
                } else if (_money > 10000000) {
                    notificationDiaLog.showError(Gravity.CENTER);
                    textMoney.requestFocus();
                    textMoney.setError("Maximum is 10,000,000.0$");
                    return;
                } else if (_date.isEmpty()) {
                    notificationDiaLog.showWarning(Gravity.CENTER);
                    dateKhoanThu.requestFocus();
                    dateKhoanThu.setError("Please chose date!");
                    return;
                } else {
                    khoanThuModel = new KhoanThu(_idKhoanThu, _spn, _title, _money, _date, idUser);
                    DbRef.child(khoanThuModel.getIdKhoanThu()).setValue(khoanThuModel);
                    notificationDiaLog.showSuccessful(Gravity.CENTER);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }

    private void getDataFireBase() {
        // get data to fire ->> app

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("KhoanThu");
        databaseReference.orderByChild("idUserKhoanThu").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                khoanThuList.clear();
                for (DataSnapshot KhoanChiDatasnaps : snapshot.getChildren()) {
                    KhoanThu khoanThu = KhoanChiDatasnaps.getValue(KhoanThu.class);
                    khoanThuList.add(khoanThu);
                }
                khoanThuAdapter = new KhoanThuAdapter(khoanThuList, getActivity());
                recyclerViewKhoanThu.setAdapter(khoanThuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}