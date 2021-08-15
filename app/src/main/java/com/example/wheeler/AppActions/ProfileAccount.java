package com.example.wheeler.AppActions;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.wheeler.R;
import com.example.wheeler.UserAuthentication.ResetPassword;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAccount extends Fragment implements View.OnClickListener{

    LinearLayout editPass;
    View views, parentLayout;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    Snackbar snackbar;
    DatabaseReference databaseReference;
    CircleImageView circleImageView;
    ImageView editProfilePic;
    TextView nameText, emailText, phoneText, loginFromProfile;
    String userPhone, userEmail;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        circleImageView = views.findViewById(R.id.profileActivityPicID);
        circleImageView.setOnClickListener(this);
        editProfilePic = views.findViewById(R.id.uploadProfilePictureID);
        editProfilePic.setOnClickListener(this);
        editPass = views.findViewById(R.id.changePasswordID);
        editPass.setOnClickListener(this);
        loginFromProfile = views.findViewById(R.id.loginFromProfileID);
        loginFromProfile.setOnClickListener(this);

        nameText = views.findViewById(R.id.profileActivityNameID);
        emailText = views.findViewById(R.id.profileActivityEmailID);
        phoneText = views.findViewById(R.id.profileActivityPhoneID);

        progressBar = views.findViewById(R.id.profileProgressbarId);
        progressBar.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference("User Information");

        parentLayout = views.findViewById(android.R.id.content);
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            if(user!=null){
                loginFromProfile.setVisibility(View.GONE);
                getUserData();
            } else {
                loginFromProfile.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

        } else {
            snackbar = Snackbar.make(parentLayout, "Turn on internet connection", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.Red));
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.setDuration(10000).show();
        }

        return views;
    }

    private void getUserData(){
        userPhone = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        databaseReference.child(userPhone).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameText.setText(" " + dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child(userPhone).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userEmail = dataSnapshot.getValue(String.class);
                emailText.setText(" " + userEmail);
                phoneText.setText(" " + userPhone);
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.changePasswordID){
            Bundle armgs = new Bundle();
            armgs.putString("email_key", userEmail);

            ResetPassword resetPassword = new ResetPassword();
            resetPassword.setArguments(armgs);
            resetPassword.show(getActivity().getSupportFragmentManager(), "Sample dialog");
        }

        if(v.getId()==R.id.loginFromProfileID){
            getActivity().finish();
            Intent intent = new Intent(getActivity(), SplashScreen.class);
            startActivity(intent);
        }
    }
}
