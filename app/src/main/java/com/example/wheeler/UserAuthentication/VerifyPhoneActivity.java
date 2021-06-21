package com.example.wheeler.UserAuthentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheeler.AppActions.MainActivity;
import com.example.wheeler.AppActions.SplashScreen;
import com.example.wheeler.ModelClass.StoreUserData;
import com.example.wheeler.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santalu.maskedittext.MaskEditText;

public class VerifyPhoneActivity extends AppCompatActivity {

    String email, username, phonenumber, password, sentCode;;
    TextView setPhone;
    MaskEditText enterOtp;
    ProgressBar progressBar;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    boolean connected = false;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User Information");
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();

        Intent intent = new Intent();
        email = intent.getStringExtra("emailKey");
        username = intent.getStringExtra("usernameKey");
        phonenumber = intent.getStringExtra("phonenumberKey");
        password = intent.getStringExtra("passwordKey");

        setPhone = findViewById(R.id.setPhoneNumberID);
        enterOtp = findViewById(R.id.otpInputID);
        progressBar = findViewById(R.id.progressBarID);
        progressBar.setVisibility(View.GONE);

        sendOtpCode();
        setPhone.setText("5 digits verification code is sent to " + phonenumber);
    }

    private void sendOtpCode(){

    }

    public void verifyOtpMethod(View v){
        String verificationOtp = enterOtp.getText().toString();

        if(verificationOtp.equals(sentCode)){
            progressBar.setVisibility(View.VISIBLE);
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                connected = true;

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        storeUserDataMethod(email, username, phonenumber);
                        Toast.makeText(VerifyPhoneActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();

                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((OnCompleteListener<AuthResult>) task1 -> {
                            if (task1.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Intent it = new Intent(VerifyPhoneActivity.this, MainActivity.class);
                                startActivity(it);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            } else {
                                Toast t = Toast.makeText(VerifyPhoneActivity.this, "Authentication failed\nError : " +
                                        task1.getException().getMessage(), Toast.LENGTH_LONG);
                                t.setGravity(Gravity.CENTER, 0, 0);
                                t.show();
                            }
                        });

                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast t = Toast.makeText(VerifyPhoneActivity.this, R.string.email_alert,
                                    Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER, 0, 0);
                            t.show();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast t = Toast.makeText(VerifyPhoneActivity.this, "Authentication failed. Error : "
                                    + "Connection lost.", Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER, 0, 0);
                            t.show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            } else {
                connected = false;
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Turn on internet connection", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.Red));
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.setDuration(10000).show();
            }
        } else {
            Toast.makeText(VerifyPhoneActivity.this, "Invalid OTP", Toast.LENGTH_LONG).show();
        }
    }

    public void resendOtpMethod(View v){
        sendOtpCode();
    }

    public void backPageMethod(View v){
        Intent it = new Intent(VerifyPhoneActivity.this, SplashScreen.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void storeUserDataMethod(String email, String username, String phone){
        String displayname = phone;
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            UserProfileChangeRequest profile;
            profile= new UserProfileChangeRequest.Builder().setDisplayName(displayname).build();
            user.updateProfile(profile).addOnCompleteListener(task -> {});
        }

        String Key_User_Info = phone;
        StoreUserData storeUserData = new StoreUserData(email, username, phone);
        databaseReference.child(Key_User_Info).setValue(storeUserData);
    }
}
