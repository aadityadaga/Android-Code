package com.example.aditya.retrofit_mvp.Router;

import android.content.Context;

import com.example.aditya.retrofit_mvp.Model.Flower;

import java.util.List;

public interface ViewFull {

    void NavigateActivity(Context mContext, List<Flower> mList, int mPosition);
}
