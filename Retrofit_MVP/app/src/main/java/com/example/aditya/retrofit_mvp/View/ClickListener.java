package com.example.aditya.retrofit_mvp.View;

import android.content.Context;

import com.example.aditya.retrofit_mvp.Model.Flower;

import java.util.List;

public interface ClickListener {
    void onClick(Context mContext, List<Flower> mList, int mPosition);

}
