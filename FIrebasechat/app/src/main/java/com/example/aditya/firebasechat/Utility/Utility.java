package com.example.aditya.firebasechat.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    @SuppressLint("NewApi")
    public static String getRealPathFromURI(Uri uri, Context mContext) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};
        String sel = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = mContext.getContentResolver ().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[] {id}, null);
        int columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    public static Uri handleImageUri(Uri uri) {
        if (uri.getPath().contains("content")) {
            Pattern pattern = Pattern.compile("(content://media/.*\\d)");
            Matcher matcher = pattern.matcher(uri.getPath());
            if (matcher.find()) {
                return Uri.parse(matcher.group(1));
            } else {
                throw new IllegalArgumentException("Cannot handle this URI");
            }
        }
        return uri;
    }

    public static void NetworkCheck(final Context context) {
        if (!isNetworkConnected(context)) {
            new AlertDialog.Builder(context)
                    .setTitle("No Internet Connection")
                    .setMessage("It looks like your internet connection is off. Please turn it " +
                            "on and try again")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ((Activity) context).finish();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }

    }

    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE); // 1
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); // 2
        return networkInfo != null && networkInfo.isConnected(); // 3
    }
}
