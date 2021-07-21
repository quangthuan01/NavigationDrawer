package com.example.navigationdrawer.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.style.TtsSpan;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationdrawer.MainActivity;
import com.example.navigationdrawer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    private TextInputEditText inputEmail, inputPassword;
    private TextView forgetPassword;
    private Button signIn;
    private FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_sign_in, container, false);

        inputEmail = view.findViewById(R.id.EditTextInputEmail);
        inputPassword = view.findViewById(R.id.EditTextInputPass);
        forgetPassword = view.findViewById(R.id.forget_pass);
        signIn = view.findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String passworld = inputPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    inputEmail.setError("Email is required!");
                    inputEmail.requestFocus();
                    return;

                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    inputEmail.setError("Please enter a valid email!");
                    inputEmail.requestFocus();
                    return;
                }
                if (passworld.isEmpty()) {
                    inputPassword.setError("Passworld is required!");
                    inputPassword.requestFocus();
                    return;

                }
                if (passworld.length() < 6) {
                    inputPassword.setError("Min passworld length should be 6  characters!");
                    inputPassword.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, passworld).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //check email successfull
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()) {
                                //redirect to user profile
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            } else {
                                //send email
                                user.sendEmailVerification();
                                Toast.makeText(getActivity(), "Check your email to verify your account!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Failed to Login! Please check your credentials ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ForgetPassword.class));
            }
        });
        return view;
    }
}