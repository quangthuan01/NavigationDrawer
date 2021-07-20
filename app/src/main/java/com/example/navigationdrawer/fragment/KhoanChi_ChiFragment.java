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

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.adapter.KhoanChiAdapter;
import com.example.navigationdrawer.adapter.SpinnerAdapterKhoanThu;
import com.example.navigationdrawer.adapter.SpinnerKhoanChiAdapter;
import com.example.navigationdrawer.model.KhoanChi;
import com.example.navigationdrawer.model.KhoanThu;
import com.example.navigationdrawer.model.LoaiChi;
import com.example.navigationdrawer.model.LoaiThu;
import com.example.navigationdrawer.notification.Notification_DiaLog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class KhoanChi_ChiFragment extends Fragment {
    private KhoanChi khoanChiModel;
    private List<KhoanChi> khoanChiList;
    private List<LoaiChi> loaiChiList;
    private KhoanChiAdapter khoanChiAdapter;
    private LoaiChi loaiChiModel;
    private SpinnerKhoanChiAdapter spinnerKhoanChiAdapter;
    private ListView listViewKhoanChi;
    private FloatingActionButton fabKhoanChi;
    private DatabaseReference databaseReference;
    private String select;
    private Notification_DiaLog notificationDiaLog;
    private FirebaseUser firebaseUser;
    private String idUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_khoan_chi__chi, container, false);
        //Assign varible
        khoanChiList = new ArrayList<>();
        loaiChiList = new ArrayList<>();


        //get userID
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        idUser = firebaseUser.getUid();
        //thong bao
        notificationDiaLog = new Notification_DiaLog(getActivity());
        listViewKhoanChi = view.findViewById(R.id.listViewKhoanChi);
        fabKhoanChi = view.findViewById(R.id.fabKhoanChi);
        loaiChiModel = new LoaiChi();
        //khoi tao bang tren firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("KhoanChi");
        //getDataBase
        getAllDataFireBase();
        //insert
        fabKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertKhoanChi(Gravity.TOP);
            }
        });

        //setOnItem
        setOnItemClick();

        return view;
    }

    private void setOnItemClick() {
        listViewKhoanChi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                khoanChiModel = (KhoanChi) khoanChiAdapter.getItem(position);
                String _id = khoanChiModel.getIdKhoanChi();
                Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_update_chi_khoanchi);

                AutoCompleteTextView inputMoney = (AutoCompleteTextView) dialog.findViewById(R.id.input_Update_Text_KhoanChi_DiaLog);
                AutoCompleteTextView inputOldTitle = (AutoCompleteTextView) dialog.findViewById(R.id.input_Update_OldTitle_KhoanChi_DiaLog);
                TextView textDate = (TextView) dialog.findViewById(R.id.text_Update_Date_KhoanChi_DiaLog);
                ImageView imageClose = (ImageView) dialog.findViewById(R.id.image_close_KhoanChi);
                TextView textSpn = (TextView)dialog.findViewById(R.id.textspinnerUpdateKhoanChi);
                Button btn_Update = (Button) dialog.findViewById(R.id.btn_Update_Dialog__KhoanChi);
                Button btn_Delete = (Button) dialog.findViewById(R.id.btn_Delete_Dialog__KhoanChi);

                inputOldTitle.setText(khoanChiModel.getOldKhoanChi());
                inputMoney.setText(khoanChiModel.getMoneyKhoanChi());
                textDate.setText(khoanChiModel.getDateKhoanChi());
                textSpn.setText(khoanChiModel.getTitleKhoanChi());

                textDate.setOnClickListener(new View.OnClickListener() {
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
                                        textDate.setText(dayOfMonth + "-" + month + "-" + year);
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
                btn_Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int _money = Integer.parseInt(inputMoney.getText().toString());
                        String _textdate = textDate.getText().toString();
                        String _title = textSpn.getText().toString();
                        String _oldtitle = inputOldTitle.getText().toString();
                        upDateData(_id,_title,_oldtitle,_money,_textdate,idUser);
                        dialog.dismiss();
                        notificationDiaLog.showSuccessful(Gravity.CENTER);
                    }
                });
                btn_Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteLoaiChi(_id);
                        dialog.dismiss();
                        notificationDiaLog.showSuccessful(Gravity.CENTER);
                    }
                });
            dialog.show();
            }
        });
    }

    private void deleteLoaiChi(String idKhoanChi) {
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("KhoanChi").child(idKhoanChi);
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

    private void upDateData(String idKhoanChi, String title,String oldtitle, int money, String date,String idUser) {
        DatabaseReference Dbref = FirebaseDatabase.getInstance().getReference("KhoanChi").child(idKhoanChi);
        KhoanChi khoanChi = new KhoanChi(idKhoanChi, title,oldtitle, money, date,idUser);
        Dbref.setValue(khoanChi);
    }

    private void insertKhoanChi(int gravity) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_bottom_sheet_khoanchi);
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
        dialog.setCancelable(false);
        TextInputEditText textTitle = (TextInputEditText) dialog.findViewById(R.id.inputTitleKhoanChi);
        TextInputEditText textMoney = (TextInputEditText) dialog.findViewById(R.id.inputMoneyKhoanChi);
        TextView dateKhoanChi = (TextView) dialog.findViewById(R.id.textDateKhoanChi);
        Spinner spinnerKhoanChi = (Spinner) dialog.findViewById(R.id.spnKhoanChi);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancelKhoanChi);
        Button btn_save = (Button) dialog.findViewById(R.id.btn_saveKhoanChi);
        //get data ->> spinner
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("LoaiChi");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loaiChiList.clear();
                for (DataSnapshot LoaiChiDatasnaps : snapshot.getChildren()) {
                    LoaiChi loaiChi = LoaiChiDatasnaps.getValue(LoaiChi.class);
                    loaiChiList.add(loaiChi);
                }
                spinnerKhoanChiAdapter = new SpinnerKhoanChiAdapter(loaiChiList, getActivity());
                spinnerKhoanChi.setAdapter(spinnerKhoanChiAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //set onClick spinner
        spinnerKhoanChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select = ((LoaiChi) spinnerKhoanChiAdapter.getItem(position)).getTitleLoaiChi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //onClick datetime
        dateKhoanChi.setOnClickListener(new View.OnClickListener() {
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
                                dateKhoanChi.setText(dayOfMonth + "-" + month + "-" + year);
                            }
                        }, nam, thang, ngay);
                datePickerDialog.show();

            }
        });
        //close dialog
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //save data
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _idKhoanChi = UUID.randomUUID().toString();
                String _spn = select;
                String _title = textTitle.getText().toString();
                int _money = Integer.parseInt(textMoney.getText().toString());
                String _date = dateKhoanChi.getText().toString();
                if (_title.isEmpty()) {
                    notificationDiaLog.showWarning(Gravity.CENTER);
                    textTitle.requestFocus();
                    return;
                } //else if () {
//                    notificationDiaLog.showError(Gravity.CENTER);
//                    textMoney.requestFocus();
//                    return;//                }
                else if (_date.isEmpty()) {
                    notificationDiaLog.showWarning(Gravity.CENTER);
                    dateKhoanChi.requestFocus();
                    return;
                } else {
                    khoanChiModel = new KhoanChi(_idKhoanChi,_spn, _title, _money, _date,idUser);
                    databaseReference.child(khoanChiModel.getIdKhoanChi()).setValue(khoanChiModel);
                    notificationDiaLog.showSuccessful(Gravity.CENTER);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void getAllDataFireBase() {
        //orderByChild ("idUser"). equalTo ("02")
        // get data to fire ->> app
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("KhoanChi");
        databaseReference.orderByChild("idUserKhoanChi").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                khoanChiList.clear();
                for (DataSnapshot KhoanChiDatasnaps : snapshot.getChildren()) {
                    KhoanChi khoanChi = KhoanChiDatasnaps.getValue(KhoanChi.class);
                    khoanChiList.add(khoanChi);
                }
                khoanChiAdapter = new KhoanChiAdapter(khoanChiList, getActivity());
                listViewKhoanChi.setAdapter(khoanChiAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}