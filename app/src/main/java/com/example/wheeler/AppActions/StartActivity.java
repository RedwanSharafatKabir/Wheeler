package com.example.wheeler.AppActions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.wheeler.R;
import com.example.wheeler.RecyclerView.HorizontalRecyclerViewAdapter;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private ArrayList<String> brandsName = new ArrayList<>();
    private int[] brandImages = {R.drawable.porsche, R.drawable.kia, R.drawable.hyundai, R.drawable.buick, R.drawable.acura,
            R.drawable.lamborghini, R.drawable.infiniti, R.drawable.ford, R.drawable.bmw, R.drawable.mazda, R.drawable.audi,
            R.drawable.maserati, R.drawable.jeep, R.drawable.fiat, R.drawable.mitsubishi, R.drawable.lexus, R.drawable.volkswagen,
            R.drawable.ram, R.drawable.aston_martin, R.drawable.dodge, R.drawable.mini, R.drawable.mclaren, R.drawable.mercedes_benz,
            R.drawable.ferrari, R.drawable.honda, R.drawable.cadillac, R.drawable.chrysler, R.drawable.chevrolet, R.drawable.lincoln,
            R.drawable.bentley, R.drawable.alfa_romeo, R.drawable.subaru, R.drawable.rolls_royce, R.drawable.toyota, R.drawable.land_rover,
            R.drawable.nissan, R.drawable.scion, R.drawable.gmc, R.drawable.volvo, R.drawable.jaguar};
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        recyclerView = findViewById(R.id.horizontalRecyclerViewID);
        initImageBitmap();
    }

    private void initImageBitmap(){
        brandsName.add("Porsche");
        brandsName.add("Kia");
        brandsName.add("Hyundai");
        brandsName.add("Buick");
        brandsName.add("Acura");
        brandsName.add("Lamborghini");
        brandsName.add("Infiniti");
        brandsName.add("Ford");
        brandsName.add("BMW");
        brandsName.add("Mazda");
        brandsName.add("Audi");
        brandsName.add("Maserati");
        brandsName.add("Jeep");
        brandsName.add("Fiat");
        brandsName.add("Mitsubishi");
        brandsName.add("Lexus");
        brandsName.add("Volkswagen");
        brandsName.add("Ram");
        brandsName.add("Aston-Martin");
        brandsName.add("Dodge");
        brandsName.add("Mini");
        brandsName.add("Bclaren");
        brandsName.add("Mercedes-Benz");
        brandsName.add("Ferrari");
        brandsName.add("Honda");
        brandsName.add("Cadillac");
        brandsName.add("Chrysler");
        brandsName.add("Chevrolet");
        brandsName.add("Lincoln");
        brandsName.add("Bentley");
        brandsName.add("Alfa-Romeo");
        brandsName.add("Subaru");
        brandsName.add("Rolls-Royce");
        brandsName.add("Toyota");
        brandsName.add("Land-Rover");
        brandsName.add("Nissan");
        brandsName.add("Scion");
        brandsName.add("GMC");
        brandsName.add("Volvo");
        brandsName.add("Jaguar");

        initRecyclerView();
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        HorizontalRecyclerViewAdapter adapter = new HorizontalRecyclerViewAdapter(this, brandsName, brandImages);
        recyclerView.setAdapter(adapter);
    }
}
