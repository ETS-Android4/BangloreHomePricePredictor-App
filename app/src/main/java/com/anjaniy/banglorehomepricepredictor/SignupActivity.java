package com.anjaniy.banglorehomepricepredictor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.anjaniy.banglorehomepricepredictor.databinding.ActivitySignupBinding;
import com.anjaniy.banglorehomepricepredictor.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore database;
    private User user;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();


        binding.signupbutton.setOnClickListener(v -> {

            //Validations:
            if(binding.emailForSignup.getText().toString().isEmpty()){
                binding.emailForSignup.setError("Enter your email address");
                binding.emailForSignup.requestFocus();
                return;
            }

            if(binding.passwordForSignup.getText().toString().isEmpty()){
                binding.passwordForSignup.setError("Enter your password");
                binding.passwordForSignup.requestFocus();
                return;
            }

            user = new User();
            user.setEmailAddress(binding.emailForSignup.getText().toString());
            user.setPassword(binding.passwordForSignup.getText().toString());

            showProgressDialog();
            auth.createUserWithEmailAndPassword(binding.emailForSignup.getText().toString(), binding.passwordForSignup.getText().toString()).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    database.collection("Users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).set(user).addOnSuccessListener(unused -> {
                        Toast.makeText(SignupActivity.this, "Account is created!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        dismissDialog();
                        finishAffinity();
                    });
                }
                else{
                    Toast.makeText(SignupActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    dismissDialog();
                }
            });
        });

        binding.alreadyAccountButton.setOnClickListener(v -> startActivity(new Intent(SignupActivity.this, LoginActivity.class)));

    }

    private void showProgressDialog() {
        dialog = new ProgressDialog(SignupActivity.this);
        dialog.show();
        dialog.setContentView(R.layout.progress_dialog_main);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    public void dismissDialog() {
        dialog.dismiss();
    }
}