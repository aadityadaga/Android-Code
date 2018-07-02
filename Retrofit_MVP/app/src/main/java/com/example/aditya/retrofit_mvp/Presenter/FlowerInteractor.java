package com.example.aditya.retrofit_mvp.Presenter;

import com.example.aditya.retrofit_mvp.Model.Flower;

import java.util.List;

public interface FlowerInteractor {
    void getData(CompleteListener listner);

    interface CompleteListener {
      void  ReadyData(List<Flower>mList);
     }
}
