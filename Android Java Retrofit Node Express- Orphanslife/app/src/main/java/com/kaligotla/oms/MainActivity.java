package com.kaligotla.oms;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, LoginFragment.class, bundle).commit();
        }
        bundle = new Bundle();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void loadLoginFragment(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, LoginFragment.class, bundle).commit();
    }

    public void loadRegisterFragment(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, RegisterFragment.class, bundle).commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}