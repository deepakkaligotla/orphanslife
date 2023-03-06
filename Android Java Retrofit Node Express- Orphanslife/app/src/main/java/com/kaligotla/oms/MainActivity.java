package com.kaligotla.oms;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = new Bundle();
        bundle.putString("key","Hello From Activity");
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,LoginFragment.class,bundle).commit();
    }
    public void loadLoginFragment(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,LoginFragment.class,bundle).commit();
    }
    public void loadRegisterFragment(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,RegisterFragment.class,bundle).commit();
    }
}