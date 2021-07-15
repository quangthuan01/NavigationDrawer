package com.example.navigationdrawer.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.navigationdrawer.R;

public class ProgressButton {

    private CardView cardView;
    private ConstraintLayout constraintLayout;
    private ProgressBar progressBar;
    private TextView textView;

    Animation fade_in;

    ProgressButton(Context context, View view) {
        fade_in = AnimationUtils.loadAnimation(context, R.anim.fade_in);

        cardView = view.findViewById(R.id.cardView);
        constraintLayout = view.findViewById(R.id.contrain_layout);
        progressBar = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.textViewStart);

    }

    void buttonActivated() {
        progressBar.setAnimation(fade_in);
        progressBar.setVisibility(View.VISIBLE);
        textView.setAnimation(fade_in);
        textView.setText("Please wait...");
    }

}
