package com.kaligotla.oms.AdminView.Sponsor;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.Location.Location;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.CustomDateFormate;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.Essentials.ImageLoadTask;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.OrphanageActivities.AddOrphanageActivities;
import com.kaligotla.oms.R;
import com.kaligotla.oms.SponsorView.Sponsor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SponsorTableDetails extends AppCompatActivity {
    int sid;
    TextInputLayout name, dob, govt_id, mobile, email, password, user_address, location, spouce_name, spouce_dob, spouce_govt_id, spouce_mobile, donation_id;
    Spinner genderSpinner, govtIdTypeSpinner, maritalStatusSpinner, spouceGovtIdTypeSpinner;
    ShapeableImageView user_image, spouce_image;
    MaterialToolbar sponsor_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sponsor_table_details );
        name = findViewById( R.id.name);
        dob = findViewById( R.id.dob);
        genderSpinner = findViewById( R.id.genderSpinner);
        govtIdTypeSpinner = findViewById( R.id.govtIdTypeSpinner );
        govt_id = findViewById( R.id.govt_id );
        mobile = findViewById( R.id.mobile );
        email = findViewById( R.id.email );
        password = findViewById( R.id.password );
        maritalStatusSpinner = findViewById( R.id.maritalStatusSpinner );
        user_image = findViewById( R.id.user_image );
        user_address = findViewById( R.id.user_address );
        location = findViewById( R.id.location );
        spouce_name = findViewById( R.id.spouce_name );
        spouce_dob = findViewById( R.id.spouce_dob );
        spouceGovtIdTypeSpinner = findViewById( R.id.spouceGovtIdTypeSpinner );
        spouce_govt_id = findViewById( R.id.spouce_govt_id );
        spouce_mobile = findViewById( R.id.spouce_mobile );
        spouce_image = findViewById( R.id.spouce_image );
        donation_id = findViewById( R.id.donation_id );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        sid = getIntent().getIntExtra("sid",0);
        getSponsor(sid);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSupportActionBar( sponsor_toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

    private void getSponsor(int sid) {
        Log.e("inside getSponsor",""+sid);
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create())
                .baseUrl( Constants.BASE_URL)
                .build()
                .create( DBService.class)
                .getSponsorByID(sid)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Location l = new Location();
                        JsonArray array = response.body().get("data").getAsJsonArray();
                        if(array.size()>0){
                            JsonObject jsonObject = array.get(0).getAsJsonObject();
                            Log.e("jsonObject",""+jsonObject);
                            if(!jsonObject.get( "location_id" ).isJsonNull()) {
                                l.setId( jsonObject.get( "location_id" ).getAsInt() );
                                if(!jsonObject.get( "pincode" ).isJsonNull()){
                                    l.setPincode( jsonObject.get( "pincode" ).getAsInt());
                                } else l.setPincode( 0 );
                                l.setArea( jsonObject.get( "area" ).getAsString() );
                                l.setCity( jsonObject.get( "city" ).getAsString() );
                                l.setDistrict( jsonObject.get( "district" ).getAsString() );
                                l.setState( jsonObject.get( "state" ).getAsString() );
                                location.getEditText().setText( l.toString() );
                            } else {
                                location.getEditText().setText(null);
                            }

                            name.getEditText().setText(jsonObject.get( "name" ).getAsString());
                            if(!jsonObject.get( "dob" ).isJsonNull())
                                dob.getEditText().setText(CustomDateFormate.convert( jsonObject.get( "dob" ).toString() ) );
                            else dob.getEditText().setText(null);

                            if(!jsonObject.get( "govt_id" ).isJsonNull())
                                govt_id.getEditText().setText( jsonObject.get( "govt_id" ).getAsString() );
                            else govt_id.getEditText().setText(null);
                            if(!jsonObject.get( "mobile" ).isJsonNull())
                                mobile.getEditText().setText( jsonObject.get( "mobile" ).getAsString() );
                            else mobile.getEditText().setText(null);
                            email.getEditText().setText(jsonObject.get( "email" ).getAsString());
                            password.getEditText().setText(jsonObject.get( "password" ).getAsString());

                            if(!jsonObject.get( "user_image" ).isJsonNull()) {
                                new ImageLoadTask(jsonObject.get( "user_image" ).getAsString(), user_image).execute();
                            } else user_image.setImageResource( R.drawable.ic_launcher_foreground );
                            if(!jsonObject.get( "user_address" ).isJsonNull())
                                user_address.getEditText().setText( jsonObject.get( "user_address" ).getAsString() );
                            else user_address.getEditText().setText(null);
                            if(!jsonObject.get( "spouce_name" ).isJsonNull())
                                spouce_name.getEditText().setText( jsonObject.get( "spouce_name" ).toString() );
                            else spouce_name.getEditText().setText(null);
                            if(!jsonObject.get( "spouce_dob" ).isJsonNull())
                                spouce_dob.getEditText().setText( CustomDateFormate.convert( jsonObject.get( "spouce_dob" ).toString() ) );
                            else spouce_dob.getEditText().setText(null);

                            if(!jsonObject.get( "spouce_govt_id" ).isJsonNull())
                                spouce_govt_id.getEditText().setText( jsonObject.get( "spouce_govt_id" ).toString() );
                            else spouce_govt_id.getEditText().setText(null);
                            if(!jsonObject.get( "spouce_mobile" ).isJsonNull())
                                spouce_mobile.getEditText().setText( jsonObject.get( "spouce_mobile" ).toString() );
                            else spouce_mobile.getEditText().setText(null);

                            if(!jsonObject.get( "spouce_image" ).isJsonNull()) {
                                new ImageLoadTask(jsonObject.get( "spouce_image" ).getAsString(), spouce_image).execute();
                            } else spouce_image.setImageResource( R.drawable.ic_launcher_foreground );
                            if(!jsonObject.get( "donation_id" ).isJsonNull())
                                donation_id.getEditText().setText( ""+jsonObject.get( "donation_id" ).toString() );
                            else donation_id.getEditText().setText(null);
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                    }
                });
    }

    public void save(View view) {
        Sponsor updateSponsor = new Sponsor();
        updateSponsor.setId(getIntent().getIntExtra("sid",0));
        updateSponsor.setName(name.getEditText().getText().toString());
        updateSponsor.setDob(dob.getEditText().getText().toString());
        updateSponsor.setGovt_id(govt_id.getEditText().getText().toString());
        updateSponsor.setMobile(mobile.getEditText().getText().toString());
        updateSponsor.setEmail(email.getEditText().getText().toString());
        updateSponsor.setPassword(password.getEditText().getText().toString());
        updateSponsor.setUser_address(user_address.getEditText().getText().toString());
        //user_image, location, spouce_image, Donation
        updateSponsor.setSpouce_name(spouce_name.getEditText().getText().toString());
        updateSponsor.setSpouce_dob(spouce_dob.getEditText().getText().toString());
        updateSponsor.setSpouce_govt_id(spouce_govt_id.getEditText().getText().toString());
        updateSponsor.setSpouce_mobile(spouce_mobile.getEditText().getText().toString());

        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create())
                .baseUrl( Constants.BASE_URL)
                .build()
                .create( DBService.class)
                .updateSponsor(sid, updateSponsor)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.sponsor_menu, menu );
        menu.removeItem( R.id.login );
        menu.removeItem( R.id.Home );
        menu.removeItem( R.id.donate );
        menu.removeItem( R.id.adopt );
        menu.removeItem( R.id.add );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            item.setIcon( R.drawable.back );
            item.setVisible( true );
            finish();
        } else if (item.getTitle().equals( "Home" )) {
            setIntent( new Intent( SponsorTableDetails.this, MainActivity.class ) );
        } else if (item.getTitle().equals( "Logout" )) {
            Toast.makeText( this, "Logout", Toast.LENGTH_SHORT ).show();
            SharedPreferences preferences = getSharedPreferences( "store", MODE_PRIVATE );
            preferences.edit().putBoolean( "guardian_logged_in", false ).commit();
            preferences.edit().putBoolean( "sponsor_logged_in", false ).commit();
            preferences.edit().putBoolean( "volunteer_logged_in", false ).commit();
            preferences.edit().putBoolean( "super_admin_logged_in", false ).commit();
            startActivity( new Intent( this, MainActivity.class ) );
        } else if (item.getTitle().equals("Add")) {
            startActivity( new Intent( this, AddOrphanageActivities.class ) );
        } else {
            finish();
            finish();
            finish();
        }
        return super.onOptionsItemSelected( item );
    }
}