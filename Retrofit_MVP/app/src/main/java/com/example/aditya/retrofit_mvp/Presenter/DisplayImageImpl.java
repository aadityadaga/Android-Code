package com.example.aditya.retrofit_mvp.Presenter;

import android.app.Activity;
import android.widget.ImageView;

import com.example.aditya.retrofit_mvp.Model.Flower;
import com.squareup.picasso.Picasso;

public class DisplayImageImpl implements DisplayImageInterface {
  Activity mActivity;
  private String URL = "http://services.hanselandpetal.com/photos/";

  public DisplayImageImpl(Activity mActivity) {
    this.mActivity = mActivity;
  }

  @Override
  public void displayImageView(Flower mFlower, ImageView mImageView) {
    Picasso.with(mActivity.getApplicationContext()).load(URL + mFlower.getPhoto()).into(mImageView);
  }
}
