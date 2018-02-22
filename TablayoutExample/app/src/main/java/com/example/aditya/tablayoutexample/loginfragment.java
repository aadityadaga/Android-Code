package com.example.aditya.tablayoutexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;


public class loginfragment extends Fragment implements View.OnClickListener {
    EditText Email,Password ;
    Button Submit;
    View view;
    String email,pass;
   Pattern emailRegex = Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_loginfragment, container, false);
        super.onViewCreated(view, savedInstanceState);
        Email =(EditText)view.findViewById(R.id.Emailid);
        Password=(EditText)view.findViewById(R.id.Password);
        Submit= (Button)view.findViewById(R.id.SubmitButton);
        Submit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        email = Email.getText().toString().trim();
        pass =Password.getText().toString().trim();
        if (email.length()==0) {
            Toast.makeText(getActivity(),"enter Email",Toast.LENGTH_SHORT).show();
             }
         else if(pass.length()==0)
        {
            Toast.makeText(getActivity(),"enter Password",Toast.LENGTH_SHORT).show();
        }
         else {
            if (checkEmail(email)) {
                Toast.makeText(getActivity(), "Login Successful ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), WelcomeScreen.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getActivity(),"enter Valid Email",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean checkEmail(String email) {
        return emailRegex.matcher(email).matches();
    }
}