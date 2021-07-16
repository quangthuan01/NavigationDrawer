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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.model.KhoanChi;
import com.example.navigationdrawer.model.LoaiThu;
import com.example.navigationdrawer.notification.Notification_DiaLog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

public class LoaiThuAdapter extends RecyclerView.Adapter<LoaiThuAdapter.MyViewHolder> {
    List<LoaiThu> data;
    Context context;
    public LoaiThuAdapter(List<LoaiThu> data,Context context) {
        this.data = data;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new MyViewHolder(

                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_loaithu, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull  LoaiThuAdapter.MyViewHolder holder, int position) {
        LoaiThu loaiThu = data.get(position);
        //set data
        holder.titleLoaithu.setText(loaiThu.getTitleLoaiThu());
        holder.dateLoaithu.setText(loaiThu.getDateLoaiThu());
        //setOnclickListener
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiThu  loaiThu = data.get(position) ;
                String _id = loaiThu.getIdLoaiThu();
                Dialog dialog = new Dialog(v.getContext());
                //set click
                dialog.setCancelable(false);
                //setmau dialog bo vien
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //set layout
                dialog.setContentView(R.layout.dialog_update_thu_loaithu);

                EditText inputTitle = (EditText) dialog.findViewById(R.id.input_Update_TextLoaiThu_DiaLog);
                TextView textDate = (TextView) dialog.findViewById(R.id.text_Update_DateLoaiThu_DiaLog);
                ImageView imageClose = (ImageView) dialog.findViewById(R.id.image_close_loaithu);
                Button btn_Update = (Button) dialog.findViewById(R.id.btn_Update_Dialog_LoaiThu);
                Button btn_Delete = (Button) dialog.findViewById(R.id.btn_Delete_Dialog_LoaiThu);

                inputTitle.setText(loaiThu.getTitleLoaiThu());
                textDate.setText(loaiThu.getDateLoaiThu());


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
                        String _title = inputTitle.getText().toString();
                        String _textdate = textDate.getText().toString();
                        upDateData(_id,_title,_textdate);
                        dialog.dismiss();
                        Notification_DiaLog notificationDiaLog = new Notification_DiaLog(context);
                        notificationDiaLog.showSuccessful(Gravity.CENTER);
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

    private void deleteLoaiChi(String idLoaiThu) {
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("LoaiThu").child(idLoaiThu);
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

    private void upDateData(String idLoaiThu, String title, String date) {
        DatabaseReference Dbref = FirebaseDatabase.getInstance().getReference("LoaiThu").child(idLoaiThu);
        LoaiThu loaiThu = new LoaiThu(idLoaiThu, title, date);
        Dbref.setValue(loaiThu);
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
            TextView titleLoaithu,dateLoaithu;
            LinearLayout linearLayout;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            titleLoaithu  = itemView.findViewById(R.id.item_textLoaiThu);
            dateLoaithu = itemView.findViewById(R.id.item_textDateLoaiThu);
            linearLayout = itemView.findViewById(R.id.layout_container_item_recyclerview);
        }
    }
}
