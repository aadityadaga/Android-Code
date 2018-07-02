package com.example.aditya.retrofit_mvp.View;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aditya.retrofit_mvp.Model.Flower;
import com.example.aditya.retrofit_mvp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.ViewHolder> {
  private int mPosition;
  private Activity mActivity;
  private List<Flower> mList;
  private String URL = "http://services.hanselandpetal.com/photos/";

  public FlowerAdapter(Activity mActivity, List<Flower> mList) {
    this.mActivity = mActivity;
    this.mList = mList;
  }

  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View mView =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.activity_item_view, parent, false);
    return new ViewHolder(mView);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    final Flower mFlower = mList.get(position);
    Picasso.with(mActivity.getApplicationContext())
        .load(URL + mFlower.getPhoto())
        .resize(250, 200)
        .into(holder.mImageViewer);
    holder.mTextView.setText(mFlower.getName());
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ClickListener mClickListener = new ClickListerImpl(mActivity, mList);
    @BindView(R.id.ImageViewer)
    ImageView mImageViewer;

    @BindView(R.id.textViewDetail)
    TextView mTextView;

    @BindView(R.id.ln_item)
    LinearLayout lnitem;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener ( this );
    }

    @Override
    public void onClick(View v) {
      mClickListener.onClick(mActivity, mList, getAdapterPosition());
    }
  }
}
