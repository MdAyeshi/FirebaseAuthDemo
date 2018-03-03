package com.example.mdjubairayeshi.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonLogout, saveUserdata;
    private TextView textViewProfile;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private EditText userName, Address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        buttonLogout = (Button) findViewById(R.id.buttonLogedOut);
        buttonLogout.setOnClickListener(this);
        saveUserdata = (Button)findViewById(R.id.buttonSaveUserData);
        userName = (EditText)findViewById(R.id.editTextUserName);
        Address = (EditText)findViewById(R.id.editTextUserAddress);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        textViewProfile =(TextView)findViewById(R.id.textViewProfile);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null)
        {
            startActivity(new Intent(this,MainActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        textViewProfile.setText("Welcome "+user.getEmail());
        saveUserdata.setOnClickListener(this);
    }
    private void saveUserInformation(){
        String name =userName.getText().toString();
        String add = Address.getText().toString();
        UserInformation userInformation = new UserInformation(name,add);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this,"Information Saved successfully",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v==buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        if (v==saveUserdata){
            saveUserInformation();
        }

    }
}
