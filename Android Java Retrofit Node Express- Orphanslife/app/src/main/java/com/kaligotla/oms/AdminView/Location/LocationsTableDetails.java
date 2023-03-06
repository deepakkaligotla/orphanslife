package com.kaligotla.oms.AdminView.Location;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.OrphanageActivities.AddOrphanageActivities;
import com.kaligotla.oms.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationsTableDetails extends AppCompatActivity {

    int location_id;
    EditText pincode, area, city, district, state;
    Toolbar sponsor_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_locations_table_details );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        pincode = findViewById( R.id.pincode);
        area = findViewById( R.id.area);
        city = findViewById( R.id.city);
        district = findViewById( R.id.district);
        state = findViewById( R.id.state);

        location_id = getIntent().getIntExtra("location_id",0);
        getLocations(location_id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSupportActionBar( sponsor_toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

    private void getLocations(int location_id) {
        Log.e("inside getLocations",""+location_id);
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create())
                .baseUrl( Constants.BASE_URL)
                .build()
                .create( DBService.class)
                .getLocationByID(location_id)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray array = response.body().get("data").getAsJsonArray();
                        if(array.size()>0){
                            JsonObject jsonObject = array.get(0).getAsJsonObject();
                            pincode.setText(jsonObject.get( "pincode" ).getAsString());
                            area.setText(jsonObject.get( "area" ).getAsString());
                            city.setText(jsonObject.get( "city" ).getAsString());
                            district.setText(jsonObject.get( "district" ).getAsString());
                            state.setText(jsonObject.get( "state" ).getAsString());
                        } else {
                            pincode.setText(null);
                            area.setText(null);
                            city.setText(null);
                            district.setText(null);
                            state.setText(null);
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
            setIntent( new Intent( this, MainActivity.class ) );
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