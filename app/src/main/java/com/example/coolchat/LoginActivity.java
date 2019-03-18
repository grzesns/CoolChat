package com.example.coolchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolBar;

    private TextInputLayout mLoginEmail;
    private  TextInputLayout mLoginPassword;
    private Button mLoginButton;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mLoginEmail = (TextInputLayout) findViewById(R.id.loginEmail);
        mLoginPassword = (TextInputLayout) findViewById(R.id.loginPassword);
        mToolBar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");
        mLoginButton = (Button) findViewById(R.id.loginButton);

        mProgressDialog = new ProgressDialog(this);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mLoginEmail.getEditText().getText().toString();
                String password = mLoginPassword.getEditText().getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
                    mProgressDialog.setTitle("Logging in");
                    mProgressDialog.setMessage("Please wait until we check your credentials");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();
                    loginUser(email, password);
                }
            }
        });
    }



    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    mProgressDialog.dismiss();
                    Intent mainIntend = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntend.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntend);
                    finish();
                }else {
                    mProgressDialog.hide();
                    Toast.makeText(LoginActivity.this, "Cannot Sign In. Please check the form and try again", Toast.LENGTH_LONG);
                }
            }
        });

    }
}