package com.example.wheeler.ViewOrderAddCart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.wheeler.AppActions.MainActivity;
import com.example.wheeler.R;

public class CartListActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView backFromCartListBtn;
    private String cartString = "";
    String carImageUrl, carId, carBrand, carModel, carHorsepower, carPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        backFromCartListBtn = findViewById(R.id.backFromCartListId);
        backFromCartListBtn.setOnClickListener(this);

        Intent intent = getIntent();
        cartString = intent.getStringExtra("cart_key");
        carImageUrl = intent.getStringExtra("carImageUrl_key");
        carId = intent.getStringExtra("carId_key");
        carBrand = intent.getStringExtra("carBrand_key");
        carModel = intent.getStringExtra("carModel_key");
        carHorsepower = intent.getStringExtra("carHorsepower_key");
        carPrice = intent.getStringExtra("carPrice_key");
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backFromCartListId){
            if(cartString.equals("MainActivity")){
                finish();
                Intent it = new Intent(CartListActivity.this, MainActivity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        } else if(cartString.equals("ParticularCarDetails")){
                finish();
                Intent intent = new Intent(CartListActivity.this, ParticularCarDetails.class);
                intent.putExtra("carImageUrl_key", carImageUrl);
                intent.putExtra("carId_key", carId);
                intent.putExtra("carBrand_key", carBrand);
                intent.putExtra("carModel_key", carModel);
                intent.putExtra("carHorsepower_key", carHorsepower);
                intent.putExtra("carPrice_key", carPrice);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(cartString.equals("MainActivity")){
            finish();
            Intent it = new Intent(CartListActivity.this, MainActivity.class);
            startActivity(it);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        } else if(cartString.equals("ParticularCarDetails")){
            finish();
            Intent intent = new Intent(CartListActivity.this, ParticularCarDetails.class);
            intent.putExtra("carImageUrl_key", carImageUrl);
            intent.putExtra("carId_key", carId);
            intent.putExtra("carBrand_key", carBrand);
            intent.putExtra("carModel_key", carModel);
            intent.putExtra("carHorsepower_key", carHorsepower);
            intent.putExtra("carPrice_key", carPrice);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}
