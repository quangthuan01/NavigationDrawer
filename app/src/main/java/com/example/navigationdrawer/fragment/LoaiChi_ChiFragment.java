package com.example.navigationdrawer.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.adapter.LoaiChiAdapter;
import com.example.navigationdrawer.model.LoaiChi;
import com.example.navigationdrawer.model.User;
import com.example.navigationdrawer.notification.Notification_DiaLog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
public class LoaiChi_ChiFragment extends Fragment {
    //Initialize varible
    private BottomNavigationView navigationView;
    private FloatingActionButton floatingActionButton;
    private Notification_DiaLog notificationDiaLog;
    private ListView listView;
    private LoaiChi loaiChiModel;
    private DatabaseReference DbRef;
    private List<LoaiChi> loaiChiList;
    private LoaiChiAdapter loaiChiAdapter;
    private FirebaseUser firebaseUser;
    private String idUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_loai_chi__chi, container, false);
        //Asssign varible
        navigationView = view.findViewById(R.id.bottomnavigationView);
        navigationView.setBackground(null);
        floatingActionButton = view.findViewById(R.id.fabLoaiChi);
        listView = view.findViewById(R.id.listViewLoaiChi);

        //get userID
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        idUser = firebaseUser.getUid();
        //thong bao
        notificationDiaLog = new Notification_DiaLog(getActivity());
        //--------
        loaiChiList = new ArrayList<>();
        //----------khoi tao colection
        DbRef = FirebaseDatabase.getInstance().getReference().child("LoaiChi");
        //lay du lieu tu tren firebase ve app
        getDataFireBase();
        //the moi
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogInsert(Gravity.CENTER);
            }
        });
        //set onclick listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loaiChiModel = (LoaiChi) loaiChiAdapter.getItem(position);
                String _idLoaiChi = loaiChiModel.getIdLoaiChi();
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_update_chi_loaichi);
                dialog.setCancelable(false);
                EditText inputUpdateLoaiChi = (EditText) dialog.findViewById(R.id.input_Update_TextLoaiChi_DiaLog);
                TextView dateUpdateLoaiChi = (TextView) dialog.findViewById(R.id.text_Update_DateLoaiChi_DiaLog);
                Button btn_update = (Button) dialog.findViewById(R.id.btn_Update_Dialog_LoaiChi);
                Button btn_delete = (Button) dialog.findViewById(R.id.btn_Delete_Dialog_LoaiChi);
                ImageView imageClose = (ImageView) dialog.findViewById(R.id.image_close);

                inputUpdateLoaiChi.setText(loaiChiModel.getTitleLoaiChi());
                dateUpdateLoaiChi.setText(loaiChiModel.getDateLoaiChi());

                dateUpdateLoaiChi.setOnClickListener(new View.OnClickListener() {
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
                                        dateUpdateLoaiChi.setText(dayOfMonth + "-" + month + "-" + year);
                                    }
                                }, nam, thang, ngay);
                        datePickerDialog.show();

                    }
                });
                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String _title = inputUpdateLoaiChi.getText().toString();
                        String _date = dateUpdateLoaiChi.getText().toString();
                        upDateData(_idLoaiChi, _title, _date, idUser);
                        dialog.dismiss();
                        notificationDiaLog.showSuccessful(Gravity.CENTER);
                    }
                });
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteLoaiChi(_idLoaiChi);
                        dialog.dismiss();
                        notificationDiaLog.showSuccessful(Gravity.CENTER);
                    }
                });
                dialog.show();

            }
        });

        return view;
    }

    private void deleteLoaiChi(String idChi) {
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("LoaiChi").child(idChi);
        Task<Void> task = DbRef.removeValue();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                notificationDiaLog.showSuccessful(Gravity.CENTER);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                notificationDiaLog.showError(Gravity.CENTER);
            }
        });
    }

    private void upDateData(String _id, String _title, String _date, String _idUserLoaiChi) {
        DatabaseReference Dbref = FirebaseDatabase.getInstance().getReference("LoaiChi").child(_id);
        LoaiChi loaiChi = new LoaiChi(_id, _title, _date, _idUserLoaiChi);
        Dbref.setValue(loaiChi);
    }

    private void showDiaLogInsert(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chi_loaichi);
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
        EditText textLoaiChi = (EditText) dialog.findViewById(R.id.inputTextLoaiChi_DiaLog);
        TextView dateLoaiChi = (TextView) dialog.findViewById(R.id.textDateLoaiChi_DiaLog);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_Cancel_Dialog_LoaiChi);
        Button btn_save = (Button) dialog.findViewById(R.id.btn_Save_Dialog_LoaiChi);

        dateLoaiChi.setOnClickListener(new View.OnClickListener() {
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
                                dateLoaiChi.setText(dayOfMonth + "-" + month + "-" + year);
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
                String _idloaichi = UUID.randomUUID().toString();
                String _title = textLoaiChi.getText().toString();
                String _date = dateLoaiChi.getText().toString();
                if (_title.isEmpty()) {
                    notificationDiaLog.showWarning(Gravity.CENTER);
                    textLoaiChi.requestFocus();
                    return;
                } else if (_date.isEmpty()) {
                    notificationDiaLog.showWarning(Gravity.CENTER);
                    dateLoaiChi.requestFocus();
                    return;
                } else {
                    loaiChiModel = new LoaiChi(_idloaichi, _title, _date, idUser);
                    DbRef.child(loaiChiModel.getIdLoaiChi()).setValue(loaiChiModel);
                    notificationDiaLog.showSuccessful(Gravity.CENTER);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void getDataFireBase() {
        // get data to fire ->> app
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LoaiChi");
        databaseReference.orderByChild("idUserLoaiChi").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loaiChiList.clear();
                for (DataSnapshot LoaiChiDatasnaps : snapshot.getChildren()) {
                    LoaiChi loaiChi = LoaiChiDatasnaps.getValue(LoaiChi.class);
                    loaiChiList.add(loaiChi);
                }
                loaiChiAdapter = new LoaiChiAdapter(loaiChiList, getActivity());
                listView.setAdapter(loaiChiAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}