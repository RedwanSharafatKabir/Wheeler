package com.example.wheeler.Api_Inteface;

import com.example.wheeler.ModelClass.CarApiData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CarApiClient {

    @GET("cars")
    Call<List<CarApiData>> getCarData();
}
