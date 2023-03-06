package com.kaligotla.oms;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.AdminHome;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.GuardianView.GuardianHome;
import com.kaligotla.oms.SponsorView.SponsorHome;
import com.kaligotla.oms.VolunteerView.VolunteerHome;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {

    TextInputLayout login_username, login_password;
    MaterialCheckBox remember_me;
    GifImageView kids_jumping;
    MaterialButton sign_in;
    MaterialTextView forgotPassword, register;
    Cred cred;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = requireArguments();
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        String value = bundle.getString("key");
        login_username = rootView.findViewById(R.id.login_username);
        login_password = rootView.findViewById( R.id.login_password );
        remember_me = rootView.findViewById( R.id.remember_me );
        kids_jumping = rootView.findViewById( R.id.kids_jumping );
        sign_in = rootView.findViewById(R.id.sign_in);
        forgotPassword = rootView.findViewById(R.id.forgotPassword);
        register = rootView.findViewById(R.id.register);

        SharedPreferences preferences = this.getActivity().getSharedPreferences( "sponsor_logged_in", MODE_PRIVATE );
        if (this.getActivity().getSharedPreferences( "store", MODE_PRIVATE ).getBoolean( "sponsor_logged_in", false )) {
            Log.e("Sponsor logged in",""+this.getActivity().getSharedPreferences( "store", MODE_PRIVATE ).getBoolean( "sponsor_logged_in", false ));
            startActivity( new Intent( getActivity(), SponsorHome.class ) );
            getActivity().finish();
        } else if(this.getActivity().getSharedPreferences( "store", MODE_PRIVATE ).getBoolean( "super_admin_logged_in", false )) {
            Log.e("Super Admin logged in",""+this.getActivity().getSharedPreferences( "store", MODE_PRIVATE ).getBoolean( "super_admin_logged_in", false ));
            startActivity( new Intent( getActivity(), AdminHome.class ) );
            getActivity().finish();
        } else if(this.getActivity().getSharedPreferences( "store", MODE_PRIVATE ).getBoolean( "guardian_logged_in", false )) {
            Log.e("Guardian logged in",""+this.getActivity().getSharedPreferences( "store", MODE_PRIVATE ).getBoolean( "guardian_logged_in", false ));
            startActivity( new Intent( getActivity(), GuardianHome.class ) );
            getActivity().finish();
        } else if(this.getActivity().getSharedPreferences( "store", MODE_PRIVATE ).getBoolean( "volunteer_logged_in", false )) {
            Log.e("Volunteer logged in",""+this.getActivity().getSharedPreferences( "store", MODE_PRIVATE ).getBoolean( "volunteer_logged_in", false ));
            startActivity( new Intent( getActivity(), VolunteerHome.class ) );
            getActivity().finish();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        //kids_jumping.animate();
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_in();
            }
        });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void sign_in() {
        cred = validateData();
        if (cred != null) {
            signinSponsor( cred );
        }
    }

    public void signinAdmin(Cred cred) {

        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create() )
                .baseUrl( Constants.BASE_URL )
                .build()
                .create( DBService.class )
                .adminlogin( cred )
                .enqueue( new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonArray = response.body().getAsJsonArray( "data" );
                        JsonObject jsonObject;
                        Log.e("cred",""+jsonArray);
                        if (jsonArray.size() > 0) {
                            jsonObject = jsonArray.get( 0 ).getAsJsonObject();
                            int admin_id = jsonObject.get( "id" ).getAsInt();
                            String role = jsonObject.get( "role" ).getAsString();
                            saveData( admin_id, role );
                            Toast.makeText( getActivity(), "Admin Login successfull", Toast.LENGTH_SHORT ).show();
                            if(role.equals( "Super_Admin" )) {
                                startActivity( new Intent( getActivity(), AdminHome.class ) );
                            } else if(role.equals( "Volunteer" )) {
                                startActivity( new Intent( getActivity(), VolunteerHome.class ) );
                            } else if(role.equals( "Guardian" )) {
                                startActivity( new Intent( getActivity(), GuardianHome.class ) );
                            }
                            getActivity().finish();
                        }
                        else if(jsonArray.size()==0) {
                            Toast.makeText( getActivity(), "No account found, kindly check email & password or Register with us", Toast.LENGTH_SHORT ).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText( getActivity(), "DB Connection failed", Toast.LENGTH_SHORT ).show();
                    }
                } );
    }

    public void signinSponsor(Cred cred) {
        new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create() )
                .baseUrl( Constants.BASE_URL )
                .build()
                .create( DBService.class )
                .sponsorlogin( cred )
                .enqueue( new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonArray = response.body().getAsJsonArray( "data" );
                        JsonObject jsonObject;
                        Log.e("cred",""+jsonArray);
                        if (jsonArray.size() > 0) {
                            jsonObject = jsonArray.get( 0 ).getAsJsonObject();
                            int sponsor_id = jsonObject.get( "id" ).getAsInt();
                            saveData( sponsor_id, null );
                            Toast.makeText( getActivity(), "Sponsor Login successfull", Toast.LENGTH_SHORT ).show();
                            startActivity( new Intent( getActivity(), SponsorHome.class ) );
                            getActivity().finish();
                        }
                        else if(jsonArray.size()==0) {
                            signinAdmin(cred);
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText( getActivity(), "DB Connection failed", Toast.LENGTH_SHORT ).show();
                    }
                } );
    }

    public void saveData(int id, String role) {
        SharedPreferences preferences = this.getActivity().getSharedPreferences( "store", MODE_PRIVATE );
        preferences.edit().putInt( "sponsor_id", id ).commit();
        preferences.edit().putInt( "admin_id", id ).commit();
        if(remember_me.isChecked()) {
            if(role==null) {
                preferences.edit().putBoolean( "sponsor_logged_in", true ).commit();
            } else if(role.equals( "Volunteer" )) {
                preferences.edit().putBoolean( "volunteer_logged_in", true ).commit();
            } else if(role.equals( "Guardian" )) {
                preferences.edit().putBoolean( "guardian_logged_in", true ).commit();
            } else if(role.equals( "Super_Admin" )) {
                preferences.edit().putBoolean( "super_admin_logged_in", true ).commit();
            }
        }
    }

    public Cred validateData() {
        Cred cred = new Cred();
        cred.setEmail( login_username.getEditText().getText().toString() );
        cred.setPassword( login_password.getEditText().getText().toString() );
        Log.e("cred",cred.toString());

        if (!cred.getEmail().equals( "" ))
            if (!cred.getPassword().equals( "" ))
                return cred;
            else
                Toast.makeText( this.getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT ).show();
        else
            Toast.makeText( this.getActivity(), "Email cannot be empty", Toast.LENGTH_SHORT ).show();
        return null;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
