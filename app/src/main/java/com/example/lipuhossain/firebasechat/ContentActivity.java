package com.example.lipuhossain.firebasechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.lipuhossain.firebasechat.adapters.UserListAdapter;
import com.example.lipuhossain.firebasechat.model.UserObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabaseReference;
    DatabaseReference userDb;
    List<UserObject> userList = new ArrayList<UserObject>();
    UserListAdapter adapter;
    ListView user_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        user_listview = (ListView) findViewById(R.id.user_list);
        userDb = firebaseDatabaseReference.child("users");
        adapter = new UserListAdapter(this, userList);
        user_listview.setAdapter(adapter);
        populateUserList();
        checkDataChange();
    }

    private void checkDataChange() {
        ValueEventListener userDbListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //User ost = dataSnapshot.getValue(Post.class);
                // ...
                updateList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        };

        userDb.addValueEventListener(userDbListener);
    }

    private void populateUserList() {
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                updateList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateList(DataSnapshot dataSnapshot) {
        userList = new ArrayList<UserObject>();

        // Result will be holded Here
        for (DataSnapshot dsp : dataSnapshot.getChildren()) {

            String user_id = (String) dsp.child("user_id").getValue();
            String user_name = (String) dsp.child("user_name").getValue();
            String is_logged_in = (String) dsp.child("is_logged_in").getValue();
            String user_status = (String) dsp.child("user_status").getValue();
            String user_gcm_id = (String) dsp.child("user_gcm_id").getValue();

            UserObject user = new UserObject();
            user.setUser_id(user_id);
            user.setUser_name(user_name);
            user.setIs_logged_in(is_logged_in);
            user.setUser_status(user_status);
            user.setUser_gcm_id(user_gcm_id);

            userList.add(user); //add result into array list

        }
        adapter = new UserListAdapter(ContentActivity.this, userList);
        user_listview.setAdapter(adapter);
    }

    public void logout(View view) {
        updateUserStatus(firebaseAuth.getCurrentUser());
        firebaseAuth.signOut();
        finish();
    }

    private void updateUserStatus(FirebaseUser user) {
        UserObject user0bject = new UserObject();
        user0bject.setUser_id(user.getUid());
        user0bject.setUser_name(user.getEmail());
        user0bject.setIs_logged_in("false");
        user0bject.setUser_gcm_id("dummy11232423421");
        user0bject.setUser_status("N/A");

        //update users status
        firebaseDatabaseReference.child("users").child(user0bject.getUser_id()).setValue(user0bject);

    }
}
