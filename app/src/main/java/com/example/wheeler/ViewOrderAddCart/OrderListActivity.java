package com.example.wheeler.ViewOrderAddCart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wheeler.AppActions.MainActivity;
import com.example.wheeler.ModelClass.StoreCartList;
import com.example.wheeler.ModelClass.StoreOrderList;
import com.example.wheeler.R;
import com.example.wheeler.RecyclerView.CartListCustomAdapter;
import com.example.wheeler.RecyclerView.OrderListCustomAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView backFromOrderListBtn;
    RecyclerView recyclerView;
    Parcelable recyclerViewState;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    ProgressBar progressBar;
    ArrayList<StoreOrderList> storeOrderListArrayList;
    OrderListCustomAdapter orderListCustomAdapter;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();

        progressBar = findViewById(R.id.orderProgressBarId);
        backFromOrderListBtn = findViewById(R.id.backFromOrderListId);
        backFromOrderListBtn.setOnClickListener(this);

        recyclerView = findViewById(R.id.orderRecyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            }
        });

        storeOrderListArrayList = new ArrayList<StoreOrderList>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Order and Buy Information");

        if(user!=null) {
            loadOrderList();

        } else {
            Toast.makeText(OrderListActivity.this, "Login First", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void loadOrderList(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storeOrderListArrayList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot : item.getChildren()) {
                        StoreOrderList storeOrderList = snapshot.getValue(StoreOrderList.class);
                        storeOrderListArrayList.add(storeOrderList);
                    }
                }

                orderListCustomAdapter = new OrderListCustomAdapter(OrderListActivity.this, storeOrderListArrayList);
                recyclerView.setAdapter(orderListCustomAdapter);
                orderListCustomAdapter.notifyDataSetChanged();
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
        if(v.getId()==R.id.backFromOrderListId){
            finish();
            Intent it = new Intent(OrderListActivity.this, MainActivity.class);
            startActivity(it);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent it = new Intent(OrderListActivity.this, MainActivity.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
