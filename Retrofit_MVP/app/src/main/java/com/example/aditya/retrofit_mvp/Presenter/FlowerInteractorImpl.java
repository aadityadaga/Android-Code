package com.example.aditya.retrofit_mvp.Presenter;

import com.example.aditya.retrofit_mvp.Model.Flower;
import com.example.aditya.retrofit_mvp.Service.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlowerInteractorImpl implements FlowerInteractor {
  List<Flower> mList = new ArrayList<>();

  public FlowerInteractorImpl() {}

  public FlowerInteractorImpl(List<Flower> mList) {
    this.mList = mList;
  }

  @Override
  public void getData(final CompleteListener listner) {
    APIClient.getClient()
        .getData()
        .enqueue(
            new Callback<List<Flower>>() {
              @Override
              public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {
                mList = response.body();
                if (!mList.isEmpty()) {
                  listner.ReadyData(mList);
                }
              }

              @Override
              public void onFailure(Call<List<Flower>> call, Throwable t) {
                try {
                  throw new InterruptedException("Error occured!");
                } catch (InterruptedException e) {
                  System.out.print(e.getMessage());
                  e.printStackTrace();
                }
              }
            });
  }
}
