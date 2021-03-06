package com.example.wheeler.AppActions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wheeler.R;

public class SplashScreen extends AppCompatActivity {

    int SPLASH_TIME_OUT = 3000;
    ImageView imageView;
    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        signInButton = findViewById(R.id.signinPageID);
        imageView = findViewById(R.id.splashImageID);
        imageView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_fade_in));

        new Handler().postDelayed(() -> signInButton.isClickable(), SPLASH_TIME_OUT);
    }

    public void signInBtnMethod(View v){
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
    }
}
