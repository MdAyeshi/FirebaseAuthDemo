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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonSignIn = (Button)findViewById(R.id.buttonSignin);
        editTextEmail = (EditText)findViewById(R.id.editTextEmailLogin);
        editTextPassword = (EditText)findViewById(R.id.editTextPasswordLogin);
        textViewRegistration = (TextView)findViewById(R.id.textViewInfoTagSignin);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null) { // to check if user is already logedin
            // start profile activity
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

        }
        progressDialog = new ProgressDialog(this);
        buttonSignIn.setOnClickListener(this);
        textViewRegistration.setOnClickListener(this);
    }
    private void userLogin(){
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
        progressDialog.setMessage("Signing please wait..");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==buttonSignIn){
            userLogin();

        }
        if(v == textViewRegistration){
            finish();
            startActivity(new Intent(this,MainActivity.class));

        }
    }
}
