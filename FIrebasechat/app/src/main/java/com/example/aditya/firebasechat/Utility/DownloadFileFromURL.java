package com.example.aditya.firebasechat.Utility;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileFromURL extends AsyncTask<String, String, String> {
  private int count;
String strName;
  public DownloadFileFromURL(String s) {
    this.strName = s;
  }

    @Override
  protected String doInBackground(String... strings) {
    URL url = null;
    try {
      url = new URL(strings[0]);
      URLConnection conection = null;
      conection = url.openConnection();
      conection.connect();
      int lenghtOfFile = conection.getContentLength();
      InputStream input = new BufferedInputStream(url.openStream(), 8192);
      OutputStream output = new FileOutputStream("/sdcard/" + strName+".pdf");
      byte data[] = new byte[1024];
      long total = 0;
      while ((count = input.read(data)) != -1) {
        total += count;
        publishProgress("" + (int) ((total * 100) / lenghtOfFile));
        output.write(data, 0, count);
      }
      output.flush();
      output.close();
      input.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute ( s );

    }
}
