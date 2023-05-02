package com.kaligotla.oms.OrphanageActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.AdoptiveStatus.AdoptiveStatus;
import com.kaligotla.oms.AdminView.Location.Location;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.R;
import com.kaligotla.oms.orphanage.Orphanage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrphanageActivitiesTable extends AppCompatActivity {
    RecyclerView OrphanageActivitiesTableRecyclerView;
    List<OrphanageActivities> orphanageActivitiesList;
    OrphanageActivitiesTableListAdapter orphanageActivitiesTableListAdapter;
    Toolbar sponsor_toolbar;
    pl.droidsonroids.gif.GifImageView images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_orphanage_activities_table );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        OrphanageActivitiesTableRecyclerView = findViewById( R.id.OrphanageActivitiesTableRecyclerView );
    }

    @Override
    protected void onResume() {
        setSupportActionBar( sponsor_toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        super.onResume();
        OrphanageActivitiesTableRecyclerView = findViewById(R.id.OrphanageActivitiesTableRecyclerView);
        orphanageActivitiesList = new ArrayList<>();
        getOrphanageActivities();
        orphanageActivitiesTableListAdapter = new OrphanageActivitiesTableListAdapter(this, orphanageActivitiesList);
        OrphanageActivitiesTableRecyclerView.setAdapter(orphanageActivitiesTableListAdapter);
        OrphanageActivitiesTableRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        orphanageActivitiesTableListAdapter.notifyDataSetChanged();

    }

    public void getOrphanageActivities() {
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create() )
                .baseUrl( Constants.BASE_URL )
                .build()
                .create( DBService.class )
                .orphanageActivities(this.getSharedPreferences("store",MODE_PRIVATE).getString("API_Token","") )
                .enqueue( new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonArray = response.body().getAsJsonArray( "data" );
                        JsonObject jsonObject;
                        if (jsonArray.size() > 0) {
                            for(int i=0; i<jsonArray.size(); i++) {
                                jsonObject = jsonArray.get(i).getAsJsonObject();
                                OrphanageActivities orphanageActivities = new OrphanageActivities();
                                AdoptiveStatus adoptiveStatus = new AdoptiveStatus();
                                Orphanage orphanage = new Orphanage();
                                Location location = new Location();
                                if(!jsonObject.get( "event_id" ).isJsonNull()) {
                                    orphanageActivities.setEvent_id( jsonObject.get( "event_id" ).getAsInt() );
                                } else orphanageActivities.setEvent_id(0);
                                if(!jsonObject.get( "orphanage_id" ).isJsonNull()) {
                                    orphanage.setId( jsonObject.get( "orphanage_id" ).getAsInt() );
                                    orphanage.setType( jsonObject.get( "type" ).getAsString() );
                                    orphanage.setAddress( jsonObject.get( "address" ).getAsString() );
                                    location.setId( jsonObject.get( "location_id" ).getAsInt() );
                                    orphanage.setLocation( location );
                                    orphanage.setContact_person( jsonObject.get( "contact_person" ).getAsString() );
                                    orphanage.setMobile( jsonObject.get( "mobile" ).getAsString() );
                                    orphanage.setPhone( jsonObject.get( "phone" ).getAsString() );
                                    orphanage.setEmail( jsonObject.get( "email" ).getAsString() );
                                    orphanage.setIn_home( jsonObject.get( "in_home" ).getAsInt() );
                                    orphanage.setAdoptable( jsonObject.get( "adoptable" ).getAsInt() );
                                    orphanage.setBoys( jsonObject.get( "boys" ).getAsInt() );
                                    orphanage.setGirls( jsonObject.get( "girls" ).getAsInt() );
                                    orphanageActivities.setOrphanage( orphanage );
                                } else orphanageActivities.setOrphanage(null);
                                if(!jsonObject.get( "details" ).isJsonNull()) {
                                    orphanageActivities.setDetails( jsonObject.get( "details" ).getAsString() );}
                                else orphanageActivities.setDetails(null);
                                if(!jsonObject.get( "image_1" ).isJsonNull())
                                    orphanageActivities.setImage_1( jsonObject.get( "image_1" ).getAsString() );
                                if(!jsonObject.get( "image_2" ).isJsonNull())
                                    orphanageActivities.setImage_2( jsonObject.get( "image_2" ).getAsString() );
                                if(!jsonObject.get( "image_3" ).isJsonNull())
                                    orphanageActivities.setImage_3( jsonObject.get( "image_3" ).getAsString() );
                                if(!jsonObject.get( "image_4" ).isJsonNull())
                                    orphanageActivities.setImage_4( jsonObject.get( "image_4" ).getAsString() );
                                if(!jsonObject.get( "image_5" ).isJsonNull())
                                    orphanageActivities.setImage_5( jsonObject.get( "image_5" ).getAsString() );
                                else images.setBackgroundResource( R.drawable.ic_launcher_foreground );
                                orphanageActivitiesList.add( orphanageActivities );
                            }
                            orphanageActivitiesTableListAdapter.notifyDataSetChanged();
                        }
                        else if(jsonArray.size()==0) {
//                            Toast.makeText( DBHelper.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e("onFailure",""+orphanageActivitiesList);
//                        Toast.makeText( DBHelper.this, "DB Connection failed", Toast.LENGTH_SHORT ).show();
                    }
                } );
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
            setIntent( new Intent( OrphanageActivitiesTable.this, MainActivity.class ) );
        } else if (item.getTitle().equals( "Logout" )) {
            Toast.makeText( this, "Logout", Toast.LENGTH_SHORT ).show();
            SharedPreferences preferences = getSharedPreferences( "store", MODE_PRIVATE );
            preferences.edit().putBoolean( "guardian_logged_in", false ).commit();
            preferences.edit().putBoolean( "sponsor_logged_in", false ).commit();
            preferences.edit().putBoolean( "volunteer_logged_in", false ).commit();
            preferences.edit().putBoolean( "super_admin_logged_in", false ).commit();
            startActivity( new Intent( OrphanageActivitiesTable.this, MainActivity.class ) );
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