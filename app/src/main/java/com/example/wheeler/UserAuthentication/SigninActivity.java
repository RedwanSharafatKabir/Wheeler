package com.example.wheeler.UserAuthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wheeler.AppActions.MainActivity;
import com.example.wheeler.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import dmax.dialog.SpotsDialog;

public class SigninActivity extends AppCompatDialogFragment implements View.OnClickListener {

    View view;
    FirebaseAuth mAuth;
    AlertDialog waitingDialog;
    EditText signinPhoneText, signinpasswordText;
    CheckBox checkBox;
    Animation fromTop, fromBottom;
    ImageButton signinButton;
    LinearLayout linearLayoutID;
    Button close, forgetPass;
    String emailObj, passObj, passedString = "Remember me", phoneObj, phonenumber;
    boolean connection = false;
    DatabaseReference databaseReference;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        fromTop = AnimationUtils.loadAnimation(getActivity(), R.anim.fromtoptobottom);
        fromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.frombottomtotop);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_signin, null);

        builder.setView(view).setTitle("LOGIN");
        setCancelable(false);

        linearLayoutID = view.findViewById(R.id.second);
        signinPhoneText = view.findViewById(R.id.loginPhoneID);
        signinpasswordText = view.findViewById(R.id.loginpassID);

        forgetPass = view.findViewById(R.id.forgetPassID);
        forgetPass.setOnClickListener(this);
        close = view.findViewById(R.id.closeDialogID1);
        close.setOnClickListener(this);
        signinButton = view.findViewById(R.id.SigninID);
        signinButton.setOnClickListener(this);

        linearLayoutID.setAnimation(fromTop);
        signinButton.setAnimation(fromBottom);
        checkBox = view.findViewById(R.id.rememberCheckBoxID);
        checkBox.setChecked(true);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User Information");

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        phoneObj = signinPhoneText.getText().toString();
        passObj = signinpasswordText.getText().toString();
        waitingDialog = new SpotsDialog.Builder().setContext(getContext()).build();

        if(v.getId()==R.id.SigninID){
            waitingDialog.show();

            if (phoneObj.isEmpty()) {
                signinPhoneText.setError("Please enter phone number");
                waitingDialog.dismiss();
                return;
            }

            if (passObj.isEmpty()) {
                signinpasswordText.setError("Please enter password");
                waitingDialog.dismiss();
                return;
            }

            if(checkBox.isChecked()){
                ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    connection = true;
                } else {
                    connection = false;
                    Toast.makeText(getActivity(), "wifi or mobile data connection lost", Toast.LENGTH_SHORT).show();
                    waitingDialog.dismiss();
                }

                if(connection==true){
                    rememberMethod(passedString);
                    phonenumber = "+88" + phoneObj;
                    databaseReference.child(phonenumber).child("email").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                emailObj = snapshot.getValue(String.class);
                                mAuth.signInWithEmailAndPassword(emailObj, passObj).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            waitingDialog.dismiss();
                                            getActivity().finish();
                                            Intent it = new Intent(getActivity(), MainActivity.class);
                                            startActivity(it);
                                            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                                            signinPhoneText.setText("");
                                            signinpasswordText.setText("");
                                        } else {
                                            waitingDialog.dismiss();
                                            Toast t = Toast.makeText(getActivity(), "Authentication failed\nError : " +
                                                    task.getException().getMessage(), Toast.LENGTH_LONG);
                                            t.setGravity(Gravity.CENTER, 0, 0);
                                            t.show();
                                        }
                                    }
                                });
                            } catch (Exception e){
                                signinPhoneText.setError("Wrong phone number !");
                                waitingDialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast t = Toast.makeText(getActivity(), "Authentication failed", Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER, 0, 0);
                            waitingDialog.dismiss();
                        }
                    });
                }
            }

            if(!checkBox.isChecked()) {
                ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    connection = true;
                } else {
                    connection = false;
                    Toast.makeText(getActivity(), "Internet connection lost", Toast.LENGTH_SHORT).show();
                    waitingDialog.dismiss();
                }

                if(connection==true){
                    passedString = "";
                    setNullDataMethod(passedString);
                    phonenumber = "+88" + phoneObj;
                    databaseReference.child(phonenumber).child("email").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                emailObj = snapshot.getValue(String.class);
                                mAuth.signInWithEmailAndPassword(emailObj, passObj).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            waitingDialog.dismiss();
                                            getActivity().finish();
                                            Intent it = new Intent(getActivity(), MainActivity.class);
                                            startActivity(it);
                                            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                                            signinPhoneText.setText("");
                                            signinpasswordText.setText("");
                                        } else {
                                            waitingDialog.dismiss();
                                            Toast t = Toast.makeText(getActivity(), "Authentication failed\nError : " +
                                                    task.getException().getMessage(), Toast.LENGTH_LONG);
                                            t.setGravity(Gravity.CENTER, 0, 0);
                                            t.show();
                                        }
                                    }
                                });
                            } catch (Exception e){
                                signinPhoneText.setError("Wrong phone number !");
                                waitingDialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast t = Toast.makeText(getActivity(), "Authentication failed", Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER, 0, 0);
                            waitingDialog.dismiss();
                        }
                    });
                }
            }
        }

        if(v.getId()==R.id.closeDialogID1){
            getDialog().dismiss();
            waitingDialog.dismiss();
        }

        if(v.getId()==R.id.forgetPassID){
//            Forgot_Password forgot_password = new Forgot_Password();
//            forgot_password.show(getFragmentManager(), "Sample dialog");
//            getDialog().dismiss();
        }
    }

    private void rememberMethod(String passedString){
        try {
            FileOutputStream fileOutputStream = getContext().openFileOutput("Personal_Info.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(passedString.getBytes());
            fileOutputStream.close();
            Snackbar.make(view, "Data saved successfully", Snackbar.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setNullDataMethod(String passedString){
        try {
            FileOutputStream fileOutputStream = getContext().openFileOutput("Personal_Info.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(passedString.getBytes());
            fileOutputStream.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
