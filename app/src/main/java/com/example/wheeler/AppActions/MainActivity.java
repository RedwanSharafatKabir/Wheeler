package com.example.wheeler.AppActions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
//import com.android.volley.toolbox.JsonArrayRequest;
import com.example.wheeler.Api_Inteface.CarApiClient;
import com.example.wheeler.ModelClass.CarApiData;
import com.example.wheeler.R;
import com.example.wheeler.RecyclerView.RecyclerViewCustomAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    String brandNameContent = "", brandImageContent = "";
    public RecyclerView recyclerView;
    String baseURL = "https://private-anon-10a3306dd3-carsapi1.apiary-mock.com/";
    CarApiClient carApiClient;
    RecyclerViewCustomAdapter recyclerViewCustomAdapter;
    List<CarApiData> carApiDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        carApiClient = retrofit.create(CarApiClient.class);

        carApiDataList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewID);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCustomAdapter = new RecyclerViewCustomAdapter(this, carApiDataList);

        getDataFromApi();
    }

    public void getDataFromApi(){
        Call<List<CarApiData>> call = carApiClient.getCarData();
        call.enqueue(new Callback<List<CarApiData>>() {
            @Override
            public void onResponse(Call<List<CarApiData>> call, Response<List<CarApiData>> response) {
                if(response.isSuccessful()){
                    List<CarApiData> dataList = response.body();
                    recyclerViewCustomAdapter.setData(dataList);
                    recyclerView.setAdapter(recyclerViewCustomAdapter);
                    recyclerViewCustomAdapter.notifyDataSetChanged();

                    for (CarApiData carApiData : dataList) {
                        brandNameContent += carApiData.getMake() + " ";
                        brandImageContent += carApiData.getImg_url() + "\n";
                    }

                    Log.d("Brand Logo", brandImageContent);
                    Log.d("Unique brands name", countDistinctWords(brandNameContent));

//                    for (CarApiData carApiData : dataList) {
//                        carApiData.setId(carApiData.getId());
//                        carApiData.setHorsepower(carApiData.getHorsepower());
//                        carApiData.setMake(carApiData.getMake());
//                        carApiData.setModel(carApiData.getModel());
//                        carApiData.setPrice(carApiData.getPrice());
//                        carApiData.setImage_url(carApiData.getImage_url());
//                        carApiDataList.add(carApiData);
//                    }
//                    recyclerViewCustomAdapter.setData(carApiDataList);
//                    recyclerView.setAdapter(recyclerViewCustomAdapter);
//                    recyclerViewCustomAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CarApiData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Response failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String countDistinctWords(String str){
        Set<String> object = new HashSet<String>();
        String[] words = str.split(" ");
        String uniqueElements = "";

        for(String wrds: words){
            object.add(wrds);
        }

        for(String elements: object){
            uniqueElements += elements + " ";
        }

        return uniqueElements;
    }
}