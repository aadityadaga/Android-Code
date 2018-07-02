package com.example.aditya.firebasechat.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.aditya.firebasechat.Model.GroupUser;
import com.example.aditya.firebasechat.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupUserAdapter extends ArrayAdapter<GroupUser> {
  private Context context;
  private List<GroupUser> mArrayList = new ArrayList<>();
  private ViewHolder viewHolder;
  private View result;

  public GroupUserAdapter(@NonNull Context context, List<GroupUser> mArrayList) {
    super(context, R.layout.activity_user_name, mArrayList);
    this.context = context;
    this.mArrayList = mArrayList;
  }

  @Nullable
  @Override
  public GroupUser getItem(int position) {
    return super.getItem(position);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    if (convertView == null) {
      viewHolder = new GroupUserAdapter.ViewHolder();
      LayoutInflater inflater = LayoutInflater.from(getContext());
      convertView = inflater.inflate(R.layout.activity_user_name, parent, false);
      viewHolder.mTextViewName = convertView.findViewById(R.id.tvEmail);
      viewHolder.mCircleImageView = convertView.findViewById(R.id.profile_image);
      viewHolder.mTextViewMessage = convertView.findViewById(R.id.tvMessage);
      viewHolder.mCheckBox = convertView.findViewById(R.id.checkbox);
      viewHolder.mCheckBox.setVisibility(View.GONE);
      viewHolder.mTextViewName.setText(mArrayList.get(0).getGroupName());
      result = convertView;
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (GroupUserAdapter.ViewHolder) convertView.getTag();
      result = convertView;
    }
    return result;
  }

  public static class ViewHolder {
    TextView mTextViewName;
    TextView mTextViewMessage;
    CircleImageView mCircleImageView;
    CheckBox mCheckBox;
  }
}
