package com.thenetwork.app.android.thenetwork.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thenetwork.app.android.thenetwork.Activities.LoginActivity;
import com.thenetwork.app.android.thenetwork.Activities.MainActivity;
import com.thenetwork.app.android.thenetwork.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment {

    private TextView gotoSignup;
    private SignupFragment signupFragment;
    private Button emailBtnSignin;
    private Button googleSignInBtn;
    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "signinfrag";
    private GoogleApiClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private ProgressBar mProgressbar;

    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        gotoSignup = view.findViewById(R.id.to_sign_up);
        signupFragment = new SignupFragment();
        emailBtnSignin = view.findViewById(R.id.email_btn_email);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mProgressbar = view.findViewById(R.id.progress_back_frag);


        emailBtnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSigninEmail = new Intent(getActivity(), LoginActivity.class);
                startActivity(toSigninEmail);
            }
        });

        gotoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.auth_container, signupFragment,"findThisFragment")
                        .commit();
            }
        });

        googleSignInBtn = view.findViewById(R.id.google_btn_signin);
        //Configure google signin
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getActivity(),"Opps An error occurred, please try again",Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressbar.setVisibility(View.VISIBLE);
                signIn();
            }
        });


        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(getActivity(),"Google SignIn failed "+e.getMessage(),Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Google SignIn  failed", e);
                mProgressbar.setVisibility(View.GONE);
                // ...
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleSignInClient.stopAutoManage(getActivity());
        mGoogleSignInClient.disconnect();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            sendToSetup();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity(),"Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                        mProgressbar.setVisibility(View.GONE);

                        // ...
                    }
                });
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void sendToSetup(){
        Intent setupIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(setupIntent);
        getActivity().finish();
    }

}
