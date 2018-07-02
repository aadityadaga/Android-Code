package com.example.aditya.firebasechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aditya.firebasechat.Adapter.ProfileUserAdapter;
import com.example.aditya.firebasechat.Model.ProfileUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateGroupActivity extends AppCompatActivity implements View.OnClickListener {
  @BindView(R.id.List_view)
  ListView mListView;

  ProfileUserAdapter mProfileAdapter;
  private List<ProfileUser> mListName = new ArrayList<>();
  private List<String> mListNameGroup = new ArrayList<>();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_group);
    Bundle mbundle = getIntent().getExtras();
    ButterKnife.bind(this);
    mListName = (List<ProfileUser>) mbundle.getSerializable("UserList");
    Toast.makeText(this, "" + mListName, Toast.LENGTH_SHORT).show();
    mProfileAdapter = new ProfileUserAdapter(mListName, this, 1);
    mListView.setAdapter(mProfileAdapter);
  }

  @OnClick(R.id.btnCreateGroupConfirm)
  @Override
  public void onClick(View v) {
    mListNameGroup = mProfileAdapter.getData();
    if (!mListNameGroup.isEmpty()) {
      Intent mIntent = new Intent(this, GroupChatActivity.class);
      mIntent.putExtra("data", (Serializable) mListNameGroup);
      startActivity(mIntent);
    } else {
      Toast.makeText(this, "Select the Users", Toast.LENGTH_SHORT).show();
    }

    //        if (!mArraylist.isEmpty()) {
    //          mIntent = new Intent(ChatActivity.this, GroupChatActivity.class);
    //          mIntent.putExtra("data", (Serializable) mArraylist);
    //          startActivity(mIntent);
    //        } else {
    //          Toast.makeText(ChatActivity.this, "Select the Users", Toast.LENGTH_SHORT).show();
    //        }
  }
}
