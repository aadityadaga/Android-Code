package com.example.aditya.retrofit_mvp.Presenter;

import com.example.aditya.retrofit_mvp.Model.Flower;
import com.example.aditya.retrofit_mvp.View.FlowerView;

import java.util.List;

public class FlowerPresenterImpl implements FlowerPresenter, FlowerInteractor.CompleteListener {
  private FlowerView mFlowerView;
  private FlowerInteractor mFlowerInteractor;

  public FlowerPresenterImpl(FlowerView mFlowerView) {
    this.mFlowerView = mFlowerView;
    this.mFlowerInteractor = new FlowerInteractorImpl ();
  }

  @Override
  public void LoadData() {
    mFlowerInteractor.getData(this);
  }

  @Override
  public void ReadyData(List<Flower> mList) {
    if (!mList.isEmpty()) {
      mFlowerView.setItem(mList);
    }
  }
}
