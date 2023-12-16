package com.example.newspotifyclone.view;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newspotifyclone.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LogInSignUpActivity extends AppCompatActivity {

    GoogleSignInClient goClient;
    GoogleSignInOptions goOptions;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    TextView logInTextView;
    View logInItemViewPhone;
    View logInItemViewGoogle;
    View logInItemViewFacebook;
    private FirebaseAuth mAuth;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Confirmation")
                .setMessage("Are you sure want to leave?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LogInSignUpActivity.super.onBackPressed();
                            }
                        })
                .setNegativeButton("No",null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_sign_up);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);


        logInTextView = findViewById(R.id.logInTextView);

        logInItemViewPhone = findViewById(R.id.logInWithPhone);
        ImageView logInPhoneImgView = logInItemViewPhone.findViewById(R.id.sourceImage);
        logInPhoneImgView.setImageResource((R.drawable.phone));
        TextView logInPhoneTxtView = logInItemViewPhone.findViewById(R.id.sourceName);
        logInPhoneTxtView.setText("Continue with phone number");

        logInItemViewGoogle = findViewById(R.id.logInWithGoogle);
        ImageView logInGoogleImgView = logInItemViewGoogle.findViewById(R.id.sourceImage);
        logInGoogleImgView.setImageResource(R.drawable.flat_color_icons_google);
        TextView logInGoogleTxtView = logInItemViewGoogle.findViewById(R.id.sourceName);
        logInGoogleTxtView.setText("Continue with Google");

        logInItemViewFacebook = findViewById(R.id.logInWithFacebook);
        ImageView logInFacebookImgView = logInItemViewFacebook.findViewById(R.id.sourceImage);
        logInFacebookImgView.setImageResource(R.drawable.facebook);
        TextView logInFacebookTxtView = logInItemViewFacebook.findViewById(R.id.sourceName);
        logInFacebookTxtView.setText("Continue with Facebook");

        goOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)//it is actually like
                .requestEmail()  //like this  .requestIdToken(getString("R.string.default_web_client_id"))
                .build();
        goClient=GoogleSignIn.getClient(this,goOptions);
        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(gAccount!=null){
            finish();
            Intent i = new Intent(LogInSignUpActivity.this, MainActivity.class);
            startActivity(i);
        }
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK){
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        task.getResult(ApiException.class);
                        finish();
                        Intent i = new Intent(LogInSignUpActivity.this, MainActivity.class);
                        startActivity(i);
                    } catch (ApiException e) {
                        Toast.makeText(LogInSignUpActivity.this,"something went wrong",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken((R.string.default_web_client_id+""))//it is actually like
                .requestEmail()  //like this  .requestIdToken(getString("R.string.default_web_client_id"))
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        logInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInSignUpActivity.this, MainActivity.class));
            }
        });

        logInItemViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInItemViewPhone.setBackground(getDrawable(R.drawable.item_selected));
                logInItemViewGoogle.setBackground(getDrawable(R.drawable.rectangle_box_item));
                logInItemViewFacebook.setBackground(getDrawable(R.drawable.rectangle_box_item));
            }
        });

        logInItemViewGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInItemViewGoogle.setBackground(getDrawable(R.drawable.item_selected));
                logInItemViewFacebook.setBackground(getDrawable(R.drawable.rectangle_box_item));
                logInItemViewPhone.setBackground(getDrawable(R.drawable.rectangle_box_item));
//                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//                startActivityForResult(signInIntent, 123);
                Intent signInIntent = goClient.getSignInIntent();
                activityResultLauncher.launch(signInIntent);

//                Handler handler = new Handler();
//                handler.postDelayed(()->{
////                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
////                    startActivityForResult(signInIntent, 123);
//                },1000);
            }
        });
        logInItemViewFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInItemViewFacebook.setBackground(getDrawable(R.drawable.item_selected));
                logInItemViewPhone.setBackground(getDrawable(R.drawable.rectangle_box_item));
                logInItemViewGoogle.setBackground(getDrawable(R.drawable.rectangle_box_item));
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        Log.d(TAG, "Got ID token.");
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                    // ...
                }
                break;
        }

        SignInCredential googleCredential = null;
        try {
            googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        String idToken = googleCredential.getGoogleIdToken();
        if (idToken !=  null) {
            // Got an ID token from Google. Use it to authenticate
            // with Firebase.
            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
            mAuth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
//                                Log.d(TAG, "signInWithCredential:success");
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                updateUI(user);
                                Intent i= new Intent(LogInSignUpActivity.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
//                                updateUI(null);
                            }
                        }
                    });
        }
    }
    public void forSignOut(){
        FirebaseAuth.getInstance().signOut();
    }
    public void updateUI(FirebaseUser currentUser){

    }
}