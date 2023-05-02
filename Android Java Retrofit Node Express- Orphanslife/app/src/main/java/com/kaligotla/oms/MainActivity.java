package com.kaligotla.oms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.kaligotla.oms.AdminView.AdminHome;
import com.kaligotla.oms.GuardianView.GuardianHome;
import com.kaligotla.oms.SponsorView.SponsorHome;
import com.kaligotla.oms.VolunteerView.VolunteerHome;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (this.getSharedPreferences( "store", MODE_PRIVATE ).getBoolean( "sponsor_logged_in", false )) {
            startActivity( new Intent( this, SponsorHome.class ) );
            this.finish();
        } else if(this.getSharedPreferences( "store", MODE_PRIVATE ).getBoolean( "super_admin_logged_in", false )) {
            startActivity( new Intent( this, AdminHome.class ) );
            this.finish();
        } else if(this.getSharedPreferences( "store", MODE_PRIVATE ).getBoolean( "guardian_logged_in", false )) {
            startActivity( new Intent( this, GuardianHome.class ) );
            this.finish();
        } else if(this.getSharedPreferences( "store", MODE_PRIVATE ).getBoolean( "volunteer_logged_in", false )) {
            startActivity( new Intent( this, VolunteerHome.class ) );
            this.finish();
        }
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