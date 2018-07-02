package com.example.aditya.retrofit_mvp.View;

import com.example.aditya.retrofit_mvp.Model.Flower;

import java.util.List;

public interface FlowerView {

  void setItem(List<Flower> mList);

  interface DisplayImage {
    void displayImage();
  }
}
