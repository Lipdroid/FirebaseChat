package com.example.lipuhossain.firebasechat;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class RegistrationActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        progressbar = new ProgressDialog(this);
        progressbar.setMessage("Registering");
    }

    public void register(View view) {
        if (et_mail.getText().toString().isEmpty())
            return;
        else if (et_password.getText().toString().isEmpty())
            return;
        else
            registerInFireBase();
    }

    private void registerInFireBase() {
        progressbar.show();
        email = et_mail.getText().toString().trim();
        password = et_password.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbar.dismiss();
                if (task.isSuccessful()) {
                    //succesfully Registererd
                    writeNewUser(task.getResult().getUser());
                    Toast.makeText(RegistrationActivity.this, "succesfully Registererd", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                }
            }
        });

    }

    private void writeNewUser(FirebaseUser user) {
        UserObject user0bject = new UserObject();
        user0bject.setUser_id(user.getUid());
        user0bject.setUser_name(user.getEmail());
        user0bject.setIs_logged_in("false");
        user0bject.setUser_gcm_id("dummy11232423421");
        user0bject.setUser_status("N/A");

        //insert a new user
        firebaseDatabaseReference.child("users").child(user0bject.getUser_id()).setValue(user0bject);


    }

}
