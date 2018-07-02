package com.example.aditya.retrofit_mvp.Router;

import android.content.Context;
import android.content.Intent;

import com.example.aditya.retrofit_mvp.DisplayImage;
import com.example.aditya.retrofit_mvp.Model.Flower;

import java.util.ArrayList;
import java.util.List;

public class ViewFullImpl implements ViewFull {
  Context mContext;
  List<Flower> mList = new ArrayList<>();

  public ViewFullImpl(Context mContext, List<Flower> mList) {
    this.mContext = mContext;
    this.mList = mList;
  }

  @Override
  public void NavigateActivity(Context mContext, List<Flower> mList, int mPosition) {
    Flower mFlower = mList.get(mPosition);
    Intent mIntent = new Intent(mContext, DisplayImage.class);
    mIntent.putExtra("Data", mFlower);
    mContext.startActivity(mIntent);
  }
}
