package com.example.lipuhossain.firebasechat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.lipuhossain.firebasechat.model.UserObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.et_mail)
    EditText et_mail;
    @Bind(R.id.et_password)
    EditText et_password;

    private ProgressDialog progressbar;
    private FirebaseAuth firebaseAuth;
    private String email;
    private String password;
    private DatabaseReference firebaseDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, ContentActivity.class));
        }
        progressbar = new ProgressDialog(this);
        progressbar.setMessage("Signin in..");
    }

    public void login(View view) {
        if (et_mail.getText().toString().isEmpty())
            return;
        else if (et_password.getText().toString().isEmpty())
            return;
        else
            loginWithFirebase();
    }

    private void loginWithFirebase() {
        progressbar.show();
        email = et_mail.getText().toString().trim();
        password = et_password.getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbar.dismiss();
                if (task.isSuccessful()) {
                    //successfully login go to main page
                    startActivity(new Intent(LoginActivity.this, ContentActivity.class));
                    updateUserStatus(task.getResult().getUser());
                }
            }
        });
    }

    private void updateUserStatus(FirebaseUser user) {
        UserObject user0bject = new UserObject();
        user0bject.setUser_id(user.getUid());
        user0bject.setUser_name(user.getEmail());
        user0bject.setIs_logged_in("true");
        user0bject.setUser_gcm_id("dummy11232423421");
        user0bject.setUser_status("Online");

        //update users status
        firebaseDatabaseReference.child("users").child(user0bject.getUser_id()).setValue(user0bject);

    }
}
