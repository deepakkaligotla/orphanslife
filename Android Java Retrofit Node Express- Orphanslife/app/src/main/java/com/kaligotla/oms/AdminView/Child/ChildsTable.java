package com.kaligotla.oms.AdminView.Child;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.AdoptiveStatus.AdoptiveStatus;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.CustomDateFormate;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.OrphanageActivities.AddOrphanageActivities;
import com.kaligotla.oms.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChildsTable extends AppCompatActivity {

    RecyclerView ChildsTableRecyclerView;
    List<Child> childList;
    ChildsTableListAdapter childsTableListAdapter;
    MaterialToolbar sponsor_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_child_table );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        ChildsTableRecyclerView = findViewById( R.id.ChildsTableRecyclerView );
    }

    @Override
    protected void onResume() {
        setSupportActionBar( sponsor_toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        super.onResume();
        ChildsTableRecyclerView = findViewById(R.id.ChildsTableRecyclerView);
        childList = new ArrayList<>();
        getChilds();
        childsTableListAdapter = new ChildsTableListAdapter(this, childList);
        ChildsTableRecyclerView.setAdapter(childsTableListAdapter);
        ChildsTableRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        childsTableListAdapter.notifyDataSetChanged();

    }

    public void getChilds() {
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create() )
                .baseUrl( Constants.BASE_URL )
                .build()
                .create( DBService.class )
                .childs( )
                .enqueue( new Callback<JsonObject>() {

                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonArray = response.body().getAsJsonArray( "data" );
                        JsonObject jsonObject;
                        if (jsonArray.size() > 0) {
                            for(int i=0; i<jsonArray.size(); i++) {
                                jsonObject = jsonArray.get(i).getAsJsonObject();
                                Child child = new Child();
                                AdoptiveStatus adoptiveStatus = new AdoptiveStatus();
                                if(!jsonObject.get( "child_id" ).isJsonNull()) {
                                    child.setChild_id( jsonObject.get( "child_id" ).getAsInt() );
                                } else child.setChild_id(0);
                                child.setChild_name(jsonObject.get( "child_name" ).getAsString());
                                if(!jsonObject.get( "child_dob" ).isJsonNull()) {
                                    child.setChild_dob( CustomDateFormate.convert( jsonObject.get( "child_dob" ).getAsString() ) );}
                                else child.setChild_dob(null);
                                if(!jsonObject.get( "child_gender" ).isJsonNull()) {
                                    child.setChild_gender( jsonObject.get( "child_gender" ).getAsString() );
                                } else child.setChild_gender(null);
                                if(!jsonObject.get( "status" ).isJsonNull()) {
                                    adoptiveStatus.setStatus( jsonObject.get( "status" ).getAsString() );
                                    child.setAdoptiveStatus( adoptiveStatus );
                                } else {
                                    child.setAdoptiveStatus( null );
                                }
                                childList.add( child );
                            }
                            childsTableListAdapter.notifyDataSetChanged();
                        }
                        else if(jsonArray.size()==0) {
//                            Toast.makeText( DBHelper.this, "No data found in Sponsors DB", Toast.LENGTH_SHORT ).show();
                        }

                        Log.e( "childList from DB",""+childList );
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e("onFailure",""+childList);
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
            setIntent( new Intent( ChildsTable.this, MainActivity.class ) );
        } else if (item.getTitle().equals( "Logout" )) {
            Toast.makeText( this, "Logout", Toast.LENGTH_SHORT ).show();
            SharedPreferences preferences = getSharedPreferences( "store", MODE_PRIVATE );
            preferences.edit().putBoolean( "guardian_logged_in", false ).commit();
            preferences.edit().putBoolean( "sponsor_logged_in", false ).commit();
            preferences.edit().putBoolean( "volunteer_logged_in", false ).commit();
            preferences.edit().putBoolean( "super_admin_logged_in", false ).commit();
            startActivity( new Intent( ChildsTable.this, MainActivity.class ) );
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