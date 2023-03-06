package com.kaligotla.oms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
    EditText sponsor_first_name, sponsor_last_name, sponsor_phone, sponsor_email, sponsor_password, confirm_password, dobtext;
    Toolbar sponsor_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        sponsor_first_name = findViewById( R.id.sponsor_first_name );
        sponsor_last_name = findViewById( R.id.sponsor_last_name );
        sponsor_phone = findViewById( R.id.sponsor_phone );
        sponsor_email = findViewById( R.id.sponsor_email );
        sponsor_password = findViewById( R.id.sponsor_password );
        confirm_password = findViewById( R.id.confirm_password );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        dobtext = findViewById( R.id.dobtext );
    }

    @Override
    protected void onResume() {
        setSupportActionBar( sponsor_toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        super.onResume();
    }

    public void signup(View view) {
        validateData();
    }

    //using retrofit for calling API
    public void registerSponsor(Sponsor sponsor) {
        new Retrofit.Builder() // Creating new Retrofit Builder object
                .addConverterFactory( GsonConverterFactory.create() ) //Adding the convertor
                .baseUrl( Constants.BASE_URL ) //Adding the base url
                .build() // creating the retrofit object
                .create( DBService.class )
                .register( sponsor )
                .enqueue( new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.e( "Adding", sponsor.toString() );
                        Toast.makeText( Register.this, "User Registerd Successfully", Toast.LENGTH_SHORT ).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText( Register.this, "haha.. You failed", Toast.LENGTH_SHORT ).show();
                    }
                } );
    }

    // For data validation
    public void validateData() {
        Sponsor sponsor = new Sponsor( sponsor_first_name.getText().toString() + " " + sponsor_last_name.getText().toString(), sponsor_email.getText().toString(), sponsor_password.getText().toString(), sponsor_phone.getText().toString() );
        String confirmPassword = confirm_password.getText().toString();
        if (!sponsor.getName().equals( "" )) {
            if (!sponsor.getEmail().equals( "" )) {
                if (!sponsor.getMobile().equals( "" )) {
                    if (!sponsor.getPassword().equals( "" )) {
                        if (sponsor.getPassword().equals( confirmPassword )) {
                            Toast.makeText( Register.this, "Checking if email already exists in DB", Toast.LENGTH_SHORT ).show();
                            new Retrofit.Builder()
                                    .addConverterFactory( GsonConverterFactory.create() )
                                    .baseUrl( Constants.BASE_URL )
                                    .build()
                                    .create( DBService.class )
                                    .register( sponsor )
                                    .enqueue( new Callback<JsonObject>() {
                                        @Override
                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                            JsonArray jsonArray = response.body().getAsJsonArray( "data" );
                                            if (jsonArray.size() > 0) {
                                                for (int i = 0; i < jsonArray.size(); i++) {
                                                    JsonObject jsonObject = jsonArray.get( i ).getAsJsonObject();
                                                    String email = jsonObject.get( "email" ).getAsString();
                                                    if (!email.equals( sponsor.getEmail() )) {
                                                        Log.e( "sending to register", sponsor.toString() );
                                                        registerSponsor( sponsor );
                                                    } else {
                                                        Toast.makeText( Register.this, "Email ID already exists, please login", Toast.LENGTH_LONG ).show();
                                                        startActivity( new Intent( Register.this, MainActivity.class ) );
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<JsonObject> call, Throwable t) {
                                        }
                                    } );
                        } else
                            Toast.makeText( this, "Passwords does not match", Toast.LENGTH_SHORT ).show();
                    } else
                        Toast.makeText( this, "Password cannot be empty", Toast.LENGTH_SHORT ).show();
                } else Toast.makeText( this, "Mobile cannot be empty", Toast.LENGTH_SHORT ).show();
            } else Toast.makeText( this, "Email cannot be empty", Toast.LENGTH_SHORT ).show();
        } else Toast.makeText( this, "Name cannot be empty", Toast.LENGTH_SHORT ).show();
    }


    public void dob(View view) {
        Calendar mcurrentDate = Calendar.getInstance();
        int year = mcurrentDate.get(Calendar.YEAR);
        int month = mcurrentDate.get(Calendar.MONTH);
        int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(Register.this, (datepicker, selectedYear, selectedMonth, selectedDay) -> {
            dobtext.setText(selectedDay + "/" + (selectedMonth+1) + "/" + selectedYear);
        },year, month, day);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }
    public void cancel(View view) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.sponsor_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            item.setIcon( R.drawable.back );
            item.setVisible( true );
            finish();
        } else if (item.getTitle().equals( "Home" )) {
            setIntent( new Intent( Register.this, MainActivity.class ) );
        } else if (item.getTitle().equals( "Donate" )) {
            startActivity( new Intent( Register.this, Donate.class ) );
        } else if (item.getTitle().equals( "Adopt" )) {
            startActivity( new Intent( Register.this, Adopt.class ) );
        }
        return super.onOptionsItemSelected( item );
    }

    public void sign_in(View view) {
        startActivity( new Intent( Register.this, MainActivity.class ) );
    }
}