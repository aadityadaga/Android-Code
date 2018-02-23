package com.example.aditya.fragmentbundleexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecivedFragment extends Fragment {

    View mView;
    @BindView(R.id.tvDataview)
    TextView mTextViewData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_recived, container, false);
        ButterKnife.bind(this, mView);

        String getArgument = getArguments().getString("Data");
        mTextViewData.setText(getArgument);
        return mView;
    }

}
