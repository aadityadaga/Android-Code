package com.example.aditya.tablayout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstFragment extends Fragment implements View.OnClickListener {

    //reference for layout file
    EditText Email,Password ;
    Button Submit;
    @Nullable
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Email =(EditText)view.findViewById(R.id.Emailid);
        Password=(EditText)view.findViewById(R.id.Password);
        Submit= (Button)view.findViewById(R.id.SubmitButton);
        Submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(),"Login Successful ",Toast.LENGTH_SHORT).show();
        Email.setText("");
        Password.setText("");

    }
}