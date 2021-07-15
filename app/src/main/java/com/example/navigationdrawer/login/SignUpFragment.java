package com.example.navigationdrawer.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    private TextInputEditText inputName,inputEmail,inputPassword,inputPhone;
    private Button signUp;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up,container,false);

        inputName = view.findViewById(R.id.EditTextInputName_signup);
        inputEmail = view.findViewById(R.id.EditTextInputEmail_signup);
        inputPassword = view.findViewById(R.id.EditTextInputPass_signup);
        inputPhone = view.findViewById(R.id.EditTextInputPhone_signup);
        signUp = view.findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String passworld = inputPassword.getText().toString().trim();
                String phone = inputPhone.getText().toString().trim();

                if (name.isEmpty()) {
                    inputName.setError("Full name is required!");
                    inputName.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    inputEmail.setError("Email is required!");
                    inputEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    inputEmail.setError("Please provide valid email!");
                    inputEmail.requestFocus();
                    return;
                }

                if (passworld.isEmpty()) {
                    inputPassword.setError("PassWorld is required!");
                    inputPassword.requestFocus();
                    return;
                }
                if (passworld.length() < 6) {
                    inputPassword.setError("Min passworld length should be 6  characters!");
                    inputPassword.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    inputPhone.setError("Phone  is required!");
                    inputPhone.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, passworld).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, email, phone);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //redirect to login layout
                                        Toast.makeText(getActivity(), "Register Successfull", Toast.LENGTH_SHORT).show();
                                        resetFromdata();
                                    } else {
                                        Toast.makeText(getActivity(), "Register Failed! Try again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Register Failed! Try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
    private void resetFromdata() {
        inputName.setText("");
        inputName.requestFocus();
        inputEmail.setText("");
        inputPassword.setText("");
        inputPhone.setText("");

    }

}