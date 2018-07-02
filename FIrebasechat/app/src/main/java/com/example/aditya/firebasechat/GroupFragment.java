package com.example.aditya.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.aditya.firebasechat.Adapter.GroupUserAdapter;
import com.example.aditya.firebasechat.Model.GroupUser;
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

public class GroupFragment extends Fragment implements AdapterView.OnItemClickListener {
private static List<GroupUser> mListName = new ArrayList<GroupUser>();
  @BindView(R.id.List_view)
  public ListView mListView;
  protected GroupUserAdapter mAdapter;
  @BindView(R.id.FrameLayout)
  FrameLayout mFrameLayout;
  private View mView;
    private DatabaseReference mDatabase;;
  private String strRegistrationName, strHostEmail;

  public GroupFragment() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mView = inflater.inflate(R.layout.fragment_person_list, container, false);
    ButterKnife.bind(this, mView);
    FirebaseInit();
    mListView.setOnItemClickListener(this);
    return mView;
  }

  public void FirebaseInit() {
    mDatabase = FirebaseDatabase.getInstance().getReference();
    strRegistrationName = SaveSharedPrefrences.getUserName(getActivity().getApplicationContext ());
    strHostEmail = SaveSharedPrefrences.getUserEmail(getActivity().getApplicationContext ());
    mDatabase
        .child("Group")
        .addChildEventListener(
            new ChildEventListener() {
              @Override
              public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GroupUser mGoupUser = dataSnapshot.getValue(GroupUser.class);
                  AdapterAddData(mGoupUser, 0);
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
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Intent mIntent = new Intent(mView.getContext(), MessagechatActivity.class);
    mIntent.putExtra("GroupName", mListName.get(position).getGroupName());
    startActivity(mIntent);
  }

  public void AdapterAddData(GroupUser mProfileUser, int viewData) {
    mListName.add(mProfileUser);
    mListName.remove(strRegistrationName);
    mAdapter = new GroupUserAdapter(mView.getContext(), mListName);
    mListView.setAdapter(mAdapter);
  }
}
