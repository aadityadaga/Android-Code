package com.example.aditya.firebasechat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.firebasechat.Adapter.MessagelistAdapter;
import com.example.aditya.firebasechat.Model.ChatMessage;
import com.example.aditya.firebasechat.Utility.SaveSharedPrefrences;
import com.example.aditya.firebasechat.Utility.Utility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class MessagechatActivity extends AppCompatActivity
    implements View.OnClickListener, EmojIconActions.KeyboardListener, TextWatcher {
  private static final int SELECT_IMAGE = 1;
  private static final int SELECT_DOCUMENT = 2;
  private static final String TAG = "MessagechatActivity";
  public String strHostName, strName, strdownloadUrl, strGroupName;
  public FirebaseDatabase mDatabase;
  public DatabaseReference mDatabaseReference, mDatabaseReferenceAnother;
  public ArrayList<ChatMessage> mArraylist = new ArrayList<ChatMessage>();
  public MessagelistAdapter mMessagelistAdapter;
  public StorageReference mStorageRef;
  public int mIntStringCompareResult;

  @BindView(R.id.listMessage)
  public RecyclerView mRecyclerListView;

  @BindView(R.id.button_chatbox_send)
  CircleButton mCircleButton;

  @BindView(R.id.edittext_chatbox)
  EditText mEditTextChat;

  @BindView(R.id.edittext_hani_chatbox)
  EmojiconEditText mEmojiEditTextChat;

  @BindView(R.id.ImageSend)
  ImageView mImageView;

  @BindView(R.id.ImageAttachfile)
  ImageView mImageViewAttachfile;

  @BindView(R.id.KeyBoardEmoji)
  ImageView mImageViewEmoji;

  @BindView(R.id.mRootView)
  View mRootView;

  @BindView(R.id.Toolbar)
  android.support.v7.widget.Toolbar mToolbar;

  @BindView(R.id.CircleImageViewProfile)
  CircleImageView mImageViewProfile;

  @BindView(R.id.tvEmail)
  TextView mTextViewUserName;

  private int count;
  private String selectedPath;
  private EmojIconActions mEmojiIcons;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_messagechat);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    Utility.NetworkCheck(this);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mArraylist.clear();
  }

  @Override
  protected void onStart() {
    super.onStart();
    Bundle mBundle = getIntent().getExtras();
    strName = mBundle.getString("Name");
    if (strName == null) {
      strName = "";
    }
    mRecyclerListView.scrollToPosition(MessagelistAdapter.position);
    strGroupName = mBundle.getString("GroupName");
    strdownloadUrl = mBundle.getString("ImageUrl");
    if (!strName.isEmpty()) {
      mTextViewUserName.setText(strName);
    } else {
      mTextViewUserName.setText(strGroupName);
    }
    strHostName = SaveSharedPrefrences.getUserName(MessagechatActivity.this);
    if (strdownloadUrl != null) {
      if (!strdownloadUrl.isEmpty()) {
        Picasso.get().load(strdownloadUrl).into(mImageViewProfile);
      }
    } else {
      mImageViewProfile.setImageResource(R.drawable.ic_person_black_24dp);
    }

    mDatabase = FirebaseDatabase.getInstance();
    try {
      mDatabase.setPersistenceEnabled(true);
    } catch (Exception e) {
      e.printStackTrace();
      Log.d(TAG, e.getLocalizedMessage());
    }
    if (!strName.isEmpty()) {
      mDatabaseReference = mDatabase.getReference("Chat_Message");
      checkChildUpdate();
      getAllData();

    } else {
      mDatabaseReference = mDatabase.getReference("Group_Message");
      getGroupData();
    }

    mDatabaseReference.keepSynced(true);
    mCircleButton.setOnClickListener(this);
    mImageView.setOnClickListener(this);
    mRecyclerListView.addItemDecoration(new DividerItemDecoration(this, 0));
    mRecyclerListView.setItemAnimator(null);
    mRecyclerListView.refreshDrawableState();
    mEmojiIcons = new EmojIconActions(this, mRootView, mEmojiEditTextChat, mImageViewEmoji);
    mEmojiIcons.ShowEmojIcon();
    mEmojiIcons.setIconsIds(R.drawable.ic_keyboard_black_24dp, R.drawable.ic_mood_black_24dp);
    mEmojiEditTextChat.addTextChangedListener(this);
    mEmojiEditTextChat.animate();
    LinearLayoutManager mLayoutManager =
        new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
    mLayoutManager.setSmoothScrollbarEnabled(true);
    mLayoutManager.setStackFromEnd(true);
    mRecyclerListView.setLayoutManager(mLayoutManager);
    mRecyclerListView.setHasFixedSize(true);
    mMessagelistAdapter = new MessagelistAdapter(mArraylist, MessagechatActivity.this);
    mRecyclerListView.setAdapter(mMessagelistAdapter);
  }

  private void getGroupData() {
    mDatabaseReference
        .child(strGroupName)
        .addChildEventListener(
            new ChildEventListener() {
              @Override
              public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ChatMessage mChatMessage = dataSnapshot.getValue(ChatMessage.class);
                mArraylist.add(mChatMessage);
                if (mArraylist != null) {
                  mMessagelistAdapter.notifyDataSetChanged();
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

  private void checkChildUpdate() {
    mIntStringCompareResult = strHostName.compareTo(strName);
    if (mIntStringCompareResult > 0) {
      strName = strName + strHostName;
      strHostName = strName.substring(0, strName.length() - strHostName.length());
      strName = strName.substring(strHostName.length());
      SaveSharedPrefrences.setUserName(this, strName);
    }
  }

  private void getAllData() {

    mDatabaseReference
        .child(strName + "-" + strHostName)
        .addChildEventListener(
            new ChildEventListener() {
              @Override
              public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ChatMessage mChatMessage = dataSnapshot.getValue(ChatMessage.class);
                mArraylist.add(mChatMessage);
                if (mArraylist != null) {
                  mMessagelistAdapter.notifyDataSetChanged();
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

  @OnClick({
    R.id.ImageSend,
    R.id.button_chatbox_send,
    R.id.KeyBoardEmoji,
    R.id.ImageviewBack,
    R.id.ImageAttachfile
  })
  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.button_chatbox_send:
        String message = mEmojiEditTextChat.getText().toString();
        if (!message.isEmpty()) {
          FirebaseUpload(message, "", "", "");
        }
        mEmojiEditTextChat.setText("");
        mImageView.setVisibility(View.VISIBLE);
        break;
      case R.id.ImageSend:
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT); //
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
        break;
      case R.id.KeyBoardEmoji:
        mEmojiIcons.setKeyboardListener(this);
        break;
      case R.id.ImageviewBack:
        finish();
        break;
      case R.id.ImageAttachfile:
        Intent mIntent = new Intent();
        mIntent.setAction(Intent.ACTION_GET_CONTENT);
        mIntent.setType("application/pdf");
        mIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivity(mIntent);
        startActivityForResult(Intent.createChooser(mIntent, "SelectPDf"), SELECT_DOCUMENT);
        break;
    }
  }

  public void FirebaseUpload(String message, String downloadUrl, String Document, String Type) {
    ChatMessage mChatMessage =
        new ChatMessage(
            message,
            SaveSharedPrefrences.getUserName(MessagechatActivity.this),
            downloadUrl,
            Document,
            Type);
    if (strName.isEmpty()) {
      mDatabaseReference.child(strGroupName).push().setValue(mChatMessage);
    } else {
      mDatabaseReference.child(strName + "-" + strHostName).push().setValue(mChatMessage);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case SELECT_IMAGE:
        if (requestCode == SELECT_IMAGE) {
          if (data != null) {
            Uri selectedImageUri = data.getData();
            selectedImageUri = Utility.handleImageUri(selectedImageUri);
            selectedPath = Utility.getRealPathFromURI(selectedImageUri, MessagechatActivity.this);
            mStorageRef = FirebaseStorage.getInstance().getReference();
            StorageReference mStorage = mStorageRef.child(data.getData().getLastPathSegment());
            mStorage
                .putFile(selectedImageUri)
                .addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        strdownloadUrl = String.valueOf(taskSnapshot.getDownloadUrl());
                        FirebaseUpload(null, strdownloadUrl, "", "Image");
                        Log.d("Download Uri", "" + strdownloadUrl);
                        getAllData();
                      }
                    })
                .addOnFailureListener(
                    new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                MessagechatActivity.this,
                                "" + e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT)
                            .show();
                      }
                    });
          }
        }
        break;
      case SELECT_DOCUMENT:
        Toast.makeText(MessagechatActivity.this, "Inside select document", Toast.LENGTH_SHORT)
            .show();
        if (data != null) {
          mStorageRef = FirebaseStorage.getInstance().getReference();
          StorageReference mStorage = mStorageRef.child(data.getData().getLastPathSegment());
          mStorage
              .putFile(Uri.parse(String.valueOf(data.getData())))
              .addOnSuccessListener(
                  new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      strdownloadUrl = String.valueOf(taskSnapshot.getDownloadUrl());
                      FirebaseUpload("", "", strdownloadUrl, "pdf");
                    }
                  })
              .addOnFailureListener(
                  new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {}
                  });
        }
        break;
    }
  }

  // KeyboardEmojiIcon
  @Override
  public void onKeyboardOpen() {
    mEmojiIcons.ShowEmojIcon();
  }

  @Override
  public void onKeyboardClose() {
    mEmojiIcons.closeEmojIcon();
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    Log.d(TAG, "beforeTextChanged: " + s);
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    if (count != 0 | start != 0) {
      mImageView.setVisibility(View.GONE);
    } else {
      mImageView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void afterTextChanged(Editable s) {}

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }
}
