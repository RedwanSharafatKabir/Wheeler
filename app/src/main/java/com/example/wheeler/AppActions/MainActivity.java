package com.example.wheeler.AppActions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
//import com.android.volley.toolbox.JsonArrayRequest;
import com.example.wheeler.Api_Inteface.CarApiClient;
import com.example.wheeler.ModelClass.CarApiData;
import com.example.wheeler.R;
import com.example.wheeler.RecyclerView.RecyclerViewCustomAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationID);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.homeID:
                ChooseFavoriteBrandsActivity fragment = new ChooseFavoriteBrandsActivity();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentID, fragment);
                transaction.commit();
                break;

            case R.id.notificationID:
                Toast.makeText(this, "Notification Fragment", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ordersID:
                Toast.makeText(this, "Order Record Fragment", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cartID:
                Toast.makeText(this, "Cart List Fragment", Toast.LENGTH_SHORT).show();
                break;

            case R.id.accountID:
                Toast.makeText(this, "Account Profile Fragment", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }
}
