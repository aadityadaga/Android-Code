package com.example.aditya.firebasechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.aditya.firebasechat.Adapter.ProfileUserAdapter;
import com.example.aditya.firebasechat.Model.ProfileUser;
import com.example.aditya.firebasechat.Utility.SaveSharedPrefrences;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonListFragment extends Fragment implements AdapterView.OnItemClickListener {
  private static List<ProfileUser> mListName = new ArrayList<ProfileUser>();

  @BindView(R.id.List_view)
  public ListView mListView;

  protected ProfileUserAdapter mAdapter;

  @BindView(R.id.FrameLayout)
  FrameLayout mFrameLayout;

  private View mView;
  private DatabaseReference mDatabase;
  private String strRegistrationName, strHostEmail;

  public PersonListFragment() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mListName.clear();
    try {
      FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mListName.clear();
    mView = inflater.inflate(R.layout.fragment_person_list, container, false);
    ButterKnife.bind(this, mView);
    FirebaseInit();
    mListView.setOnItemClickListener(this);
    return mView;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onResume() {
    mListName.clear();
    FirebaseInit();
    super.onResume();
  }

  public void FirebaseInit() {
    mDatabase = FirebaseDatabase.getInstance().getReference();
    strRegistrationName = SaveSharedPrefrences.getUserName(getContext().getApplicationContext());
    strHostEmail = SaveSharedPrefrences.getUserEmail(getContext().getApplicationContext());
    mDatabase
        .child("User")
        .addChildEventListener(
            new ChildEventListener() {
              @Override
              public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ProfileUser mProfileUser = dataSnapshot.getValue(ProfileUser.class);
                if (!strHostEmail.isEmpty()) {
                  if (!mProfileUser.getEmail().equalsIgnoreCase(strHostEmail)) {
                    AdapterAddData(mProfileUser, 0);
                  } else {
                    SaveSharedPrefrences.setUserName(
                        mView.getContext(), mProfileUser.getName().toString());
                  }
                } else {
                  if (!strRegistrationName.isEmpty()) {
                    AdapterAddData(mProfileUser, 0);
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

  public void AdapterAddData(ProfileUser mProfileUser, int viewData) {
    if (!mListName.contains(mProfileUser)) {
      mListName.add(mProfileUser);
      mListName.remove(strRegistrationName);
      mAdapter = new ProfileUserAdapter(mListName, mView.getContext(), viewData);
      mListView.setAdapter(mAdapter);
    }
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Intent mIntent = new Intent(mView.getContext(), MessagechatActivity.class);
    mIntent.putExtra("Name", mListName.get(position).getName());
    mIntent.putExtra("ImageUrl", mListName.get(position).getUriImage());
    startActivity(mIntent);
  }

  public List<ProfileUser> getData() {
    // FirebaseInit();
    return mListName;
  }
}
