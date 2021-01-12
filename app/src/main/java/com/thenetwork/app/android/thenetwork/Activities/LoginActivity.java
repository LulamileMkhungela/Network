package com.thenetwork.app.android.thenetwork.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thenetwork.app.android.thenetwork.ForgotPassword;
import com.thenetwork.app.android.thenetwork.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";


    //Email and Password
    private EditText loginEmailText;
    private EditText loginPassText;

    private ProgressBar loginProgress;


    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.LoginTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginEmailText = findViewById(R.id.login_email);
        loginPassText = findViewById(R.id.login_password);
        //Buttons and Views
        Button loginBtn = findViewById(R.id.login_btn);
        loginProgress = findViewById(R.id.login_progress);
        TextView goToForgotPassword = findViewById(R.id.txt_forgot_password_login);
        TextView goToRegister = findViewById(R.id.txt_to_signUp);
        ImageView closeLogin = findViewById(R.id.img_close_login);

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        goToForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(i);

            }
        });
        closeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, AuthActivity.class);
                startActivity(i);
            }
        });

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        //Buttons
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String loginEmail = loginEmailText.getText().toString();
                String loginPass = loginPassText.getText().toString();

                if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)) {

                    loginProgress.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(loginEmail, loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                sendToMain();

                            } else {

                                try {
                                    String exception = task.getException().getMessage();
                                    Snackbar.make(findViewById(R.id.main_layout), "Error : " + exception, Snackbar.LENGTH_SHORT).show();

                                } catch (Exception e) {
                                    Log.e("ERROR", e.getMessage());
                                }
                            }
                            loginProgress.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent mainIntent = new Intent(LoginActivity.this, com.thenetwork.app.android.thenetwork.Activities.MainActivity.class);
        startActivity(mainIntent);
        finish();
    }


    private void sendToSetup() {
        Intent setupIntent = new Intent(LoginActivity.this, com.thenetwork.app.android.thenetwork.Activities.SetupActivity.class);
        startActivity(setupIntent);
        finish();

    }

}
