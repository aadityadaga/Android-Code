package com.example.aditya.firebasechat.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.firebasechat.Constant.Constant;
import com.example.aditya.firebasechat.Model.ChatMessage;
import com.example.aditya.firebasechat.R;
import com.example.aditya.firebasechat.Retrofit.APIImplementation;
import com.example.aditya.firebasechat.Retrofit.APIInterface;
import com.example.aditya.firebasechat.Utility.DownloadFileFromURL;
import com.example.aditya.firebasechat.Utility.SaveSharedPrefrences;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import me.himanshusoni.chatmessageview.ChatMessageView;

public class MessagelistAdapter extends RecyclerView.Adapter<MessagelistAdapter.MyViewHolder>
    implements View.OnClickListener {
  public static int position;
  private final List<ChatMessage> mList;
  private Context mContext;
  private String PDFDocumentUri;
  private APIInterface apiInterface;

  public MessagelistAdapter(List<ChatMessage> mList, Context context) {
    this.mList = mList;
    this.mContext = context;
    apiInterface = APIImplementation.getClient().create(APIInterface.class);
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View itemView =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.activity_chat_message_list, parent, false);

    return new MyViewHolder(itemView);
  }

  @Override
  public int getItemViewType(int position) {
    super.getItemViewType(position);
    this.position = position;
    return position;
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    ChatMessage mChatMessage = mList.get(position);
    String mStrUser = mChatMessage.getMessageUser();
    if (mStrUser.equals(SaveSharedPrefrences.getUserName(mContext))) {
      if (!mChatMessage.getMessageText().isEmpty()) {
        holder.mImageviewRight.setVisibility(View.GONE);
        holder.mPDFViewRight.setVisibility(View.GONE);
        holder.mTextViewMessageRight.setText(mChatMessage.getMessageText());
      } else if (mChatMessage.getType().equals("pdf")) {
        PDFDocumentUri = mChatMessage.getUriDocument();
        holder.mImageviewRight.setVisibility(View.GONE);
        holder.mPDFViewRight.setVisibility(View.VISIBLE);
      } else {
        holder.mTextViewMessageRight.setVisibility(View.GONE);
        holder.mImageviewRight.setVisibility(View.VISIBLE);
        DownloadImage(mChatMessage.getUriImage(), holder.mImageviewRight);
      }
      holder.mTextViewMessage.setVisibility(View.GONE);
      holder.mTextViewDateRight.setText(getDate(mChatMessage.getMessageTime()));
      holder.mTextViewTimeRight.setText(getTime(mChatMessage.getMessageTime()));
      holder.mChatMessageView.setVisibility(View.GONE);
      holder.mChatMessageViewRight.setVisibility(View.VISIBLE);
    } else {
      if (!mChatMessage.getMessageText().isEmpty()) {
        holder.mImageviewLeft.setVisibility(View.GONE);
        holder.mTextViewMessage.setText(mChatMessage.getMessageText());
        holder.mPDFViewLeft.setVisibility(View.GONE);
      } else if (mChatMessage.getType().equals("pdf")) {
        PDFDocumentUri = mChatMessage.getUriDocument();
        holder.mImageviewLeft.setVisibility(View.GONE);
        holder.mPDFViewLeft.setVisibility(View.VISIBLE);
      } else {
        holder.mTextViewMessage.setVisibility(View.GONE);
        holder.mImageviewLeft.setVisibility(View.VISIBLE);
        DownloadImage(mChatMessage.getUriImage(), holder.mImageviewLeft);
      }
      holder.mTextViewMessageRight.setVisibility(View.GONE);
      holder.mTextViewDateLeft.setText(getDate(mChatMessage.getMessageTime()));
      holder.mTextViewTimeLeft.setText(getTime(mChatMessage.getMessageTime()));
      holder.mChatMessageViewRight.setVisibility(View.GONE);
      holder.mChatMessageView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  private String getTime(long time) {
    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    cal.setTimeInMillis(time);
    String date = DateFormat.format("dd-MM-yyyy hh:mm aa", cal).toString();
    String[] datearray = date.split(" ");
    return datearray[1] + datearray[2];
  }

  private String getDate(long time) {
    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    cal.setTimeInMillis(time);
    String date = DateFormat.format("dd-MM-yyyy hh:mm aa", cal).toString();
    String[] datearray = date.split(" ");
    return datearray[0];
  }

  public void DownloadImage(final String strurl, final ImageView mImageview) {
    Log.d("MessagelistAdapter", "DownloadImage:" + strurl);
    Picasso.get()
        .load(strurl)
        .error(R.drawable.ic_broken_image_black_24dp)
        .resize(200, 200)
        .into(mImageview);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.ImageViewLeft:
        Toast.makeText(mContext, "Imageviewleft", Toast.LENGTH_SHORT).show();
        break;
      case R.id.ImageViewRight:
        Toast.makeText(mContext, "ImageviewRight", Toast.LENGTH_SHORT).show();
        break;
    }
  }

  private void DowndloadPDF(String pdfDocumentUri) {
   String[] str = pdfDocumentUri.split( Constant.BASE_URL);
    new DownloadFileFromURL(str[1].toString ()).execute(pdfDocumentUri);
    //    String[] str = pdfDocumentUri.split(Constant.BASE_URL);
    //    Call<ResponseBody> mCall = apiInterface.getPDF(str[1].toString ());
    //    mCall.enqueue(
    //        new Callback<ResponseBody>() {
    //          @Override
    //          public void onResponse(
    //              Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
    //            ResponseBody mResponsebody = response.body();
    //          }
    //
    //          @Override
    //          public void onFailure(Call<ResponseBody> call, Throwable t) {
    //            Toast.makeText(mContext, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    //          }
    //        });
  }

  public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mTextViewMessage,
        mTextViewMessageRight,
        mTextViewDateRight,
        mTextViewTimeRight,
        mTextViewTimeLeft,
        mTextViewDateLeft;
    public ChatMessageView mChatMessageView, mChatMessageViewRight;
    public ImageView mImageviewLeft, mImageviewRight, mPDFViewLeft, mPDFViewRight;

    public MyViewHolder(View itemView) {
      super(itemView);

      mTextViewMessage = itemView.findViewById(R.id.tvMessageLeft);
      mTextViewMessageRight = itemView.findViewById(R.id.tvMessageRight);
      mChatMessageView = itemView.findViewById(R.id.tvChatmessageLeft);
      mChatMessageViewRight = itemView.findViewById(R.id.tvChatmessageRight);
      mTextViewDateRight = itemView.findViewById(R.id.tvDateRight);
      mTextViewDateLeft = itemView.findViewById(R.id.tvDateleft);
      mImageviewLeft = itemView.findViewById(R.id.ImageViewLeft);
      mImageviewRight = itemView.findViewById(R.id.ImageViewRight);
      mTextViewTimeRight = itemView.findViewById(R.id.tvTimeRight);
      mTextViewTimeLeft = itemView.findViewById(R.id.tvTimeLeft);
      mPDFViewLeft = itemView.findViewById(R.id.ImagePDFViewLeft);
      mPDFViewRight = itemView.findViewById(R.id.ImagePDFViewRight);
      mPDFViewLeft.setOnClickListener(this);
      mPDFViewRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.ImagePDFViewLeft:
          DowndloadPDF(PDFDocumentUri);
          break;
        case R.id.ImagePDFViewRight:
          DowndloadPDF(PDFDocumentUri);
          break;
      }
    }
  }
}
