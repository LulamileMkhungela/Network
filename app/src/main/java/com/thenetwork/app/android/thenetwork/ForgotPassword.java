package com.thenetwork.app.android.thenetwork;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    TextView backToLoginBtn, submit_area;
    private EditText inputEmail;
    private LinearLayout rootLayout;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        backToLoginBtn = findViewById(R.id.btn_backToLogin_forget_password);
        submit_area = findViewById(R.id.btn_submit_forget_password);
        inputEmail = findViewById(R.id.et_email_forgot_password);
        rootLayout = findViewById(R.id.root_forgot_password);
        progressBar = findViewById(R.id.progress_bar_forgot_password);

        auth = FirebaseAuth.getInstance();

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submit_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Snackbar snackbar = Snackbar.make(rootLayout, "Please Enter Registered  Email address", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Snackbar snackbar = Snackbar.make(rootLayout,
                                            "We have sent you instructions to reset your password, Please check your txt_cell_number_other_users!",
                                            Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            onBackPressed();
                                        }
                                    }, 2000);
                                } else {
                                    Snackbar snackbar = Snackbar.make(rootLayout,
                                            "Failed to reset password, Please try again", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}
