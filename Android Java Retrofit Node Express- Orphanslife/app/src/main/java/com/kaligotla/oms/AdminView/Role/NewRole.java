package com.kaligotla.oms.AdminView.Role;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.AdoptRequest.NewAdoptRequest;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewRole extends AppCompatActivity {
    TextInputLayout role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_role);
        role = findViewById(R.id.role);
    }

    public void newRole(View view) {
        Role addNewRole = new Role();
        addNewRole.setRole(role.getEditText().getText().toString());

        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create() )
                .baseUrl( Constants.BASE_URL )
                .build()
                .create( DBService.class )
                .newRole(addNewRole)
                .enqueue( new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject data = response.body().getAsJsonObject( "data" );
                        if (data.get("affectedRows").getAsInt()==1) {
                            Toast.makeText(NewRole.this,"Created a new role ID "+data.get("insertId")+" in Database",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            Toast.makeText(NewRole.this,"Unable to insert new role into db, please try again",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(NewRole.this,"DB connection failed, please try after sometime",Toast.LENGTH_SHORT).show();
                    }
                } );

    }
}