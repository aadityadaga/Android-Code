package com.example.aditya.fragmentbundleexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendFragment extends Fragment implements View.OnClickListener {
    View mView;
    @BindView(R.id.btnSend)
    Button mBtnSend;
    @BindView(R.id.etData)
    EditText mEditTextRead;


    String mStrData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_send, container, false);
        ButterKnife.bind(this, mView);
        mBtnSend.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View v) {
        mStrData = String.valueOf(mEditTextRead.getText());
        Fragment fragment = new RecivedFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Data", mStrData);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }
}
