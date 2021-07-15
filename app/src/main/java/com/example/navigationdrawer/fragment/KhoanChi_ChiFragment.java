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
import com.example.navigationdrawer.adapter.SpinnerKhoanChiAdapter;
import com.example.navigationdrawer.model.KhoanChi;
import com.example.navigationdrawer.model.LoaiChi;
import com.example.navigationdrawer.notification.Notification_DiaLog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    private Spinner spinnerKhoanChi;
    private TextView textDateKhoanChi;
    private AutoCompleteTextView inputMoneyKhoanChi;
    private Button btn_SaveAmounts;
    private DatabaseReference databaseReference;
    private String select;
    private Notification_DiaLog notificationDiaLog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_khoan_chi__chi, container, false);
        //Assign varible
        khoanChiList = new ArrayList<>();
        loaiChiList = new ArrayList<>();
        notificationDiaLog = new Notification_DiaLog(getActivity());
        listViewKhoanChi = view.findViewById(R.id.listViewKhoanChi);
        spinnerKhoanChi = view.findViewById(R.id.spinnerKhoanChi);
        textDateKhoanChi = view.findViewById(R.id.textClickDate);
        inputMoneyKhoanChi = view.findViewById(R.id.autoCompleteTextView);
        btn_SaveAmounts = view.findViewById(R.id.btn_saveKhoanChi);
        loaiChiModel = new LoaiChi();
        //khoi tao bang tren firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("KhoanChi");
        //getDataBase
        getAllDataFireBase();
        //showSpinner
        spinnerKhoanChi();
        //click spinner
        selectSpinner();
        //chon date
        chonDate();
        //insert
        insertKhoanChi();
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
                TextView textDate = (TextView) dialog.findViewById(R.id.text_Update_Date_KhoanChi_DiaLog);
                ImageView imageClose = (ImageView) dialog.findViewById(R.id.image_close_KhoanChi);
                TextView textSpn = (TextView)dialog.findViewById(R.id.textspinnerUpdateKhoanChi);
                Button btn_Update = (Button) dialog.findViewById(R.id.btn_Update_Dialog__KhoanChi);
                Button btn_Delete = (Button) dialog.findViewById(R.id.btn_Delete_Dialog__KhoanChi);

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
                        String _money = inputMoney.getText().toString();
                        String _textdate = textDate.getText().toString();
                        String _title = textSpn.getText().toString();
                        upDateData(_id,_title,_money,_textdate);
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

    private void upDateData(String idKhoanChi, String title, String money, String date) {
        DatabaseReference Dbref = FirebaseDatabase.getInstance().getReference("KhoanChi").child(idKhoanChi);
        KhoanChi khoanChi = new KhoanChi(idKhoanChi, title, money, date);
        Dbref.setValue(khoanChi);
    }

    private void insertKhoanChi() {
        btn_SaveAmounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = UUID.randomUUID().toString();
                String title = select;
                String money = inputMoneyKhoanChi.getText().toString();
                String date = textDateKhoanChi.getText().toString();
                if (money.isEmpty()) {
                    inputMoneyKhoanChi.requestFocus();
                    notificationDiaLog.showWarning(Gravity.CENTER);
                    return;
                } else if (date.isEmpty()) {
                    textDateKhoanChi.requestFocus();
                    notificationDiaLog.showWarning(Gravity.CENTER);
                    return;
                } else {
                    KhoanChi khoanChi = new KhoanChi(id, title, money, date);
                    databaseReference.child(khoanChi.getIdKhoanChi()).setValue(khoanChi);
                    notificationDiaLog.showSuccessful(Gravity.CENTER);
                    inputMoneyKhoanChi.setText("");
                    textDateKhoanChi.setText("");
                }
            }
        });
    }

    private void chonDate() {
        textDateKhoanChi.setOnClickListener(new View.OnClickListener() {
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
                                textDateKhoanChi.setText(dayOfMonth + "-" + month + "-" + year);
                            }
                        }, nam, thang, ngay);
                datePickerDialog.show();
            }
        });
    }

    private void spinnerKhoanChi() {
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
    }

    private void selectSpinner() {
        spinnerKhoanChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select = ((LoaiChi) spinnerKhoanChiAdapter.getItem(position)).getTitleLoaiChi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getAllDataFireBase() {
        // get data to fire ->> app
        databaseReference.addValueEventListener(new ValueEventListener() {
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