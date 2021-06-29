package com.example.wheeler.ViewOrderAddCart;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheeler.AppActions.MainActivity;
import com.example.wheeler.R;
import com.squareup.picasso.Picasso;

public class ParticularCarDetails extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView, backToHome, minus, plus;
    String carImageUrl, carId, carBrand, carModel, carHorsepower, carPrice;
    TextView carIdText, carBrandText, carModelText, carHorsepowerText, carPriceText, totalPrice, count;
    Button buyNow, addToCart;
    int countCarNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_car_details);

        backToHome = findViewById(R.id.backToHomeFromParticularCarId);
        backToHome.setOnClickListener(this);
        imageView = findViewById(R.id.imageUrlParticularID);

        minus = findViewById(R.id.minusID);
        minus.setOnClickListener(this);
        plus = findViewById(R.id.plusID);
        plus.setOnClickListener(this);
        buyNow = findViewById(R.id.buyNowID);
        buyNow.setOnClickListener(this);
        addToCart = findViewById(R.id.addToCartID);
        addToCart.setOnClickListener(this);

        count = findViewById(R.id.carCountID);
        totalPrice = findViewById(R.id.totalAmountParticularID);
        carIdText = findViewById(R.id.carParticularID);
        carBrandText = findViewById(R.id.carMakeBrandParticularID);
        carModelText = findViewById(R.id.carModelParticularID);
        carHorsepowerText = findViewById(R.id.horsePowerParticularID);
        carPriceText = findViewById(R.id.priceParticularID);

        Intent it = getIntent();
        carImageUrl = it.getStringExtra("carImageUrl_key");
        carId = it.getStringExtra("carId_key");
        carBrand = it.getStringExtra("carBrand_key");
        carModel = it.getStringExtra("carModel_key");
        carHorsepower = it.getStringExtra("carHorsepower_key");
        carPrice = it.getStringExtra("carPrice_key");

        Picasso.get().load(carImageUrl).into(imageView);
        carIdText.setText("Car ID: " + carId);
        carBrandText.setText("Brand: " + carBrand);
        carModelText.setText("Model: " + carModel);
        carHorsepowerText.setText("Horsepower: " + carHorsepower + " hp");
        carPriceText.setText("Price: " + carPrice + " $");

        totalPrice.setText("Total amount: " + carPrice + " $");
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backToHomeFromParticularCarId){
            finish();
            Intent it = new Intent(ParticularCarDetails.this, MainActivity.class);
            startActivity(it);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        if(v.getId()==R.id.plusID){
            countCarNumber++;
            count.setText(Integer.toString(countCarNumber));
            Double perPiecePrice = Double.parseDouble(carPrice);
            Double finalPrice = countCarNumber * perPiecePrice;
            totalPrice.setText("Total amount: " + finalPrice + " $");
        }

        if(v.getId()==R.id.minusID){
            if(countCarNumber>1){
                countCarNumber--;
                count.setText(Integer.toString(countCarNumber));
            }

            count.setText(Integer.toString(countCarNumber));
            Double perPiecePrice = Double.parseDouble(carPrice);
            Double finalPrice = countCarNumber * perPiecePrice;
            totalPrice.setText("Total amount: " + finalPrice + " $");
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent it = new Intent(ParticularCarDetails.this, MainActivity.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }
}
