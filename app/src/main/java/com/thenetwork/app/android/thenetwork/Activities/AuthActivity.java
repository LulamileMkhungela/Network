package com.thenetwork.app.android.thenetwork.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.thenetwork.app.android.thenetwork.Fragments.SigninFragment;
import com.thenetwork.app.android.thenetwork.Fragments.SignupFragment;
import com.thenetwork.app.android.thenetwork.R;
import com.thenetwork.app.android.thenetwork.WelcomeActivity;

public class AuthActivity extends AppCompatActivity {

    private SigninFragment signinFragment;
    private SignupFragment signupFragment;
    private WelcomeActivity welcomeActivity;
    private com.thenetwork.app.android.thenetwork.Activities.SplashActivity splashActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        signinFragment = new SigninFragment();
        signupFragment = new SignupFragment();
        welcomeActivity = new WelcomeActivity();
        splashActivity = new com.thenetwork.app.android.thenetwork.Activities.SplashActivity();

        replaceFragment(signupFragment);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void replaceFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.auth_container,fragment);
        fragmentTransaction.commit();

    }
}

