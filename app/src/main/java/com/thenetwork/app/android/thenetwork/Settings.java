package com.thenetwork.app.android.thenetwork;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;


public class Settings extends AppCompatActivity {

    Button manageNotifications, expandableInvite, sendFeedback, privacyPolicy, termsAndConditions, changePassword;
    ExpandableRelativeLayout expandableAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Settings");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
//    });
    }

    public void expandableButtonTerms(View view) {
        termsAndConditions = findViewById(R.id.btn_expandableTermsC);
        termsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://drive.google.com/file/d/1IvjtIzp3p2tOIzNN9o8WuYpwpFL5B80l/view?usp=sharing"));
                startActivity(intent);
            }
        });
    }

    public void expandableButtonInvite(View view) {
        expandableInvite = findViewById(R.id.btn_Invite);
        expandableInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "TheNetwork Application for finding your loved ones/ items");
                    String shareMessage = "Hey \n\nYou can even share what you are going through and get help from others. " +
                            "Connecting with people whilst sharing what you are going through made easier. \n\n ";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.thenetwork.app.android.thenetwork" +
                            BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Share Via"));
                } catch (Exception e) {
                    Toast.makeText(Settings.this, "Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void expandableButtonPrivacy(View view) {
        privacyPolicy = findViewById(R.id.btn_expandablePrivacyP);
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://drive.google.com/file/d/1E82irXPlSHz3K_2FO4tE7uDM3RnBCIOu/view?usp=sharing"));
                startActivity(intent);
            }
        });
    }

    public void expandableButtonSendF(View view) {
        sendFeedback = findViewById(R.id.btn_expandableSendFeedback);
        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, SendFeedBack.class);
                startActivity(intent);
            }
        });
    }

    public void expandableButtonNotifications(View view) {
        manageNotifications = findViewById(R.id.btn_manage_notifications);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, this.getPackageName());
            startActivity(intent);
        } else {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + this.getPackageName()));
            startActivity(intent);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void expandableAbout(View view) {

        expandableAbout = findViewById(R.id.layout_expandableAbout);
        expandableAbout.toggle(); // toggle expand and collapse
    }

    public void expandableButtonChangePassword(View view) {
        changePassword = findViewById(R.id.btn_expandableChangePassword);
        Intent intent = new Intent(Settings.this, ForgotPassword.class);
        startActivity(intent);
    }
}
