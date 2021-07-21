package com.example.navigationdrawer.notification;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.navigationdrawer.R;

public class Notification_DiaLog {
    Context context;

    public Notification_DiaLog(Context context) {
        this.context = context;
    }

    public void showSuccessful(int gravity) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_successful);
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
        Button btnSuccess = (Button) dialog.findViewById(R.id.button_successful);
        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showWarning(int gravity) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_warning);
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
        Button btnWarning = (Button) dialog.findViewById(R.id.button_warning);
        btnWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showError(int gravity) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_failed);
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
        Button btnFailed = (Button) dialog.findViewById(R.id.button_failed);
        btnFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
