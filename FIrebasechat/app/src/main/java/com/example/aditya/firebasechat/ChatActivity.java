package com.example.aditya.firebasechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.aditya.firebasechat.Adapter.ProfileUserAdapter;
import com.example.aditya.firebasechat.Model.ProfileUser;
import com.example.aditya.firebasechat.Utility.SaveSharedPrefrences;
import com.example.aditya.firebasechat.Adapter.TabPageAdapter;
import com.example.aditya.firebasechat.Utility.Utility;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
  private static final String TAG = "ChatActivity";
  private static List<ProfileUser> mListName = new ArrayList<>();

  @BindView(R.id.viewPager)
  ViewPager mViewPager;

  @BindView(R.id.tabLayout)
  TabLayout mTabLayout;

  @BindView(R.id.btnCreateGroup)
  CircleImageView mCircleImageView;

  private ProfileUserAdapter mAdapter;
  private List<String> mArraylist = new ArrayList<>();
  private PersonListFragment mPersonalListFragmnet;
  private TabPageAdapter mTabPageAdapter;
  private String strEmail;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_userlist);
    ButterKnife.bind(this);
    Utility.NetworkCheck(this);
    strEmail = SaveSharedPrefrences.getUserEmail(this);
    mPersonalListFragmnet = new PersonListFragment();
    mTabPageAdapter = new TabPageAdapter(getSupportFragmentManager());
    mViewPager.setAdapter(mTabPageAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.menu, menu);
    return true;
  }

  //      public void FirebaseInit() {
  //        mDatabase
  //            .child("User")
  //            .addChildEventListener(
  //                new ChildEventListener() {
  //                  @Override
  //                  public void onChildAdded(DataSnapshot dataSnapshot, String s) {
  //                    ProfileUser mProfileUser = dataSnapshot.getValue(ProfileUser.class);
  //                    if (!strHostEmail.isEmpty()) {
  //                      if (!mProfileUser.getEmail().equalsIgnoreCase(strHostEmail)) {
  //                        AdapterAddData(mProfileUser);
  //                      } else {
  //                        SaveSharedPrefrences.setUserName(
  //                            ChatActivity.this, mProfileUser.getName().toString());
  //                      }
  //                    } else {
  //                      if (!strRegistrationName.isEmpty()) {
  //                        AdapterAddData(mProfileUser);
  //                      }
  //                    }
  //                  }
  //
  //                  @Override
  //                  public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
  //
  //                  @Override
  //                  public void onChildRemoved(DataSnapshot dataSnapshot) {}
  //
  //                  @Override
  //                  public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
  //
  //                  @Override
  //                  public void onCancelled(DatabaseError databaseError) {}
  //                });
  //        mDatabase
  //            .child("Group")
  //            .addChildEventListener(
  //                new ChildEventListener() {
  //                  @Override
  //                  public void onChildAdded(DataSnapshot dataSnapshot, String s) {
  //                    GroupUser mGoupUser = dataSnapshot.getValue(GroupUser.class);
  //                    AdapterAddGroup(mGoupUser);
  //                  }
  //
  //                  @Override
  //                  public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
  //
  //                  @Override
  //                  public void onChildRemoved(DataSnapshot dataSnapshot) {}
  //
  //                  @Override
  //                  public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
  //
  //                  @Override
  //                  public void onCancelled(DatabaseError databaseError) {}
  //                });
  //      }
  //
  //      private void AdapterAddData(ProfileUser mProfileUser) {
  //        mListName.add(mProfileUser);
  //        mListName.remove(strRegistrationName);
  //
  //
  //      private void AdapterAddGroup(GroupUser mGroupUser) {
  //        mListGroup.add(mGroupUser);
  //        mGroupAdapter = new GroupUserAdapter(ChatActivity.this, mListGroup);
  //        mListView.setAdapter(mGroupAdapter);
  //    //    mListView.setAdapter(mAdapter);
  //      }
  //
  //      @Override
  //      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
  //        Intent mIntent = new Intent(this, MessagechatActivity.class);
  //        mIntent.putExtra("Name", mListName.get(position).getName());
  //        mIntent.putExtra("ImageUrl", mListName.get(position).getUriImage());
  //        startActivity(mIntent);
  //      }
  //
  //      @Override
  //      public void onBackPressed() {
  //        if (mPressedOnce) {
  //          super.onBackPressed();
  //        }
  //        this.mPressedOnce = true;
  //        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();
  //        new Handler()
  //            .postDelayed(
  //                new Runnable() {
  //                  @Override
  //                  public void run() {
  //                    mPressedOnce = false;
  //                  }
  //                },
  //                2000);
  //      }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case R.id.menuProfile:
        Intent mInent = new Intent(this, ProfileUpdate.class);
        startActivity(mInent);
        break;
      case R.id.menuLogout:
        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        FirebaseAuth.getInstance().signOut();
        SaveSharedPrefrences.clear(this);
        startActivity(mIntent);
        break;
    }
    return true;
  }

  @OnClick(R.id.btnCreateGroup)
  public void onClick(View v) {
    mListName = mPersonalListFragmnet.getData();
    Intent mIntent = new Intent(ChatActivity.this, CreateGroupActivity.class);
    mIntent.putExtra("UserList", (Serializable) mListName);
    startActivity(mIntent);
  }
}
