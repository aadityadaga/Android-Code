package com.example.aditya.firebasechat.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupUser implements Serializable {
    String groupName;
    List<String> groupMember = new ArrayList<> ();
    long messageTime;

    public GroupUser(String groupName, List<String> groupMember) {
        this.groupName = groupName;
        this.groupMember = groupMember;
        this.messageTime = new Date ().getTime();
    }

    public GroupUser() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(List<String> groupMember) {
        this.groupMember = groupMember;
    }
}
