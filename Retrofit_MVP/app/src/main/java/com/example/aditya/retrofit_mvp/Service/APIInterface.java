package com.example.aditya.retrofit_mvp.Service;

import com.example.aditya.retrofit_mvp.Model.Flower;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
  @GET("/feeds/flowers.json")
  Call<List<Flower>> getData();
}
