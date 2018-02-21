package com.example.aditya.fragmentaddexample;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  {
    Button AddButton,RemoveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    AddButton= (Button)findViewById(R.id.AddButton);
    RemoveButton=(Button)findViewById(R.id.RemoveButton) ;
        final FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        final Displayfragment displayfragment= new Displayfragment();
    AddButton.setOnClickListener(new View.OnClickListener() {


        @Override
        public void onClick(View view) {

                transaction.add(R.id.Framelayout, displayfragment, "Display");
                transaction.commitNowAllowingStateLoss();
        }

    });
    RemoveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                transaction.remove(displayfragment);
                transaction.commitNowAllowingStateLoss();
        }
    });

    }

}
