package com.kaligotla.oms.orphanage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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
import com.kaligotla.oms.AdminView.Location.Location;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.OrphanageActivities.AddOrphanageActivities;
import com.kaligotla.oms.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OrphanagesTableDetails extends AppCompatActivity {
    int sid;
    EditText type, address, location, contact_person, mobile, phone, email, in_home, adoptable, boys, girls;
    pl.droidsonroids.gif.GifImageView orphanage_image;
    Toolbar sponsor_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_orphanages_table_details );
        type = findViewById( R.id.type);
        address = findViewById( R.id.address);
        location = findViewById( R.id.location );
        contact_person = findViewById( R.id.contact_person);
        mobile = findViewById( R.id.mobile );
        phone = findViewById( R.id.phone );
        email = findViewById( R.id.email );
        in_home = findViewById( R.id.in_home );
        adoptable = findViewById( R.id.adoptable );
        boys = findViewById( R.id.boys );
        girls = findViewById( R.id.girls );
        orphanage_image = findViewById( R.id.orphanage_image );
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
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create())
                .baseUrl( Constants.SPONSOR_URL)
                .build()
                .create( DBService.class)
                .getOrphanageByID(getSharedPreferences("store", MODE_PRIVATE).getString("API_Token",""), sid)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Location l = new Location();
                        JsonArray array = response.body().get("data").getAsJsonArray();
                        if(array.size()>0){
                            JsonObject jsonObject = array.get(0).getAsJsonObject();
                            if(!jsonObject.get( "location_id" ).isJsonNull()) {
                                l.setId( jsonObject.get( "location_id" ).getAsInt() );
                                if(!jsonObject.get( "pincode" ).isJsonNull()){
                                    l.setPincode( jsonObject.get( "pincode" ).getAsInt());
                                } else l.setPincode( 0 );
                                l.setArea( jsonObject.get( "area" ).getAsString() );
                                l.setCity( jsonObject.get( "city" ).getAsString() );
                                l.setDistrict( jsonObject.get( "district" ).getAsString() );
                                l.setState( jsonObject.get( "state" ).getAsString() );
                                location.setText( l.toString() );
                            } else {
                                location.setText(null);
                            }

                            type.setText(jsonObject.get( "type" ).getAsString());
                            if(!jsonObject.get( "address" ).isJsonNull())
                                address.setText(jsonObject.get( "address" ).toString() );
                            else address.setText(null);
                            if(!jsonObject.get( "contact_person" ).isJsonNull())
                                contact_person.setText( jsonObject.get( "contact_person" ).getAsString() );
                            else contact_person.setText(null);
                            if(!jsonObject.get( "mobile" ).isJsonNull())
                                mobile.setText( jsonObject.get( "mobile" ).getAsString() );
                            else mobile.setText(null);
                            if(!jsonObject.get( "phone" ).isJsonNull())
                                phone.setText( jsonObject.get( "phone" ).getAsString() );
                            else phone.setText(null);
                            email.setText(jsonObject.get( "email" ).getAsString());
                            if(!jsonObject.get( "in_home" ).isJsonNull())
                                in_home.setText( ""+jsonObject.get( "in_home" ).getAsString() );
                            else in_home.setText(null);
                            if(!jsonObject.get( "adoptable" ).isJsonNull())
                                adoptable.setText(""+ jsonObject.get( "adoptable" ).toString() );
                            else adoptable.setText(null);
                            if(!jsonObject.get( "boys" ).isJsonNull())
                                boys.setText( ""+jsonObject.get( "boys" ).getAsString() );
                            else boys.setText(null);
                            if(!jsonObject.get( "girls" ).isJsonNull())
                                girls.setText( ""+jsonObject.get( "girls" ).toString() );
                            else girls.setText(null);
                            if(!jsonObject.get( "orphanage_image" ).isJsonNull()) {
                                orphanage_image.setImageURI( Uri.parse( jsonObject.get( "orphanage_image" ).toString() ) );
                            } else orphanage_image.setBackgroundResource( R.drawable.back );
                        }
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
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            item.setIcon( R.drawable.back );
            item.setVisible( true );
            finish();
        } else if (item.getTitle().equals( "Home" )) {
            setIntent( new Intent( OrphanagesTableDetails.this, MainActivity.class ) );
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