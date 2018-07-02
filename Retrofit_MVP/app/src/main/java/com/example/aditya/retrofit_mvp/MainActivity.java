package com.example.aditya.retrofit_mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aditya.retrofit_mvp.Model.Flower;
import com.example.aditya.retrofit_mvp.Presenter.FlowerPresenter;
import com.example.aditya.retrofit_mvp.Presenter.FlowerPresenterImpl;
import com.example.aditya.retrofit_mvp.View.FlowerAdapter;
import com.example.aditya.retrofit_mvp.View.FlowerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FlowerView {
  @BindView(R.id.RecyclerView)
  RecyclerView mRecyclerView;

  private FlowerAdapter mFlowerAdapter;
  private FlowerPresenter mFlowerPresenter;
  private List<Flower> mList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initializeViews();
  }

  private void initializeViews() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(layoutManager);
    mFlowerAdapter = new FlowerAdapter(this, mList);
    mRecyclerView.setAdapter(mFlowerAdapter);
    mFlowerPresenter = new FlowerPresenterImpl(this);
    mFlowerPresenter.LoadData();
  }

  @Override
  public void setItem(List<Flower> mList) {
    this.mList.addAll(mList);
    mFlowerAdapter.notifyDataSetChanged();
  }
}
