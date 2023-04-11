package com.kaligotla.oms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.SponsorView.Adopt;
import com.kaligotla.oms.SponsorView.Donate;
import com.kaligotla.oms.SponsorView.Sponsor;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {
    EditText name, dob, mobile, email, password, confirm_password;
    Spinner whoAmISpinner, genderSpinner, adminRoleSpinner;
    String[] whoAmISpinnerArray = {"Sponsor","Admin"};
    String[] genderSpinnerArray = {"Male", "Female", "Other"};
    String[] adminRoleSpinnerArray = {"Volunteer", "Guardian", "Super_Admin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        whoAmISpinner = findViewById(R.id.whoAmISpinner);
        name = findViewById( R.id.name );
        dob = findViewById( R.id.dob );
        genderSpinner = findViewById(R.id.genderSpinner);
        mobile = findViewById( R.id.mobile );
        email = findViewById( R.id.email );
        password = findViewById( R.id.password );
        confirm_password = findViewById(R.id.confirm_password);
        adminRoleSpinner = findViewById(R.id.adminRoleSpinner);
    }

    @Override
    protected void onResume() {
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        super.onResume();
    }


}