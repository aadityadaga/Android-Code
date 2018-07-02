package com.example.aditya.firebasechat.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {

    @GET("{PDF}")
    Call<ResponseBody>getPDF(@Path ( value = "PDF" ,encoded =true)String PDF);
}
