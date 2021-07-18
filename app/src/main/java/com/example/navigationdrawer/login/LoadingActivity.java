package com.example.navigationdrawer.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.navigationdrawer.MainActivity;
import com.example.navigationdrawer.R;

public class LoadingActivity extends AppCompatActivity {
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        view  = findViewById(R.id.myProgessBarButton);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressButton progressButton = new ProgressButton(LoadingActivity.this,view);
                progressButton.buttonActivated();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoadingActivity.this, WellcomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },5000);

            }
        });
    }
}