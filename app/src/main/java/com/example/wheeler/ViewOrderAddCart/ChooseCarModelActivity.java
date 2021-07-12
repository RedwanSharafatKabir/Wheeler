package com.example.wheeler.ViewOrderAddCart;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.wheeler.Api_Inteface.CarApiClient;
import com.example.wheeler.ModelClass.CarApiData;
import com.example.wheeler.R;
import com.example.wheeler.RecyclerView.RecyclerViewCustomAdapter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooseCarModelActivity extends Fragment {

    Parcelable recyclerViewState;
    View views, parentLayout;
    String brandNameContent = "", brandImageContent = "";
    public RecyclerView recyclerView;
    String TAG = "REDWAN", baseURL = "https://private-anon-d011506c2c-carsapi1.apiary-mock.com/";
    CarApiClient carApiClient;
    RecyclerViewCustomAdapter recyclerViewCustomAdapter;
    List<CarApiData> carApiDataList;
    ProgressBar progressBar;
    ConnectivityManager cm;
    NetworkInfo netInfo;
    Snackbar snackbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.activity_choose_car_models, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        carApiClient = retrofit.create(CarApiClient.class);

        progressBar = views.findViewById(R.id.homeProgressbarId);
        progressBar.setVisibility(View.VISIBLE);

        carApiDataList = new ArrayList<>();
        recyclerView = views.findViewById(R.id.recyclerViewID);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewCustomAdapter = new RecyclerViewCustomAdapter(getContext(), carApiDataList);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            }
        });

        parentLayout = views.findViewById(android.R.id.content);
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            getDataFromApi();
        } else {
            snackbar = Snackbar.make(parentLayout, "Turn on internet connection", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.Red));
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.setDuration(10000).show();
        }

        return views;
    }

    private void getDataFromApi(){
        Call<List<CarApiData>> call = carApiClient.getCarData();
        call.enqueue(new Callback<List<CarApiData>>() {
            @Override
            public void onResponse(Call<List<CarApiData>> call, Response<List<CarApiData>> response) {
                if(response.isSuccessful()){
                    List<CarApiData> dataList = response.body();
                    recyclerViewCustomAdapter.setData(dataList);
                    recyclerView.setAdapter(recyclerViewCustomAdapter);
                    recyclerViewCustomAdapter.notifyDataSetChanged();
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

                    progressBar.setVisibility(View.GONE);

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
//
//                    recyclerViewCustomAdapter.setData(carApiDataList);
//                    recyclerView.setAdapter(recyclerViewCustomAdapter);
//                    recyclerViewCustomAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CarApiData>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                try {
                    Log.i(TAG, "Response failure: " + t.getMessage());
                } catch (Exception e){
                    Toast.makeText(getActivity(), "Response failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static String countDistinctWords(String str){
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
