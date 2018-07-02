package com.example.aditya.firebasechat.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.aditya.firebasechat.GroupFragment;
import com.example.aditya.firebasechat.PersonListFragment;

public class TabPageAdapter extends FragmentPagerAdapter {
  public TabPageAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return new PersonListFragment();
      case 1:
        return new GroupFragment();
    }
    return null;
  }

  @Override
  public int getCount() {
    return 2;
  }

  public CharSequence getPageTitle(int position) {
    String title = null;
    if (position == 0) {
      title = "Profile";
    } else if (position == 1) {
      title = "Group";
    }
    return title;
  }
}
