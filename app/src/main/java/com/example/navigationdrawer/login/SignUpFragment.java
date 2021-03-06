package com.example.navigationdrawer.login;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
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

    private TextInputEditText inputName, inputEmail, inputPassword, inputPhone;
    private Button signUp;
    private FirebaseAuth mAuth;
//    private ProgressDialog progressDialog;
//    private static  final int DELAY = 5000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up, container, false);

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
//                                        setProgressDialog();
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

    private Boolean validateName() {
        String val = inputName.getText().toString();
        if (val.isEmpty()) {
            inputName.setError("Field cannot be empty");
            return false;
        } else {
            inputName.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = inputEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            inputEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            inputEmail.setError("Invalid email address");
            return false;
        } else {
            inputEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePhone() {
        String val = inputPhone.getText().toString();
        if (val.isEmpty()) {
            inputPhone.setError("Field cannot be empty");
            return false;
        } else {
            inputPhone.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = inputPassword.getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z])" + //any letter
                "(?=.*[@#$%^&+=])" + //at least 1 special character
                "(?=\\S+$)" +//no white spaces
                ".{4,}" +    //at least 4 characters
                "$";
        if (val.isEmpty()) {
            inputPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            inputPassword.setError("Password is too weak");
            return false;
        } else {
            inputPassword.setError(null);
            return true;
        }
    }

//    private void setProgressDialog(){
//        progressDialog = new ProgressDialog(getActivity());
//        //show dialog
//        progressDialog.show();
//        progressDialog.setContentView(R.layout.layout_progress_dialog);
//
//        progressDialog.getWindow().setBackgroundDrawableResource(
//                android.R.color.transparent
//        );
//    }

}