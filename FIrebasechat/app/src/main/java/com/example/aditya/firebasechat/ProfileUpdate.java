package com.example.aditya.firebasechat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.firebasechat.Model.ProfileUser;
import com.example.aditya.firebasechat.Utility.SaveSharedPrefrences;
import com.example.aditya.firebasechat.Utility.Utility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUpdate extends AppCompatActivity {
  public String strdownloadUrl;

  @BindView(R.id.profile_image)
  CircleImageView mCircleProfileImage;

  @BindView(R.id.ImageSend)
  CircleImageView mCircleImageChoose;

  @BindView(R.id.tvEmail)
  TextView tvEmail;

  @BindView(R.id.tvName)
  TextView tvName;

  private FirebaseAuth mAuth;
  private String selectedPath, strEmail;
  private FirebaseDatabase mDatabase;
  private DatabaseReference mDatabaseReference, mDatabaseReferenceChild;
  private FirebaseUser mUser;
  private MessagechatActivity mMessageChatActivity;
  private int SELECT_IMAGE = 1;
  private StorageReference mStorageRef;
  private ArrayList<ProfileUser> mListName;

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == SELECT_IMAGE) {
      if (data != null) {
        Uri selectedImageUri = data.getData();
        selectedImageUri = Utility.handleImageUri(selectedImageUri);
        selectedPath = Utility.getRealPathFromURI(selectedImageUri, ProfileUpdate.this);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("User");
        final Uri finalSelectedImageUri = selectedImageUri;
        StorageReference mStorage = mStorageRef.child(data.getData().getLastPathSegment());
        mStorage
            .putFile(selectedImageUri)
            .addOnSuccessListener(
                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    try {
                      Bitmap bitmap =
                          MediaStore.Images.Media.getBitmap(
                              getContentResolver(), finalSelectedImageUri);
                      mCircleProfileImage.setImageBitmap(bitmap);
                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                    strdownloadUrl = String.valueOf(taskSnapshot.getDownloadUrl());
                  }
                });
      }
    }
  }

  public void FirebaseInit() {
    mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    try {
      FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
    mDatabaseReference
        .child("User")
        .addChildEventListener(
            new ChildEventListener() {
              @Override
              public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ProfileUser mProfileUser = dataSnapshot.getValue(ProfileUser.class);
                if (mProfileUser
                    .getEmail()
                    .equalsIgnoreCase(SaveSharedPrefrences.getUserEmail(ProfileUpdate.this))) {
                  tvEmail.setText(mProfileUser.getEmail());
                  tvName.setText(mProfileUser.getName());
                  if (!mProfileUser.getUriImage().isEmpty()) {
                    Picasso.get().load(mProfileUser.getUriImage()).into(mCircleProfileImage);
                  }
                }
              }

              @Override
              public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

              @Override
              public void onChildRemoved(DataSnapshot dataSnapshot) {}

              @Override
              public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

              @Override
              public void onCancelled(DatabaseError databaseError) {}
            });
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_update);
    ButterKnife.bind(this);
    mDatabase = FirebaseDatabase.getInstance();
    mDatabaseReference = mDatabase.getReference();
    mDatabaseReferenceChild = mDatabaseReference.child("User");
  }

  @Override
  protected void onStart() {
    super.onStart();
    FirebaseInit();
  }

  @OnClick({R.id.ImageSend, R.id.btnUpdate})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.ImageSend:
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT); //
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
        break;
      case R.id.btnUpdate:
        ProfileUser mProfileUser =
            new ProfileUser(
                SaveSharedPrefrences.getUserName(this),
                SaveSharedPrefrences.getUserEmail(this),
                strdownloadUrl);
        mDatabaseReferenceChild
            .child(SaveSharedPrefrences.getUserKey(ProfileUpdate.this))
            .setValue(mProfileUser)
            .addOnSuccessListener(
                new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                    Toast.makeText(ProfileUpdate.this, "Update Successful", Toast.LENGTH_SHORT)
                        .show();
                  }
                })
            .addOnFailureListener(
                new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileUpdate.this, "Update UnSuccessful", Toast.LENGTH_SHORT)
                        .show();
                  }
                });
        break;
    }
  }
}
