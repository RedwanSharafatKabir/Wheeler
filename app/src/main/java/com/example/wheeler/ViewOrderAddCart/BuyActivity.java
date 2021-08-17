package com.example.wheeler.ViewOrderAddCart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheeler.AppActions.MainActivity;
import com.example.wheeler.ModelClass.StoreCartList;
import com.example.wheeler.ModelClass.StoreOrderList;
import com.example.wheeler.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BuyActivity extends AppCompatActivity implements View.OnClickListener{

    View parentLayout;
    Button proceed;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    Snackbar snackbar;
    ProgressBar progressBar;
    ImageView imageView, backBtn;
    EditText cityText, areaText, roadText, houseText;
    String carImageUrl, carId, carBrand, carModel, carHorsepower, userPhone, totalPrice, quantity, city, area, road, house, singlePrice;
    TextView carBrandText, carModelText, carHorsepowerText, totalPriceText, count, singlePriceText;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        progressBar = findViewById(R.id.orderProgressBarId);
        parentLayout = findViewById(android.R.id.content);

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        databaseReference = FirebaseDatabase.getInstance().getReference("Order and Buy Information");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Intent intent = getIntent();
        carImageUrl = intent.getStringExtra("carImageUrl_key");
        carId = intent.getStringExtra("carId_key");
        carBrand = intent.getStringExtra("carBrand_key");
        carModel = intent.getStringExtra("carModel_key");
        carHorsepower = intent.getStringExtra("carHorsepower_key");
        singlePrice = intent.getStringExtra("carPrice_key");
        quantity = intent.getStringExtra("carQuantity_key");
        totalPrice = intent.getStringExtra("totalFinalPrice_key");

        proceed = findViewById(R.id.proceedID);
        proceed.setOnClickListener(this);
        backBtn = findViewById(R.id.backFromBuyId);
        backBtn.setOnClickListener(this);
        imageView = findViewById(R.id.imageBuyID);

        carBrandText = findViewById(R.id.carMakeBrandBuyID);
        carModelText = findViewById(R.id.carModelBuyID);
        carHorsepowerText = findViewById(R.id.horsePowerBuyID);
        count = findViewById(R.id.quantityBuyID);
        totalPriceText = findViewById(R.id.totalAmountBuyID);
        singlePriceText = findViewById(R.id.singlePriceBuyID);

        cityText = findViewById(R.id.customerCityID);
        areaText = findViewById(R.id.customerAreaID);
        roadText = findViewById(R.id.customerRoadID);
        houseText = findViewById(R.id.customerHouseID);

        Picasso.get().load(carImageUrl).into(imageView);
        carBrandText.setText("Brand: " + carBrand);
        carModelText.setText("Model: " + carModel);
        carHorsepowerText.setText("Horsepower: " + carHorsepower + " hp");
        singlePriceText.setText("Price: " + singlePrice + " $");
        count.setText("Quantity: " + quantity);
        totalPriceText.setText("Total: " + totalPrice + " $");

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            if(user!=null) {
                userPhone = user.getDisplayName();
                checkOrderItems();
            } else {
                Toast.makeText(BuyActivity.this, "Login First", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        } else {
            Toast.makeText(BuyActivity.this, "Turn on internet connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void checkOrderItems(){
        try{
            databaseReference.child(userPhone).child(carId).child("city").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        city = snapshot.getValue().toString();
                        cityText.setText(city);

                        databaseReference.child(userPhone).child(carId).child("area").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                area = snapshot.getValue().toString();
                                areaText.setText(area);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });

                        databaseReference.child(userPhone).child(carId).child("road").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                road = snapshot.getValue().toString();
                                roadText.setText(road);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });

                        databaseReference.child(userPhone).child(carId).child("house").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                house = snapshot.getValue().toString();
                                houseText.setText(house);

                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });

                    } catch (Exception e) {
                        Log.i("Database Error ", e.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });

        } catch (Exception e){
            Log.i("Database Error ", e.getMessage());
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        city = cityText.getText().toString();
        area = areaText.getText().toString();
        road = roadText.getText().toString();
        house = houseText.getText().toString();

        if(v.getId()==R.id.backFromBuyId){
            finish();
            Intent intent = new Intent(BuyActivity.this, ParticularCarDetails.class);
            intent.putExtra("carImageUrl_key", carImageUrl);
            intent.putExtra("carId_key", carId);
            intent.putExtra("carBrand_key", carBrand);
            intent.putExtra("carModel_key", carModel);
            intent.putExtra("carHorsepower_key", carHorsepower);
            intent.putExtra("carPrice_key", singlePrice);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        if(v.getId()==R.id.proceedID){
            if(city.isEmpty()){
                cityText.setError("Enter your city");
            }

            if(area.isEmpty()){
                cityText.setError("Enter your living area");
            }

            if(road.isEmpty()){
                cityText.setError("Enter road no.");
            }

            if(house.isEmpty()){
                cityText.setError("Enter house no.");
            }

            if(!city.isEmpty() || !area.isEmpty() || !road.isEmpty() || !house.isEmpty()) {
                storeOrderDetails(userPhone, city, area, road, house, carId, quantity, totalPrice,
                        carImageUrl, carBrand, carModel, carHorsepower, singlePrice);
            }
        }
    }

    private void storeOrderDetails(String userPhone, String city, String area, String road, String house, String carId,
                                   String quantity, String totalPrice, String carImageUrl, String carBrand, String carModel,
                                   String carHorsepower, String singlePrice){

        if(user!=null) {
            StoreOrderList storeOrderList = new StoreOrderList(carId, quantity, totalPrice, city, area, road, house,
                    carImageUrl, carBrand, carModel, carHorsepower, singlePrice);

            databaseReference.child(userPhone).child(carId).setValue(storeOrderList);

            Toast.makeText(BuyActivity.this, "Successfully Ordered", Toast.LENGTH_SHORT).show();
            snackbar = Snackbar.make(parentLayout, "Your order will be delivered within 60 minutes", Snackbar.LENGTH_LONG);
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.show();

        } else {
            Toast.makeText(BuyActivity.this, "Login First", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(BuyActivity.this, ParticularCarDetails.class);
        intent.putExtra("carImageUrl_key", carImageUrl);
        intent.putExtra("carId_key", carId);
        intent.putExtra("carBrand_key", carBrand);
        intent.putExtra("carModel_key", carModel);
        intent.putExtra("carHorsepower_key", carHorsepower);
        intent.putExtra("carPrice_key", singlePrice);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }
}
