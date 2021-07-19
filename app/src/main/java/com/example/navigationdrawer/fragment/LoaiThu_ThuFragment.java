package com.example.navigationdrawer.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.adapter.LoaiChiAdapter;
import com.example.navigationdrawer.adapter.LoaiThuAdapter;
import com.example.navigationdrawer.model.LoaiChi;
import com.example.navigationdrawer.model.LoaiThu;
import com.example.navigationdrawer.notification.Notification_DiaLog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoaiThu_ThuFragment extends Fragment {
    private FloatingActionButton fabLoaiThu;
    private List<LoaiThu> loaiThuList;
    private LoaiThu loaiThuModel;
    private LoaiThuAdapter loaiThuAdapter;
    private RecyclerView  recyclerView;
    private DatabaseReference DbRef;
    private Notification_DiaLog notificationDiaLog;
    private FirebaseUser firebaseUser;
    private String idUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_loai_thu__thu,container,false);

        fabLoaiThu = view.findViewById(R.id.floatingActionButton);
        recyclerView = view.findViewById(R.id.recyclerViewLoaiThu);
        notificationDiaLog = new Notification_DiaLog(getActivity());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //get userID
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        idUser = firebaseUser.getUid();
        loaiThuList = new ArrayList<>();
        loaiThuAdapter = new LoaiThuAdapter(loaiThuList,getActivity());
        DbRef = FirebaseDatabase.getInstance().getReference().child("LoaiThu");

        //getdataFireBase
        getDataFireBase();
        //insert data
        fabLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLoaiThu(Gravity.CENTER);
            }
        });

        return view;
    }

    private void addLoaiThu(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thu_loaithu);
        Window window = dialog.getWindow();
        //check
        if (window == null){
            return;
        }
        // xu ly vi tri dia log center
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        //check
        if (Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);//co the tat dialog khi click ben ngoai
        }else {
            dialog.setCancelable(false);//ko the tat dialog khi click ben ngoai
        }
        EditText textLoaiThu = (EditText) dialog.findViewById(R.id.inputTextLoaiThu_DiaLog);
        TextView dateLoaiThu = (TextView) dialog.findViewById(R.id.textDateLoaiThu_DiaLog);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_Cancel_Dialog_LoaiThu);
        Button btn_save = (Button) dialog.findViewById(R.id.btn_Save_Dialog_LoaiThu);

        dateLoaiThu.setOnClickListener(new View.OnClickListener() {
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
                                dateLoaiThu.setText(dayOfMonth + "-" + month + "-" + year);
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
                String _idloaithu = UUID.randomUUID().toString();
                String  _title  = textLoaiThu.getText().toString();
                String  _date = dateLoaiThu.getText().toString();
                if (_title.isEmpty()){
                    notificationDiaLog.showWarning(Gravity.CENTER);
                    textLoaiThu.requestFocus();
                    return;
                }else if (_date.isEmpty()){
                    notificationDiaLog.showWarning(Gravity.CENTER);
                    dateLoaiThu.requestFocus();
                    return;
                }else {
                     loaiThuModel = new LoaiThu(_idloaithu, _title, _date,idUser);
                    DbRef.child(loaiThuModel.getIdLoaiThu()).setValue(loaiThuModel);
                    notificationDiaLog.showSuccessful(Gravity.CENTER);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();


    }
    private void getDataFireBase(){
        // get data to fire ->> app
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LoaiThu");
        databaseReference.orderByChild("idUserLoaiThu").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loaiThuList.clear();
                for (DataSnapshot LoaiThuDatasnaps : snapshot.getChildren()) {
                    LoaiThu loaiThu = LoaiThuDatasnaps.getValue(LoaiThu.class);
                    loaiThuList.add(loaiThu);
                }
                loaiThuAdapter = new LoaiThuAdapter(loaiThuList,getActivity());
                recyclerView.setAdapter(loaiThuAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}