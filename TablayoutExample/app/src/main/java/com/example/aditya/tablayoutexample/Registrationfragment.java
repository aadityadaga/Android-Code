package com.example.aditya.tablayoutexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Registrationfragment extends Fragment implements View.OnClickListener {

    EditText Email,Password,ConfirmPassword ;
    Button Submit;
    String pass,confirm;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_registrationfragment, container, false);
        Email =(EditText)view.findViewById(R.id.Emailid);
        Password=(EditText)view.findViewById(R.id.Password);
        ConfirmPassword=(EditText)view.findViewById(R.id.Passwordcopy);
        pass= String.valueOf(Password.getText());
        confirm= String.valueOf(ConfirmPassword.getText());
        Submit= (Button)view.findViewById(R.id.SubmitButton);
        Submit.setOnClickListener(this);
        return  view;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onClick(View v) {
        if (pass.trim()==confirm.trim())
        {
            Toast.makeText(getActivity(),"Registration Sucessful",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getActivity(),"Incorrect Password",Toast.LENGTH_SHORT).show();
            Email.setText("");
            Password.setText("");
            ConfirmPassword.setText("");
        }
    }
}
