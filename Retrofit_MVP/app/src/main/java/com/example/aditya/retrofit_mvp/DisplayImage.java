package com.example.aditya.retrofit_mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.aditya.retrofit_mvp.Model.Flower;
import com.example.aditya.retrofit_mvp.Presenter.DisplayImageImpl;
import com.example.aditya.retrofit_mvp.Presenter.DisplayImageInterface;
import com.example.aditya.retrofit_mvp.View.FlowerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayImage extends AppCompatActivity implements FlowerView.DisplayImage {
@BindView ( R.id.ImageViewerFull )
    ImageView mImageView;
    Flower mFlower ;
    private DisplayImageInterface mDisplayImageInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_display_image );
        ButterKnife.bind ( this );
        mDisplayImageInterface = new DisplayImageImpl (this);
       displayImage ();
    }

    @Override
    public void displayImage() {
        Intent mIntent = getIntent ();
        mFlower= (Flower) mIntent.getSerializableExtra ("Data");
        mDisplayImageInterface.displayImageView ( mFlower,mImageView );
    }
}
