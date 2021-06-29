package com.example.wheeler.UserAuthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wheeler.AppActions.MainActivity;
import com.example.wheeler.ModelClass.StoreUserData;
import com.example.wheeler.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import dmax.dialog.SpotsDialog;

public class SignupActivity extends AppCompatDialogFragment implements View.OnClickListener {

    MaskEditText signupPhoneText;
    Animation fromTop, fromBottom;
    EditText signupEmailText, signupUsernameText, signupPasswordText;
    ImageButton signupButton;
    LinearLayout linearLayout;
    Button close;
    AlertDialog waitingDialog;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    boolean connected = false;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        fromTop = AnimationUtils.loadAnimation(getActivity(), R.anim.fromtoptobottom);
        fromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.frombottomtotop);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_signup, null);

        builder.setView(view).setTitle("SIGN UP");
        builder.setMessage("Create new account with gmail.\nYou can use a contact number only once." +
                "\nOne contact number for multiple accounts " +
                "will remove your previous account data.");
        setCancelable(false);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User Information");

        linearLayout = view.findViewById(R.id.first);
        close = view.findViewById(R.id.closeDialogID);
        close.setOnClickListener(this);

        signupEmailText = view.findViewById(R.id.signupEmailID);
        signupUsernameText = view.findViewById(R.id.signupUsernameID);
        signupPhoneText = view.findViewById(R.id.signupPhoneID);
        signupPasswordText = view.findViewById(R.id.signupPasswordID);
        signupButton = view.findViewById(R.id.SignupID);
        signupButton.setOnClickListener(this);

        linearLayout.setAnimation(fromTop);
        signupButton.setAnimation(fromBottom);

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        final String email = signupEmailText.getText().toString();
        final String username = signupUsernameText.getText().toString();
        final String phone = signupPhoneText.getText().toString();
        final String password = signupPasswordText.getText().toString();
        final String phonenumber = "+88" + phone;
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();

        if(v.getId()==R.id.SignupID) {
            waitingDialog = new SpotsDialog.Builder().setContext(getContext()).build();
            waitingDialog.show();

            if (email.isEmpty()) {
                waitingDialog.dismiss();
                signupEmailText.setError("Please enter email address");
                return;
            }

            if (username.isEmpty()) {
                waitingDialog.dismiss();
                signupUsernameText.setError("Please enter username");
                return;
            }

            if (phone.isEmpty()) {
                waitingDialog.dismiss();
                signupPhoneText.setError("Please enter your contact number");
                return;
            }

            if (password.isEmpty()) {
                waitingDialog.dismiss();
                signupPasswordText.setError("Please enter password");
                return;
            }

            if (password.length() < 8) {
                waitingDialog.dismiss();
                signupPasswordText.setError("Password must be at least 8 characters");
                return;
            }

            if(phone.length() < 11) {
                waitingDialog.dismiss();
                signupPhoneText.setError("Invalid phone number");
            }

            else {
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    connected = true;
                    waitingDialog.dismiss();

                    signupWithEmail(email, username, phonenumber, password);
                    Intent it = new Intent(getActivity(), MainActivity.class);
                    startActivity(it);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

//                    Intent it = new Intent(getActivity(), VerifyPhoneActivity.class);
//                    it.putExtra("emailKey", email);
//                    it.putExtra("usernameKey", username);
//                    it.putExtra("phonenumberKey", phonenumber);
//                    it.putExtra("passwordKey", password);
//                    startActivity(it);
//                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    signupEmailText.setText("");
                    signupUsernameText.setText("");
                    signupPhoneText.setText("");
                    signupPasswordText.setText("");
                } else {
                    connected = false;
                    Snackbar snackbar = Snackbar.make(getView(), "Turn on internet connection", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.Red));
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.setDuration(10000).show();
                }
            }
        }

        if(v.getId()==R.id.closeDialogID){
            getDialog().dismiss();
        }
    }

    private void signupWithEmail(String email, String username, String phonenumber, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    storeUserDataMethod(email, username, phonenumber);
                    Toast.makeText(getActivity(), "Successfully registered", Toast.LENGTH_LONG).show();
                    waitingDialog.dismiss();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent it = new Intent(getActivity(), MainActivity.class);
                                startActivity(it);
                                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            } else {
                                waitingDialog.dismiss();
                                Toast t = Toast.makeText(getActivity(), "Authentication failed\nError : " +
                                        task.getException().getMessage(), Toast.LENGTH_LONG);
                                t.setGravity(Gravity.CENTER, 0, 0);
                                t.show();
                            }
                        }
                    });

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        waitingDialog.dismiss();
                        Toast t = Toast.makeText(getActivity(), R.string.email_alert,
                                Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0, 0);
                        t.show();
                    } else {
                        waitingDialog.dismiss();
                        Toast t = Toast.makeText(getActivity(), "Authentication failed. Error : "
                                + "Connection lost.", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0, 0);
                        t.show();
                    }
                }
            }
        });
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
