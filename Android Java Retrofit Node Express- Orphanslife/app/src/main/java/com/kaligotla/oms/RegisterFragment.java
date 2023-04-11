package com.kaligotla.oms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.Admin.Admin;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.SponsorView.Sponsor;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment {

    TextInputLayout name, dob, mobile, email, password, confirm_password;
    TextInputEditText dobEditText;
    LinearLayout adminRoleLinearLayout;
    MaterialButton signup;
    Spinner whoAmISpinner, genderSpinner, adminRoleSpinner;
    String[] whoAmISpinnerArray = {"Sponsor", "Admin"};
    String[] genderSpinnerArray = {"Male", "Female", "Other"};
    String[] adminRoleSpinnerArray = {"Volunteer", "Guardian"};
    View rootView;
    String emailFromLogin, passwordFromLogin;
    Admin admin;
    Sponsor sponsor;
    String sponsor_name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        emailFromLogin = getArguments().getString("email");
        passwordFromLogin = getArguments().getString("password");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RegisterViewModel homeViewModel =
                new ViewModelProvider(this).get(RegisterViewModel.class);
        rootView = inflater.inflate(R.layout.fragment_register, container, false);
        whoAmISpinner = rootView.findViewById(R.id.whoAmISpinner);
        adminRoleLinearLayout = rootView.findViewById(R.id.adminRoleLinearLayout);
        name = rootView.findViewById(R.id.name);
        dob = rootView.findViewById(R.id.dob);
        dobEditText = rootView.findViewById(R.id.dobEditText);
        genderSpinner = rootView.findViewById(R.id.genderSpinner);
        mobile = rootView.findViewById(R.id.mobile);
        email = rootView.findViewById(R.id.email);
        password = rootView.findViewById(R.id.password);
        confirm_password = rootView.findViewById(R.id.confirm_password);
        adminRoleSpinner = rootView.findViewById(R.id.adminRoleSpinner);
        signup = rootView.findViewById(R.id.signup);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        ArrayAdapter whoAmIAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, whoAmISpinnerArray);
        whoAmIAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        whoAmISpinner.setAdapter(whoAmIAdapter);

        ArrayAdapter adminRoleAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, adminRoleSpinnerArray);
        adminRoleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adminRoleSpinner.setAdapter(adminRoleAdapter);

        ArrayAdapter genderAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, genderSpinnerArray);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

        //restore state
        if (savedInstanceState == null) {
            Log.e("onCreateView savedInstanceState", "null");
        } else if (savedInstanceState != null) {
            if (savedInstanceState.getString("who").equals(whoAmISpinnerArray[0])) {
                whoAmISpinner.setSelection(1);
            } else whoAmISpinner.setSelection(1);
            sponsor_name = savedInstanceState.getString("sponsor_name");
            name.getEditText().setText(sponsor_name);
        }
        super.onCreate(savedInstanceState);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        email.getEditText().setText(emailFromLogin);
        password.getEditText().setText(passwordFromLogin);

        adminRoleLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        whoAmISpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (whoAmISpinner.getSelectedItem().toString().equals("Admin")) {
                    Log.e("makeAdminRoleSpinnerVisible", whoAmISpinner.getSelectedItem().toString());
                    adminRoleLinearLayout.setVisibility(View.VISIBLE);
                } else if (whoAmISpinner.getSelectedItem().toString().equals("Sponsor")) {
                    Log.e("makeAdminRoleSpinnerVisible", whoAmISpinner.getSelectedItem().toString());
                    adminRoleLinearLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                adminRoleLinearLayout.setVisibility(View.GONE);
            }
        });
        super.onResume();
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), (new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dob.getEditText().setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }), year, month, day);
                datePickerDialog.show();
            }
        });
    }

    //using retrofit for calling API
    public void registerSponsor(Sponsor sponsor) {
        new Retrofit.Builder() // Creating new Retrofit Builder object
                .addConverterFactory(GsonConverterFactory.create()) //Adding the convertor
                .baseUrl(Constants.BASE_URL) //Adding the base url
                .build() // creating the retrofit object
                .create(DBService.class)
                .register(sponsor)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.body().getAsJsonObject("data").get("affectedRows").getAsInt()==1) {
                            Toast.makeText(getActivity(), "User Registerd Successfully with ID - "+response.body().getAsJsonObject("data").get("insertId").getAsInt(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getActivity(), "haha.. You failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    // For data validation
    public void validateData() {
        sponsor = new Sponsor(name.getEditText().getText().toString(), dob.getEditText().getText().toString(), genderSpinner.getSelectedItem().toString(), mobile.getEditText().getText().toString(), email.getEditText().getText().toString(), password.getEditText().getText().toString());
        String emailRegex = "[a-zA-Z0-9]{0,}([._-]?[a-zA-Z0-9]{1,})[@][a-z]{3,}(.com)";
        Pattern p = Pattern.compile(emailRegex);
        Matcher m = p.matcher(sponsor.getSponsor_email());
        String confirmPassword = confirm_password.getEditText().getText().toString();
        if (!sponsor.getSponsor_name().equals("")) {
            if (!sponsor.getSponsor_email().equals("")) {
                if (!sponsor.getSponsor_mobile().equals("")) {
                    if (sponsor.getSponsor_password()!="") {
                        if (sponsor.getSponsor_password().equals(confirmPassword)) {
                            if (m.matches()) {
                                Toast.makeText(getActivity(), "Checking if email already exists in DB", Toast.LENGTH_SHORT).show();
                                new Retrofit.Builder()
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .baseUrl(Constants.BASE_URL)
                                        .build()
                                        .create(DBService.class)
                                        .checkSponsorExists(sponsor)
                                        .enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                JsonArray jsonArray = response.body().getAsJsonArray("data");
                                                if (jsonArray.size()==0) {
                                                    Log.e("sending to register", sponsor.toString());
                                                    registerSponsor(sponsor);
                                                } else if(jsonArray.size()>0) {
                                                    Toast.makeText(getActivity(), "Email ID already exists, please login", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                            }
                                        });
                            } else
                                Toast.makeText(getActivity(), "Please provide a valid email", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Mobile cannot be empty", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getActivity(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getActivity(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
    }

    public void cancel(View view) {
        onDestroy();
    }

    public void sign_in(View view) {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String who = whoAmISpinner.getSelectedItem().toString();
        String adminRole = adminRoleSpinner.getSelectedItem().toString();
        sponsor_name = name.getEditText().getText().toString();
//        admin.setAdmin_name(name.getEditText().getText().toString());
//        sponsor.setSponsor_dob(dob.getEditText().getText().toString());
//        admin.setAdmin_dob(dob.getEditText().getText().toString());
//        sponsor.setSponsor_gender(genderSpinner.getSelectedItem().toString());
//        admin.setAdmin_gender(genderSpinner.getSelectedItem().toString());
//        sponsor.setSponsor_mobile(mobile.getEditText().getText().toString());
//        admin.setAdmin_mobile(mobile.getEditText().getText().toString());
//        sponsor.setSponsor_email(email.getEditText().getText().toString());
//        admin.setAdmin_email(email.getEditText().getText().toString());
        outState.putString("who", who);
        outState.putString("adminRole", adminRole);
        outState.putString("sponsor_name", sponsor_name);
    }
}
