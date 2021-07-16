package com.example.navigationdrawer.fragment;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class TaiKhoanFragment extends Fragment {
    private TextView textSignOut;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_taikhoan,container,false);
            textSignOut = view.findViewById(R.id.textSignOut);
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
        dialog.setCancelable(true);
        Button btn_exit = (Button) dialog.findViewById(R.id.btn_Exit);
        Button btn_logout = (Button) dialog.findViewById(R.id.btn_LogOut);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent out= new Intent(Intent.ACTION_MAIN);
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
