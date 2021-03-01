package com.example.wheeler.AppActions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wheeler.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void nextActivity(View view){
        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(it);
    }
}
