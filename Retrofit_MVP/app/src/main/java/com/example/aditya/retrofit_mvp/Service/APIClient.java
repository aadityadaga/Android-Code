package com.example.aditya.retrofit_mvp.Service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

  private static Retrofit retrofit = null;
  private static APIInterface sApiInterface;

  public static APIInterface getClient() {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    retrofit =
        new Retrofit.Builder()
            .baseUrl("http://services.hanselandpetal.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
    sApiInterface=  retrofit.create(APIInterface.class);
    return sApiInterface;
  }
}
