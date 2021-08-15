package com.example.wheeler.ViewOrderAddCart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wheeler.AppActions.MainActivity;
import com.example.wheeler.ModelClass.StoreCartList;
import com.example.wheeler.ModelClass.StoreOrderList;
import com.example.wheeler.R;
import com.example.wheeler.RecyclerView.CartListCustomAdapter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView backFromCartListBtn;
    private String cartString = "";
    String carImageUrl, carId, carBrand, carModel, carHorsepower, carPrice;
    RecyclerView recyclerView;
    Parcelable recyclerViewState;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    ProgressBar progressBar;
    ArrayList<StoreCartList> storeCartListArrayList;
    CartListCustomAdapter cartListCustomAdapter;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();

        Intent intent = getIntent();
        cartString = intent.getStringExtra("cart_key");
        carImageUrl = intent.getStringExtra("carImageUrl_key");
        carId = intent.getStringExtra("carId_key");
        carBrand = intent.getStringExtra("carBrand_key");
        carModel = intent.getStringExtra("carModel_key");
        carHorsepower = intent.getStringExtra("carHorsepower_key");
        carPrice = intent.getStringExtra("carPrice_key");

        progressBar = findViewById(R.id.cartProgressBarId);
        backFromCartListBtn = findViewById(R.id.backFromCartListId);
        backFromCartListBtn.setOnClickListener(this);

        recyclerView = findViewById(R.id.cartRecyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            }
        });

        storeCartListArrayList = new ArrayList<StoreCartList>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart Information");

        if(user!=null) {
            loadCartList();

        } else {
            Toast.makeText(CartListActivity.this, "Login First", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void loadCartList(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storeCartListArrayList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot : item.getChildren()) {
                        StoreCartList storeCartList = snapshot.getValue(StoreCartList.class);
                        storeCartListArrayList.add(storeCartList);
                    }
                }

                cartListCustomAdapter = new CartListCustomAdapter(CartListActivity.this, storeCartListArrayList);
                recyclerView.setAdapter(cartListCustomAdapter);
                cartListCustomAdapter.notifyDataSetChanged();
                recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
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
