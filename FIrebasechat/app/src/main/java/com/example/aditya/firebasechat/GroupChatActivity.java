package com.example.aditya.firebasechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aditya.firebasechat.Model.GroupUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupChatActivity extends AppCompatActivity implements View.OnClickListener {
  @BindView(R.id.etGroupName)
  EditText mEditTextGroupName;

  List<String> mArrayList = new ArrayList<>();
  String mStringGroupName;
  private FirebaseDatabase mDatabase;
  private DatabaseReference mDatabaseReference, mDatabaseReferenceChild;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_chat);
    ButterKnife.bind(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    Bundle mBundle = getIntent().getExtras();
    if (mBundle != null) {
      mArrayList = mBundle.getStringArrayList("data");
    }
    mDatabase = FirebaseDatabase.getInstance();
    mDatabaseReference = mDatabase.getReference();
    mDatabaseReferenceChild = mDatabaseReference.child("Group");
  }

  @OnClick(R.id.btnUpdate)
  @Override
  public void onClick(View v) {
    mStringGroupName = mEditTextGroupName.getText().toString();
    final GroupUser mGroupUser = new GroupUser(mStringGroupName, mArrayList);
    mDatabaseReferenceChild
        .push()
        .setValue(mGroupUser)
        .addOnSuccessListener(
            new OnSuccessListener<Void>() {
              @Override
              public void onSuccess(Void aVoid) {
                Toast.makeText(
                        GroupChatActivity.this, R.string.group_created, Toast.LENGTH_SHORT)
                    .show();
                Intent mIntent = new Intent(GroupChatActivity.this, ChatActivity.class);
                startActivity(mIntent);
              }
            })
        .addOnFailureListener(
            new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                Toast.makeText(
                        GroupChatActivity.this, R.string.group_created_Unsuccessful, Toast.LENGTH_SHORT)
                    .show();
              }
            });
  }
}
