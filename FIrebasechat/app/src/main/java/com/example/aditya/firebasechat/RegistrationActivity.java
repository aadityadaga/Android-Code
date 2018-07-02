package com.example.aditya.firebasechat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aditya.firebasechat.Model.ProfileUser;
import com.example.aditya.firebasechat.Utility.SaveSharedPrefrences;
import com.example.aditya.firebasechat.Utility.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.aditya.firebasechat.Utility.Utility.handleImageUri;

public class RegistrationActivity extends AppCompatActivity {
  private static final int SELECT_IMAGE = 1;
  public static String strEmailId, strPassword, strName;
  private static String UID = "";

  @BindView(R.id.etEmailID)
  EditText etEmailID;

  @BindView(R.id.etPassword)
  EditText etPassword;

  @BindView(R.id.etConfirmPassword)
  EditText etConfirmPassword;

  @BindView(R.id.etNameID)
  EditText etName;

  @BindView(R.id.profile_image)
  CircleImageView mCircleProfileImage;

  @BindView(R.id.ImageSend)
  CircleImageView mCircleImageChoose;

  private FirebaseAuth mAuth;
  private FirebaseDatabase mDatabase;
  private DatabaseReference mDatabaseReference, mDatabaseReferenceChild;
  private String selectedPath;
  private StorageReference mStorageRef;
  private String strdownloadUrl = "";

  @Override
  protected void onStart() {
    super.onStart();
    mDatabase = FirebaseDatabase.getInstance();
    mDatabaseReference = mDatabase.getReference();
    mDatabaseReferenceChild = mDatabaseReference.child("User");
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registration);
    ButterKnife.bind(this);
    Utility.NetworkCheck(this);
  }

  @OnClick({R.id.btnRegistration, R.id.ImageSend})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btnRegistration:
        strEmailId = etEmailID.getText().toString().trim();
        strPassword = etPassword.getText().toString().trim();
        strName = etName.getText().toString().trim();
        if (strEmailId.isEmpty()) {
          etEmailID.setError("enter Email id");
        } else if (etPassword.getText().toString().trim().isEmpty()) {
          etPassword.setError("enter Password");
        } else if (!etPassword
            .getText()
            .toString()
            .trim()
            .equals(etConfirmPassword.getText().toString().trim())) {
          etConfirmPassword.setError("enter Password");
        } else {

          Registration();
        }
        break;
      case R.id.ImageSend:
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT); //
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
        break;
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == SELECT_IMAGE) {
      if (data != null) {
        Uri selectedImageUri = data.getData();
        selectedImageUri = Utility.handleImageUri(selectedImageUri);
        selectedPath = Utility.getRealPathFromURI(selectedImageUri,RegistrationActivity.this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference mStorage = mStorageRef.child(data.getData().getLastPathSegment());
        final Uri finalSelectedImageUri = selectedImageUri;
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
                })
            .addOnFailureListener(
                new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {}
                });
      }
    }
  }

  private void Registration() {
    Log.d("Registration Activity", "Registration");
    mAuth = FirebaseAuth.getInstance();
    if (mAuth != null) {
      mAuth
          .createUserWithEmailAndPassword(strEmailId, strPassword)
          .addOnCompleteListener(
              RegistrationActivity.this,
              new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if (!task.isSuccessful()) {
                    Toast.makeText(
                            RegistrationActivity.this,
                            getString(R.string.Auntication),
                            Toast.LENGTH_SHORT)
                        .show();
                  } else {
                    Log.d("Registration Activity", "Else Codition");

                    try {
                      mDatabase.setPersistenceEnabled(true);
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                    ProfileUser mProfileUser = new ProfileUser(strName, strEmailId, strdownloadUrl);
                    mDatabaseReferenceChild
                        .push()
                        .setValue(mProfileUser)
                        .addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                Toast.makeText(
                                        RegistrationActivity.this,
                                        "Registration Successful",
                                        Toast.LENGTH_SHORT)
                                    .show();
                              }
                            })
                        .addOnFailureListener(
                            new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                Toast.makeText(
                                        RegistrationActivity.this,
                                        "Registration UnSuccessful",
                                        Toast.LENGTH_SHORT)
                                    .show();
                              }
                            });
                    SaveSharedPrefrences.setUserEmail(RegistrationActivity.this, strEmailId);
                    SaveSharedPrefrences.setUserName(RegistrationActivity.this, strName);
                    Intent mIntent = new Intent(RegistrationActivity.this, ChatActivity.class);
                    strName = etName.getText().toString().trim();
                    startActivity (mIntent);
                  }
                }
              });
    }
  }
}
