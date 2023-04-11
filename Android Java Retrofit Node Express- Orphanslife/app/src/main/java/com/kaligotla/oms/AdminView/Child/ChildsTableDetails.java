package com.kaligotla.oms.AdminView.Child;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.CustomDateFormate;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.Essentials.ImageLoadTask;
import com.kaligotla.oms.MainActivity;
import com.kaligotla.oms.OrphanageActivities.AddOrphanageActivities;
import com.kaligotla.oms.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChildsTableDetails extends AppCompatActivity {

    int child_id;
    TextInputLayout name, dob, admitted_date, leave_date, mother_name, father_name, mobile, status_id, admin_id;
    Spinner gender;
    ShapeableImageView image;
    MaterialToolbar sponsor_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_childs_table_details );
        sponsor_toolbar = findViewById( R.id.sponsor_toolbar );
        name = findViewById( R.id.name);
        dob = findViewById( R.id.dob);
        gender = findViewById( R.id.gender);
        admitted_date = findViewById( R.id.admitted_date );
        leave_date = findViewById( R.id.leave_date );
        mother_name = findViewById( R.id.mother_name );
        father_name = findViewById( R.id.father_name );
        mobile = findViewById( R.id.mobile);
        image = findViewById( R.id.image );
        status_id = findViewById( R.id.status_id );
        admin_id = findViewById( R.id.admin_id );
        child_id = getIntent().getIntExtra("child_id",0);
        getChild(child_id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSupportActionBar( sponsor_toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

    private void getChild(int child_id) {
        Log.e("inside getChild",""+child_id);
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create())
                .baseUrl( Constants.BASE_URL)
                .build()
                .create( DBService.class)
                .getChildByID(child_id)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        JsonArray jsonData = response.body().get("data").getAsJsonArray();
                        if(jsonData.size()>0){
                            JsonObject jsonObject = jsonData.get(0).getAsJsonObject();
                            Log.e("jsonObject",""+jsonObject);
                            if(!jsonObject.get( "child_name" ).isJsonNull())
                                name.getEditText().setText(jsonObject.get( "child_name" ).getAsString());
                            else name.getEditText().setText(null);
                            if(!jsonObject.get( "child_dob" ).isJsonNull())
                                dob.getEditText().setText( CustomDateFormate.convert( jsonObject.get( "child_dob" ).toString() ) );
                            else dob.getEditText().setText(null);


                            if(!jsonObject.get( "admitted_date" ).isJsonNull())
                                admitted_date.getEditText().setText( CustomDateFormate.convert( jsonObject.get( "admitted_date" ).getAsString() ) );
                            else admitted_date.getEditText().setText(null);
                            if(!jsonObject.get( "leave_date" ).isJsonNull())
                                leave_date.getEditText().setText( CustomDateFormate.convert( jsonObject.get( "leave_date" ).getAsString() ) );
                            else leave_date.getEditText().setText(null);
                            if(!jsonObject.get( "mother_name" ).isJsonNull())
                                mother_name.getEditText().setText( jsonObject.get( "mother_name" ).getAsString() );
                            else mother_name.getEditText().setText(null);
                            if(!jsonObject.get( "father_name" ).isJsonNull())
                                father_name.getEditText().setText( jsonObject.get( "father_name" ).getAsString() );
                            else father_name.getEditText().setText(null);
                            if(!jsonObject.get( "child_mobile" ).isJsonNull())
                                mobile.getEditText().setText( jsonObject.get( "child_mobile" ).getAsString() );
                            else mobile.getEditText().setText(null);
                            if(!jsonObject.get( "child_image" ).isJsonNull())
                                new ImageLoadTask(jsonObject.get( "child_image" ).getAsString(), image).execute();
                            else image.setBackgroundResource( R.drawable.ic_launcher_foreground );
                            if(!jsonObject.get( "status_id" ).isJsonNull())
                                status_id.getEditText().setText( jsonObject.get( "status_id" ).getAsString() );
                            else status_id.getEditText().setText(null);
                            if(!jsonObject.get( "admin_id" ).isJsonNull())
                                admin_id.getEditText().setText( jsonObject.get( "admin_id" ).getAsString() );
                            else admin_id.getEditText().setText(null);

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
            setIntent( new Intent( ChildsTableDetails.this, MainActivity.class ) );
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