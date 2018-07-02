package com.example.aditya.firebasechat.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.aditya.firebasechat.Model.ProfileUser;
import com.example.aditya.firebasechat.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUserAdapter extends ArrayAdapter<ProfileUser> {
  private Context context;
  private List<ProfileUser> mArrayList;
  private List<String> mArraylistGroup = new ArrayList<>();
  private int ViewData;

  public ProfileUserAdapter(List<ProfileUser> mData, Context context, int ViewData) {
     super(context, R.layout.activity_user_name, mData);
    this.mArrayList = mData;
    this.context = context;
    this.ViewData = ViewData;
  }

  @NonNull
  @Override
  public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    final ProfileUser mProfileuser = getItem(position);
    final View result;
    final ViewHolder viewHolder;
    if (convertView == null) {
      viewHolder = new ViewHolder();
      LayoutInflater inflater = LayoutInflater.from(getContext());
      convertView = inflater.inflate(R.layout.activity_user_name, parent, false);
      viewHolder.mTextViewName = convertView.findViewById(R.id.tvEmail);
      viewHolder.mCircleImageView = convertView.findViewById(R.id.profile_image);
      viewHolder.mTextViewMessage = convertView.findViewById(R.id.tvMessage);
      viewHolder.mCheckBox = convertView.findViewById(R.id.checkbox);
      viewHolder.mCheckBox.setVisibility(View.GONE);
            if (ViewData == 1) {
              viewHolder.mCheckBox.setVisibility(View.VISIBLE);
              viewHolder.mCheckBox.setOnCheckedChangeListener(
                  new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                      if (isChecked) {
                        mArraylistGroup.add(mArrayList.get(position).getName());
                        viewHolder.mCheckBox.setChecked(true);
                      } else{
                        mArraylistGroup.remove(mArrayList.get(position).getName());
                        viewHolder.mCheckBox.setChecked(false);
                      }
                      buttonView.isChecked();
                    }
                  });
            } else {
              viewHolder.mCheckBox.setVisibility(android.view.View.GONE);
            }
      result = convertView;
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
      result = convertView;
    }
    viewHolder.mTextViewName.setText(mProfileuser.getName());
    if (!mProfileuser.getUriImage().isEmpty()) {
      Picasso.get()
          .load(mProfileuser.getUriImage())
          .resize(100, 100)
          .into(viewHolder.mCircleImageView);

    } else {
      viewHolder.mCircleImageView.setBackgroundResource(R.drawable.ic_person_black_24dp);
    }
    return convertView;
  }

  public List<String> getData() {

    return mArraylistGroup;
  }

  public List<ProfileUser> getUserDetails() {
    return mArrayList;
  }

  public static class ViewHolder {
    TextView mTextViewName;
    TextView mTextViewMessage;
    CircleImageView mCircleImageView;
    CheckBox mCheckBox;
  }
}
