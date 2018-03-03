package com.example.mdjubairayeshi.firebaseauthdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        editTextEmail = (EditText)findViewById(R.id.editTextEmailLogin);
        editTextPassword = (EditText)findViewById(R.id.editTextPasswordLogin);
        textViewSignin = (TextView)findViewById(R.id.textViewInfoTag);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() !=null){
            //user already loged in
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
        }
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }
    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter the email Id",Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please enter the password",Toast.LENGTH_SHORT).show();
            return;
        }

        //if validations are ok
        //we will first show a progressbar
        progressDialog.setMessage("registering please wait....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //user is successfully registered and loged in
                    //we will start the profile activity here
                    //lets display the toast only
                    finish();
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                    Toast.makeText(getApplicationContext(),"You are successfully loged in",Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(getApplicationContext(),"Could not register. Please try again"+task.toString(),Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    @Override
    public void onClick(View view){
        if(view==buttonRegister)
        {
            //register the user
            registerUser();
        }
        else if (view == textViewSignin)
        {
            //Signin the user
            // we will open login activity here
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
