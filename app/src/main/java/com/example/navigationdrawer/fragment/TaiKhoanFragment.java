package com.example.navigationdrawer.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.login.LoginActivity;
import com.example.navigationdrawer.model.User;
import com.example.navigationdrawer.notification.Notification_DiaLog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TaiKhoanFragment extends Fragment {
    private TextView textSignOut, textEmail, textPhone, textFullName, textFull;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private String idUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_taikhoan, container, false);
        textSignOut = view.findViewById(R.id.textSignOut);
        textEmail = view.findViewById(R.id.textEmailProfile);
        textPhone = view.findViewById(R.id.textPhoneProfile);
        textFullName = view.findViewById(R.id.textFullNameProfile);
        textFull = view.findViewById(R.id.textFull);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        idUser = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        // get   data to fire ->> app
        databaseReference.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    String name = user.name;
                    String email = user.email;
                    String phone = user.phone;
                    textEmail.setText(email);
                    textPhone.setText(phone);
                    textFull.setText(name);
                    textFullName.setText(name);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Notification_DiaLog notification_diaLog = new Notification_DiaLog(getActivity());
                notification_diaLog.showError(Gravity.CENTER);
            }
        });
        textSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut(Gravity.CENTER);
            }
        });
        return view;
    }

    private void logOut(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_exit_sign_out_);
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
        Button btn_exit = (Button) dialog.findViewById(R.id.btn_Exit);
        Button btn_logout = (Button) dialog.findViewById(R.id.btn_LogOut);
        ImageView image_Close_Account = (ImageView) dialog.findViewById(R.id.image_Close_Account);

        image_Close_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent out = new Intent(Intent.ACTION_MAIN);
                out.addCategory(Intent.CATEGORY_HOME);
                startActivity(out);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                dialog.dismiss();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        dialog.show();


    }
}
