package com.thenetwork.app.android.thenetwork.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.thenetwork.app.android.thenetwork.R;


public class SafetyDialogActivity extends AppCompatActivity {
Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_dialog);
        cancel = findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SafetyDialogActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
