package com.example.aditya.firebasechat.Model;

import java.io.Serializable;

public class ProfileUser implements Serializable {
  private String Name, Email, UriImage;

  public ProfileUser() {}

  public ProfileUser(String name, String email, String uriImage) {
    this.Name = name;
    this.Email = email;
    this.UriImage = uriImage;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getEmail() {
    return Email;
  }

  public void setEmail(String email) {
    Email = email;
  }

  public String getUriImage() {
    return UriImage;
  }

  public void setUriImage(String uriImage) {
    UriImage = uriImage;
  }
}
