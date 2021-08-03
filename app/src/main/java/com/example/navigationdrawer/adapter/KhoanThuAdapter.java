package com.example.navigationdrawer.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.model.KhoanThu;
import com.example.navigationdrawer.notification.Notification_DiaLog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KhoanThuAdapter.MyViewHolder(

                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_khoanthu, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull KhoanThuAdapter.MyViewHolder holder, int position) {
        //set du lieu len recyclerview
        KhoanThu khoanThu = data.get(position);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String idUser = firebaseUser.getUid();
        Double d = Double.valueOf(khoanThu.getMoneyKhoanThu());
        holder.inputTitle.setText(khoanThu.getTitleKhoanThu());
        holder.textDetail.setText(khoanThu.getOldTitle());
        holder.inputMoney.setText(String.format("%,.1f", d) + " $");
        holder.textDate.setText(khoanThu.getDateKhoanThu());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KhoanThu khoanThu1 = data.get(position);
                String _id = khoanThu1.getIdKhoanThu();

                Dialog dialog = new Dialog(v.getContext(), R.style.DialogAnimation);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Window window = dialog.getWindow();
                //check
                if (window == null) {
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                window.setAttributes(layoutParams);
                //set click
                dialog.setCancelable(false);
                //setmau dialog bo vien
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //set layout
                dialog.setContentView(R.layout.dialog_update_thu_khoanthu);

                EditText inputMoney = (EditText) dialog.findViewById(R.id.input_Update_MoneyKhoanThu_DiaLog);
                EditText inputTypes = (EditText) dialog.findViewById(R.id.input_Update_TypysKhoanThu_DiaLog);
                TextView textDate = (TextView) dialog.findViewById(R.id.text_Update_DateKhoanThu_DiaLog);
                TextView textTitle = (TextView) dialog.findViewById(R.id.text_Update_SpnKhoanThu_DiaLog);
                ImageView imageClose = (ImageView) dialog.findViewById(R.id.image_close_Khoanthu);
                Button btn_Update = (Button) dialog.findViewById(R.id.btn_Update_Dialog_KhoanThu);
                Button btn_Delete = (Button) dialog.findViewById(R.id.btn_Delete_Dialog_KhoanThu);

                Double moneyST = Double.valueOf(khoanThu1.getMoneyKhoanThu());
                textTitle.setText(khoanThu1.getTitleKhoanThu());
                inputTypes.setText(khoanThu1.getOldTitle());
                inputMoney.setText(String.format("%,.1f", moneyST) + " $");
                textDate.setText(khoanThu1.getDateKhoanThu());


                textDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
                        int thang = calendar.get(Calendar.MONTH);
                        int nam = calendar.get(Calendar.YEAR);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        textDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
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
                        String _title = textTitle.getText().toString();
                        String _types = inputTypes.getText().toString();
                        int _money = Integer.parseInt(inputMoney.getText().toString());
                        String _textdate = textDate.getText().toString();

                        if (_money < 5) {
                            inputMoney.requestFocus();
                            inputMoney.setError("Minimum is 5.0$");
                            return;
                        } else if (_money > 10000000) {
                            inputMoney.requestFocus();
                            inputMoney.setError("Maximum is 10,000,000.0$");
                            return;
                        } else {
                            upDateData(_id, _title, _types, _money, _textdate, idUser);
                            dialog.dismiss();
                            Notification_DiaLog notificationDiaLog = new Notification_DiaLog(context);
                            notificationDiaLog.showSuccessful(Gravity.CENTER);
                        }


                    }
                });
                btn_Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteLoaiChi(_id);
                        dialog.dismiss();
                        Notification_DiaLog notificationDiaLog = new Notification_DiaLog(context);
                        notificationDiaLog.showSuccessful(Gravity.CENTER);
                    }
                });
                dialog.show();

            }
        });
    }

    private void deleteLoaiChi(String idKhoanThu) {
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("KhoanThu").child(idKhoanThu);
        Task<Void> task = DbRef.removeValue();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Notification_DiaLog notificationDiaLog = new Notification_DiaLog(context);
                notificationDiaLog.showSuccessful(Gravity.CENTER);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Notification_DiaLog notificationDiaLog = new Notification_DiaLog(context);
                notificationDiaLog.showError(Gravity.CENTER);
            }
        });
    }

    private void upDateData(String idKhoanThu, String title, String types, int money, String date, String idUser) {
        DatabaseReference Dbref = FirebaseDatabase.getInstance().getReference("KhoanThu").child(idKhoanThu);
        KhoanThu khoanThu = new KhoanThu(idKhoanThu, title, types, money, date, idUser);
        Dbref.setValue(khoanThu);
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


        TextView textDate, inputTitle, textDetail, inputMoney;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            inputTitle = itemView.findViewById(R.id.item_TheLoaiKhoanThu);
            textDetail = itemView.findViewById(R.id.item_DetailTheLoaiKhoanThu);
            inputMoney = itemView.findViewById(R.id.item_textSoTienThu);
            textDate = itemView.findViewById(R.id.item_textDateKhoanThu);
            linearLayout = itemView.findViewById(R.id.layout_container_item_khoanthu_recyclerView);

        }
    }
}
