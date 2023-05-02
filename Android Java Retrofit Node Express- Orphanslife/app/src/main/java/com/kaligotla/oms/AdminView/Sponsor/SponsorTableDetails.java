package com.kaligotla.oms.AdminView.Sponsor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
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

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SponsorTableDetails extends AppCompatActivity {
    int sid;
    TextInputLayout name, dob, govt_id, mobile, email, password, user_address, location, spouce_name, spouce_dob, spouce_govt_id, spouce_mobile, donation_id;
    TextInputEditText dobEditText;
    Spinner genderSpinner, govtIdTypeSpinner, maritalStatusSpinner, spouceGovtIdTypeSpinner;
    ShapeableImageView user_image, spouce_image;
    MaterialToolbar sponsor_toolbar;
    String[] genderSpinnerArray = {"Male", "Female", "Other"};
    String[] govtIdTypeSpinnerArray = {"Aadhaar", "Voter", "Driving License", "PAN", "Passport"};
    String[] maritalStatusSpinnerArray = {"Single", "Married", "Divorced", "Break_UP", "Widowed"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sponsor_table_details );
        name = findViewById( R.id.name);
        dob = findViewById( R.id.dob);
        dobEditText = findViewById(R.id.dobEditText);
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
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SponsorTableDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dob.getEditText().setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });
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
                            if(!jsonObject.get( "sponsor_location_id" ).isJsonNull()) {
                                l.setId( jsonObject.get( "sponsor_location_id" ).getAsInt() );
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

                            name.getEditText().setText(jsonObject.get( "sponsor_name" ).getAsString());
                            if(!jsonObject.get( "sponsor_dob" ).isJsonNull())
                                dob.getEditText().setText(CustomDateFormate.convert(jsonObject.get( "sponsor_dob" ).getAsString()));
                            else dob.getEditText().setText(null);

                            if (!jsonObject.get("sponsor_gender").isJsonNull()) {
                                String existingGender = jsonObject.get("sponsor_gender").getAsString();
                                ArrayAdapter arrayAdapter = new ArrayAdapter(SponsorTableDetails.this, android.R.layout.simple_list_item_1, genderSpinnerArray);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                genderSpinner.setAdapter(arrayAdapter);
                                if (existingGender != null) {
                                    int spinnerPosition = arrayAdapter.getPosition(existingGender);
                                    genderSpinner.setSelection(spinnerPosition);
                                } else genderSpinner.setSelection(1);
                            } else genderSpinner.setSelection(1);

                            if (!jsonObject.get("sponsor_govt_id_type").isJsonNull()) {
                                String existingGovtIdType = jsonObject.get("sponsor_govt_id_type").getAsString();
                                ArrayAdapter arrayAdapter = new ArrayAdapter(SponsorTableDetails.this, android.R.layout.simple_list_item_1, govtIdTypeSpinnerArray);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                govtIdTypeSpinner.setAdapter(arrayAdapter);
                                if (existingGovtIdType != null) {
                                    int spinnerPosition = arrayAdapter.getPosition(existingGovtIdType);
                                    govtIdTypeSpinner.setSelection(spinnerPosition);
                                } else govtIdTypeSpinner.setSelection(1);
                            } else govtIdTypeSpinner.setSelection(1);

                            if(!jsonObject.get( "sponsor_govt_id" ).isJsonNull())
                                govt_id.getEditText().setText( jsonObject.get( "sponsor_govt_id" ).getAsString() );
                            else govt_id.getEditText().setText(null);
                            if(!jsonObject.get( "sponsor_mobile" ).isJsonNull())
                                mobile.getEditText().setText( jsonObject.get( "sponsor_mobile" ).getAsString() );
                            else mobile.getEditText().setText(null);
                            email.getEditText().setText(jsonObject.get( "sponsor_email" ).getAsString());
                            password.getEditText().setText(jsonObject.get( "sponsor_password" ).getAsString());

                            if (!jsonObject.get("marital_status").isJsonNull()) {
                                String existingMaritalStatus = jsonObject.get("marital_status").getAsString();
                                ArrayAdapter arrayAdapter = new ArrayAdapter(SponsorTableDetails.this, android.R.layout.simple_list_item_1, maritalStatusSpinnerArray);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                maritalStatusSpinner.setAdapter(arrayAdapter);
                                if (existingMaritalStatus != null) {
                                    int spinnerPosition = arrayAdapter.getPosition(existingMaritalStatus);
                                    maritalStatusSpinner.setSelection(spinnerPosition);
                                } else maritalStatusSpinner.setSelection(1);
                            } else maritalStatusSpinner.setSelection(1);

                            if(!jsonObject.get( "sponsor_image" ).isJsonNull()) {
                                new ImageLoadTask(jsonObject.get( "sponsor_image" ).getAsString(), user_image).execute();
                            } else user_image.setImageResource( R.drawable.ic_launcher_foreground );
                            if(!jsonObject.get( "sponsor_address" ).isJsonNull())
                                user_address.getEditText().setText( jsonObject.get( "sponsor_address" ).getAsString() );
                            else user_address.getEditText().setText(null);
                            if(!jsonObject.get( "spouce_name" ).isJsonNull())
                                spouce_name.getEditText().setText( jsonObject.get( "spouce_name" ).getAsString() );
                            else spouce_name.getEditText().setText(null);
                            if(!jsonObject.get( "spouce_dob" ).isJsonNull())
                                spouce_dob.getEditText().setText( CustomDateFormate.convert( jsonObject.get( "spouce_dob" ).getAsString() ) );
                            else spouce_dob.getEditText().setText(null);

                            if (!jsonObject.get("spouce_govt_id_type").isJsonNull()) {
                                String existingGovtIdType = jsonObject.get("spouce_govt_id_type").getAsString();
                                ArrayAdapter arrayAdapter = new ArrayAdapter(SponsorTableDetails.this, android.R.layout.simple_list_item_1, govtIdTypeSpinnerArray);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spouceGovtIdTypeSpinner.setAdapter(arrayAdapter);
                                if (existingGovtIdType != null) {
                                    int spinnerPosition = arrayAdapter.getPosition(existingGovtIdType);
                                    spouceGovtIdTypeSpinner.setSelection(spinnerPosition);
                                } else spouceGovtIdTypeSpinner.setSelection(1);
                            } else spouceGovtIdTypeSpinner.setSelection(1);

                            if(!jsonObject.get( "spouce_govt_id" ).isJsonNull())
                                spouce_govt_id.getEditText().setText( jsonObject.get( "spouce_govt_id" ).getAsString());
                            else spouce_govt_id.getEditText().setText(null);
                            if(!jsonObject.get( "spouce_mobile" ).isJsonNull())
                                spouce_mobile.getEditText().setText( jsonObject.get( "spouce_mobile" ).getAsString());
                            else spouce_mobile.getEditText().setText(null);

                            if(!jsonObject.get( "spouce_image" ).isJsonNull()) {
                                new ImageLoadTask(jsonObject.get( "spouce_image" ).getAsString(), spouce_image).execute();
                            } else spouce_image.setImageResource( R.drawable.ic_launcher_foreground );
                            if(!jsonObject.get( "donation_id" ).isJsonNull())
                                donation_id.getEditText().setText( ""+jsonObject.get( "donation_id" ).getAsInt());
                            else donation_id.getEditText().setText(null);
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                    }
                });
    }

    public void update(View view) {
        Sponsor updateSponsor = new Sponsor();
        updateSponsor.setSponsor_id(getIntent().getIntExtra("sid",0));
        updateSponsor.setSponsor_name(name.getEditText().getText().toString());
        updateSponsor.setSponsor_dob(dob.getEditText().getText().toString());
        updateSponsor.setSponsor_gender(genderSpinner.getSelectedItem().toString());
        updateSponsor.setSponsor_govt_id_type(govtIdTypeSpinner.getSelectedItem().toString());
        updateSponsor.setSponsor_govt_id(govt_id.getEditText().getText().toString());
        updateSponsor.setSponsor_mobile(mobile.getEditText().getText().toString());
        updateSponsor.setSponsor_email(email.getEditText().getText().toString());
        updateSponsor.setSponsor_password(password.getEditText().getText().toString());
        updateSponsor.setMarital_status(maritalStatusSpinner.getSelectedItem().toString());
        updateSponsor.setSponsor_address(user_address.getEditText().getText().toString());
        //user_image, location, spouce_image, Donation
        updateSponsor.setSpouce_name(spouce_name.getEditText().getText().toString());
        updateSponsor.setSpouce_dob(spouce_dob.getEditText().getText().toString());
        updateSponsor.setSpouce_govt_id_type(spouceGovtIdTypeSpinner.getSelectedItem().toString());
        updateSponsor.setSpouce_govt_id(spouce_govt_id.getEditText().getText().toString());
        updateSponsor.setSpouce_mobile(spouce_mobile.getEditText().getText().toString());
        Log.e("updateSponsor",updateSponsor.toString());

        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create())
                .baseUrl( Constants.BASE_URL)
                .build()
                .create( DBService.class)
                .updateSponsor(this.getSharedPreferences("store", MODE_PRIVATE).getString("API_Token",""), sid, updateSponsor)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject data = response.body().getAsJsonObject( "data" );
                        if (data.get("affectedRows").getAsInt()==1) {
                            Toast.makeText(SponsorTableDetails.this,"Updated Sponsor ID "+sid+" in Database",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(SponsorTableDetails.this,"Unable to update sponsor, please try again",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(SponsorTableDetails.this,"DB Connection Failed, please try after sometime",Toast.LENGTH_SHORT).show();
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