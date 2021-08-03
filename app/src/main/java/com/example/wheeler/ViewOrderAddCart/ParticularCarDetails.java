package com.example.wheeler.ViewOrderAddCart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheeler.AppActions.MainActivity;
import com.example.wheeler.ModelClass.StoreCartList;
import com.example.wheeler.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParticularCarDetails extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView, backToHome, minus, plus;
    String carImageUrl, carId, carBrand, carModel, carHorsepower, carPrice, userPhone, measuredPrice, totalPriceFromCart, quantityFromCart;
    TextView carIdText, carBrandText, carModelText, carHorsepowerText, carPriceText, totalPrice, count;
    Button buyNow, addToCart, removeFromCart;
    CardView cardView1, cardView2;
    int countCarNumber = 1;
    CircleImageView cartFloatingBtn;
    TextView countCartRedText;
    DatabaseReference orderBuyReference, cartReference;
    CardView countCartCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_car_details);

        cardView1 = findViewById(R.id.cardViewId1);
        cardView2 = findViewById(R.id.cardViewId2);
        backToHome = findViewById(R.id.backToHomeFromParticularCarId);
        backToHome.setOnClickListener(this);
        imageView = findViewById(R.id.imageUrlParticularID);

        cartFloatingBtn = findViewById(R.id.cartFloatingBtnId);
        cartFloatingBtn.setOnClickListener(this);
        countCartRedText = findViewById(R.id.countCartItemsId);
        countCartRedText.setOnClickListener(this);
        minus = findViewById(R.id.minusID);
        minus.setOnClickListener(this);
        plus = findViewById(R.id.plusID);
        plus.setOnClickListener(this);
        buyNow = findViewById(R.id.buyNowID);
        buyNow.setOnClickListener(this);
        addToCart = findViewById(R.id.addToCartID);
        addToCart.setOnClickListener(this);
        removeFromCart = findViewById(R.id.removeFromCartID);
        removeFromCart.setOnClickListener(this);

        countCartCardView = findViewById(R.id.countCartCardViewId);
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
        quantityFromCart = it.getStringExtra("carQuantity_key");
        totalPriceFromCart = it.getStringExtra("carCost_key");

        Picasso.get().load(carImageUrl).into(imageView);
        carIdText.setText("Car ID: " + carId);
        carBrandText.setText("Brand: " + carBrand);
        carModelText.setText("Model: " + carModel);
        carHorsepowerText.setText("Horsepower: " + carHorsepower + " hp");
        carPriceText.setText("Price: " + carPrice + " $");

        if(totalPriceFromCart.equals("No Data")) {
            cardView1.setVisibility(View.VISIBLE);
            totalPrice.setText("Total amount: " + carPrice + " $");
        } else {
            cardView2.setVisibility(View.VISIBLE);
            totalPrice.setText("Total amount: " + totalPriceFromCart + " $");
            count.setText(quantityFromCart);
        }

        userPhone = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        orderBuyReference = FirebaseDatabase.getInstance().getReference("Order and Buy Information");
        cartReference = FirebaseDatabase.getInstance().getReference("Cart Information");

        checkCartItems();
    }

    private void refresh(int milliSecond){
        final Handler handler = new Handler(Looper.getMainLooper());

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                checkCartItems();
            }
        };

        handler.postDelayed(runnable, milliSecond);
    }

    private void checkCartItems(){
        cartReference.child(userPhone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>0){
                    countCartCardView.setVisibility(View.VISIBLE);
                    countCartRedText.setVisibility(View.VISIBLE);
                    countCartRedText.setText(String.valueOf(snapshot.getChildrenCount()));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {}
        });

        refresh(1000);
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
            measuredPrice = String.valueOf(finalPrice);
            totalPrice.setText("Total amount: " + measuredPrice + " $");
        }

        if(v.getId()==R.id.minusID){
            if(countCarNumber>1){
                countCarNumber--;
                count.setText(Integer.toString(countCarNumber));
            }

            count.setText(Integer.toString(countCarNumber));
            Double perPiecePrice = Double.parseDouble(carPrice);
            Double finalPrice = countCarNumber * perPiecePrice;
            measuredPrice = String.valueOf(finalPrice);
            totalPrice.setText("Total amount: " + measuredPrice + " $");
        }

        if(v.getId()==R.id.addToCartID){
            storeToCartList(carId, carBrand, carModel, carHorsepower, countCarNumber, measuredPrice, carPrice, carImageUrl);
            cardView1.setVisibility(View.GONE);
            cardView2.setVisibility(View.VISIBLE);
        }

        if(v.getId()==R.id.removeFromCartID){
            removeFromCartMethod();
        }

        if(v.getId()==R.id.cartFloatingBtnId || v.getId()==R.id.countCartItemsId){
            finish();
            Intent intent = new Intent(ParticularCarDetails.this, CartListActivity.class);
            intent.putExtra("cart_key", "ParticularCarDetails");
            intent.putExtra("carImageUrl_key", carImageUrl);
            intent.putExtra("carId_key", carId);
            intent.putExtra("carBrand_key", carBrand);
            intent.putExtra("carModel_key", carModel);
            intent.putExtra("carHorsepower_key", carHorsepower);
            intent.putExtra("carPrice_key", carPrice);
            startActivity(intent);
        }

        if(v.getId()==R.id.buyNowID){

        }
    }

    private void storeToCartList(String carId, String carBrand, String carModel, String carHorsepower,
                                 int quantity, String carFinalPrice, String carSinglePrice, String carImageUrl){

        StoreCartList storeCartList = new StoreCartList(carId, carBrand, carModel, carHorsepower,
                quantity, carFinalPrice, carSinglePrice, carImageUrl);
        cartReference.child(userPhone).child(carId).setValue(storeCartList);

        Toast.makeText(ParticularCarDetails.this, "Car added to cart", Toast.LENGTH_SHORT).show();
    }

    private void removeFromCartMethod(){
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to remove from cart ?");
        alertDialogBuilder.setIcon(R.drawable.exit);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    cartReference.child(userPhone).child(carId).removeValue();
                    Toast.makeText(ParticularCarDetails.this, "Car removed from cart", Toast.LENGTH_SHORT).show();
					count.setText("1");
					totalPrice.setText("Total amount: " + carPrice + " $");
                    cardView2.setVisibility(View.GONE);
                    cardView1.setVisibility(View.VISIBLE);
                } catch (Exception e){
                    Log.i("Removed ", e.getMessage());
                }
            }
        });

        alertDialogBuilder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
