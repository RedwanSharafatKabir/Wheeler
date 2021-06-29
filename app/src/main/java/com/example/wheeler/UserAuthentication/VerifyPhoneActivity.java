package com.example.wheeler.UserAuthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wheeler.AppActions.MainActivity;
import com.example.wheeler.AppActions.SplashScreen;
import com.example.wheeler.ModelClass.StoreUserData;
import com.example.wheeler.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santalu.maskedittext.MaskEditText;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    String email, username, phonenumber, password;
    TextView setPhone;
    MaskEditText enterOtp;
    ProgressBar progressBar;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    boolean connected = false;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private static final String TAG = "Verify Phone Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User Information");
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();

        setPhone = findViewById(R.id.setPhoneNumberID);
        enterOtp = findViewById(R.id.otpInputID);
        progressBar = findViewById(R.id.verificationProgressBarID);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        email = intent.getStringExtra("emailKey");
        username = intent.getStringExtra("usernameKey");
        phonenumber = intent.getStringExtra("phonenumberKey");
        password = intent.getStringExtra("passwordKey");

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                signinWithOtpCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCodeSent(@NotNull String verificationId, @NotNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, forceResendingToken);
                Log.i(TAG, "onCodeSent" + verificationId);
                mVerificationId = verificationId;
                forceResendingToken = token;
                progressBar.setVisibility(View.GONE);
            }
        };

        sendOtpCode();
    }

    private void sendOtpCode(){
        PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phonenumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
        setPhone.setText("Verification code is sent to " + phonenumber);
    }

    private void resendOtpCode(String phonenumber, PhoneAuthProvider.ForceResendingToken token){
        PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phonenumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .setForceResendingToken(token)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
        setPhone.setText("Verification code is sent to " + phonenumber);
    }

    public void submitOtpMethod(View v){
        String verificationOtp = enterOtp.getText().toString();
        progressBar.setVisibility(View.VISIBLE);

        if(!verificationOtp.isEmpty()){
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                connected = true;
                verifyWithCode(mVerificationId, verificationOtp);
            }
            else {
                connected = false;
                progressBar.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Turn on internet connection", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.Red));
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.setDuration(10000).show();
            }

        } else if(verificationOtp.isEmpty()){
            enterOtp.setError("Enter verification code");
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void verifyWithCode(String mVerificationId, String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signinWithOtpCredential(credential);
    }

    private void signinWithOtpCredential(PhoneAuthCredential credential){
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            connected = true;

            mAuth.signInWithCredential(credential)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressBar.setVisibility(View.GONE);

                            finish();
                            Intent it = new Intent(VerifyPhoneActivity.this, MainActivity.class);
                            startActivity(it);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

//                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                Log.d(TAG, "User account deleted.");
//                                            }
//                                        }
//                                    });
//                                }
//                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast t = Toast.makeText(VerifyPhoneActivity.this, "Verification failed", Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER, 0, 0);
                            t.show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }
        else {
            connected = false;
            progressBar.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Turn on internet connection", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.Red));
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.setDuration(10000).show();
        }
    }

    public void resendOtpButtonMethod(View v){
        progressBar.setVisibility(View.VISIBLE);

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            connected = true;

            resendOtpCode(phonenumber, forceResendingToken);
        }
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
