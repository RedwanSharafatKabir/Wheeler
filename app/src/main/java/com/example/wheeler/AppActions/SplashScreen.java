package com.example.wheeler.AppActions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wheeler.R;
import com.example.wheeler.UserAuthentication.SigninActivity;
import com.example.wheeler.UserAuthentication.SignupActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class SplashScreen extends AppCompatActivity {

    int SPLASH_TIME_OUT = 3000;
    ImageView imageView;
    Button signInButton;
    FirebaseUser firebaseUser = null;
    String passedString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        signInButton = findViewById(R.id.signinPageID);
        imageView = findViewById(R.id.splashImageID);
        imageView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_fade_in));

        new Handler(Looper.getMainLooper()).postDelayed(() -> signInButton.isClickable(), SPLASH_TIME_OUT);
    }

    @Override
    protected void onStart() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rememberMeMethod();

        if (firebaseUser != null && !passedString.isEmpty()) {
            finish();
            Intent it = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(it);
        }
        super.onStart();
    }

    public void signInBtnMethod(View v){
        SigninActivity signinActivity = new SigninActivity();
        signinActivity.show(getSupportFragmentManager(), "Sample dialog");
    }

    public void joinNowBtnMethod(View v){
        SignupActivity signupActivity = new SignupActivity();
        signupActivity.show(getSupportFragmentManager(), "Sample dialog");
    }

    public void rememberMeMethod(){
        try {
            FileInputStream fileInputStream = openFileInput("Personal_Info.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String recievedMessage;
            StringBuffer stringBuffer = new StringBuffer();

            while((recievedMessage=bufferedReader.readLine())!=null){
                stringBuffer.append(recievedMessage);
            }

            passedString = stringBuffer.toString();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
