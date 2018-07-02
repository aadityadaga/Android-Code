package com.example.aditya.firebasechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aditya.firebasechat.Utility.SaveSharedPrefrences;
import com.example.aditya.firebasechat.Utility.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MAIN ACTIVITY";

  @BindView(R.id.etEmailID)
  EditText mEditTextEmailId;

  @BindView(R.id.etPassword)
  EditText mEditTextPassword;

  private FirebaseAuth mAuth;
  private String UId;

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    System.exit(0);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    firebaseInit();
  }

  private void firebaseInit() {
    mAuth = FirebaseAuth.getInstance();
    UId = mAuth.getUid();

    Utility.NetworkCheck(this);
    if (UId != null) {
      SaveSharedPrefrences.setUserEmail(this, mAuth.getCurrentUser().getEmail().toString());
      SaveSharedPrefrences.setUserName(this, mAuth.getCurrentUser().getDisplayName().toString());
      Intent mIntent = new Intent(MainActivity.this, ChatActivity.class);
      mIntent.putExtra("UID", UId);
      mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
      startActivity(mIntent);
    }
  }

  @OnClick({R.id.btnSubmit, R.id.tvCreateAccount})
  public void OnClick(View mView) {
    switch (mView.getId()) {
      case R.id.tvCreateAccount:
        Intent mIntent = new Intent(MainActivity.this, RegistrationActivity.class);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        startActivity(mIntent);
        break;
      case R.id.btnSubmit:
        loginUser();
        break;
    }
  }

  private void loginUser() {
    String strEmail, strPassword;
    strEmail = mEditTextEmailId.getText().toString();
    strPassword = mEditTextPassword.getText().toString();
    mAuth
        .signInWithEmailAndPassword(strEmail, strPassword)
        .addOnCompleteListener(
            this,
            new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  FirebaseUser user = mAuth.getCurrentUser();
                  UId = mAuth.getUid();
                  Intent mIntent = new Intent(MainActivity.this, ChatActivity.class);
                  SaveSharedPrefrences.setUserEmail(MainActivity.this, user.getEmail());
                  SaveSharedPrefrences.setUserName(MainActivity.this, user.getDisplayName());
                  mIntent.putExtra("UID", UId);
                  overridePendingTransition(R.anim.push_up_in, R.anim.push_down_out);
                  mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  startActivity(mIntent);
                } else {
                  Log.w(TAG, "signInWithEmail:failure", task.getException());
                  Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT)
                      .show();
                }
              }
            });
  }
}
