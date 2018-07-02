package com.example.aditya.retrofit_mvp.View;

import android.content.Context;

import com.example.aditya.retrofit_mvp.Model.Flower;
import com.example.aditya.retrofit_mvp.Router.ViewFull;
import com.example.aditya.retrofit_mvp.Router.ViewFullImpl;

import java.util.ArrayList;
import java.util.List;

public class ClickListerImpl implements ClickListener {
  Context mContext;
  ViewFull mViewFull;
  List<Flower> mList = new ArrayList<>();
  int mPosition;

  public ClickListerImpl(Context mContext, List<Flower> mList) {
    this.mContext = mContext;
    this.mViewFull = new ViewFullImpl(mContext, mList);
    this.mList = mList;
  }

  @Override
  public void onClick(Context mContext, List<Flower> mList, int mPosition) {
    this.mPosition = mPosition;
    mViewFull.NavigateActivity(mContext, mList, this.mPosition);
  }
}
