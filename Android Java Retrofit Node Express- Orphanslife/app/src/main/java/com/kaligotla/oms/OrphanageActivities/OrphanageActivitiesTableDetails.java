package com.kaligotla.oms.OrphanageActivities;

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
import com.kaligotla.oms.AdminView.Location.Location;
import com.kaligotla.oms.AdminView.Role.Role;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.Essentials.ImageLoadTask;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.R;
import com.kaligotla.oms.orphanage.Orphanage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrphanageActivitiesTableDetails extends AppCompatActivity {

    int aid;
    EditText orphanage, details;
    pl.droidsonroids.gif.GifImageView image_1, image_2, image_3, image_4, image_5;
    Toolbar sponsor_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_orphanage_activities_table_details );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        orphanage = findViewById( R.id.orphanage );
        details = findViewById( R.id.details );
        image_1 = findViewById( R.id.image_1 );
        image_2 = findViewById( R.id.image_2 );
        image_3 = findViewById( R.id.image_3 );
        image_4 = findViewById( R.id.image_4 );
        image_5 = findViewById( R.id.image_5 );
        aid = getIntent().getIntExtra("aid",0);
        getAdmin(aid);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSupportActionBar( sponsor_toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

    private void getAdmin(int aid) {
        Log.e("inside getAdmin",""+aid);
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create())
                .baseUrl( Constants.BASE_URL)
                .build()
                .create( DBService.class)
                .getOrphanageActivitiesByID(aid)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Location l = new Location();
                        Role r = new Role();
                        Orphanage o = new Orphanage();
                        JsonArray array = response.body().get("data").getAsJsonArray();
                        if(array.size()>0){
                            JsonObject jsonObject = array.get(0).getAsJsonObject();
                            Log.e("jsonObject",""+jsonObject);
                            if(!jsonObject.get( "orphanage_id" ).isJsonNull())
                                orphanage.setText(jsonObject.get( "orphanage_id" ).getAsString());
                            else orphanage.setText(null);
                            if(!jsonObject.get( "details" ).isJsonNull())
                                details.setText( jsonObject.get( "details" ).toString() );
                            else details.setText(null);
                            if(!jsonObject.get( "image_1" ).isJsonNull())
                                new ImageLoadTask(jsonObject.get( "image_1" ).getAsString(), image_1).execute();
                            else image_1.setBackgroundResource( R.drawable.ic_launcher_foreground );
                            if(!jsonObject.get( "image_2" ).isJsonNull())
                                new ImageLoadTask(jsonObject.get( "image_2" ).getAsString(), image_2).execute();
                            else image_2.setBackgroundResource( R.drawable.ic_launcher_foreground );
                            if(!jsonObject.get( "image_3" ).isJsonNull())
                                new ImageLoadTask(jsonObject.get( "image_3" ).getAsString(), image_3).execute();
                            else image_3.setBackgroundResource( R.drawable.ic_launcher_foreground );
                            if(!jsonObject.get( "image_4" ).isJsonNull())
                                new ImageLoadTask(jsonObject.get( "image_4" ).getAsString(), image_4).execute();
                            else image_4.setBackgroundResource( R.drawable.ic_launcher_foreground );
                            if(!jsonObject.get( "image_5" ).isJsonNull())
                                new ImageLoadTask(jsonObject.get( "image_5" ).getAsString(), image_5).execute();
                            else image_5.setBackgroundResource( R.drawable.ic_launcher_foreground );
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
            setIntent( new Intent( OrphanageActivitiesTableDetails.this, MainActivity.class ) );
        } else if (item.getTitle().equals( "Logout" )) {
            Toast.makeText( this, "Logout", Toast.LENGTH_SHORT ).show();
            SharedPreferences preferences = getSharedPreferences( "store", MODE_PRIVATE );
            preferences.edit().putBoolean( "guardian_logged_in", false ).commit();
            preferences.edit().putBoolean( "sponsor_logged_in", false ).commit();
            preferences.edit().putBoolean( "volunteer_logged_in", false ).commit();
            preferences.edit().putBoolean( "super_admin_logged_in", false ).commit();
            startActivity( new Intent( OrphanageActivitiesTableDetails.this, MainActivity.class ) );
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