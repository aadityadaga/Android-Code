package com.example.aditya.firebasechat.Model;

import android.net.Uri;

import java.io.Serializable;
import java.util.Date;

public class ChatMessage  implements Serializable{
  private String messageText;
  private String messageUser;
  private String UriImage;
  private String UriDocument;
  private String Type;
  private long messageTime;

  public ChatMessage() {}

  public ChatMessage(String messageText, String messageUser, String uriImage) {
    this.messageText = messageText;
    this.messageUser = messageUser;
    this.UriImage = uriImage;
    messageTime = new Date().getTime();
  }

  public ChatMessage(String messageText, String messageUser, String uriImage, String uriDocument, String type) {
    this.messageText = messageText;
    this.messageUser = messageUser;
    this.UriImage = uriImage;
    this.UriDocument = uriDocument;
    this.Type = type;
    messageTime = new Date().getTime();
  }

  public String getMessageText() {
    return messageText;
  }

  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }

  public String getMessageUser() {
    return messageUser;
  }

  public void setMessageUser(String messageUser) {
    this.messageUser = messageUser;
  }

  public long getMessageTime() {
    return messageTime;
  }

  public void setMessageTime(long messageTime) {
    this.messageTime = messageTime;
  }

  public String getUriImage() {
    return UriImage;
  }

  public void setUriImage(String uriImage) {
    UriImage = uriImage;
  }

  public String getType() {
    return Type;
  }

  public void setType(String type) {
    Type = type;
  }

  public String getUriDocument() {
    return UriDocument;
  }

  public void setUriDocument(String uriDocument) {
    UriDocument = uriDocument;
  }
}
