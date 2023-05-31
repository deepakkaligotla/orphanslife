package com.kaligotla.oms;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.Admin.Admin;
import com.kaligotla.oms.AdminView.AdminHome;
import com.kaligotla.oms.AdminView.Role.Role;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.GuardianView.GuardianHome;
import com.kaligotla.oms.SponsorView.Sponsor;
import com.kaligotla.oms.SponsorView.SponsorHome;
import com.kaligotla.oms.VolunteerView.VolunteerHome;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {
    TextInputLayout login_username, login_password, enteredOTP;
    MaterialCheckBox remember_me;
    LinearLayout otpLinearLayout;
    GifImageView kids_jumping;
    MaterialButton sign_in, validateOTP;
    MaterialTextView forgotPassword, otpExpireMsg, timeCount;
    Cred cred;
    View rootView;
    Bundle bundle;
    int sentOTP;
    Sponsor loggedInSponsor;
    Admin loggedInAdmin;
    LocalTime otpSentTime, validateOTPTime, otpSentTimeCounter;
    CircularProgressIndicator otpExpireProgress;
    CountDownTimer otpTimer;
    int progress = 100, seconds = 120;
    String apiToken;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = new Bundle();
        sharedPreferences = this.getActivity().getSharedPreferences("store", Context.MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
        Log.e("API Token from Shared Preferences", sharedPreferences.getString("API_Token", ""));
        Log.e("super_admin_logged_in from Shared Preferences", "" + sharedPreferences.getBoolean("super_admin_logged_in", false));
        Log.e("admin_id from Shared Preferences", "" + sharedPreferences.getInt("admin_id", 0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        login_username = rootView.findViewById(R.id.login_username);
        login_password = rootView.findViewById(R.id.login_password);
        enteredOTP = rootView.findViewById(R.id.enteredOTP);
        remember_me = rootView.findViewById(R.id.remember_me);
        kids_jumping = rootView.findViewById(R.id.kids_jumping);
        sign_in = rootView.findViewById(R.id.sign_in);
        otpExpireProgress = rootView.findViewById(R.id.otpExpireProgress);
        timeCount = rootView.findViewById(R.id.timeCount);
        validateOTP = rootView.findViewById(R.id.validateOTP);
        forgotPassword = rootView.findViewById(R.id.forgotPassword);
        otpLinearLayout = rootView.findViewById(R.id.otpLinearLayout);
        otpExpireMsg = rootView.findViewById(R.id.otpExpireMsg);
        otpLinearLayout.setVisibility(View.GONE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //restoring data when screen orientation changed
        if (savedInstanceState != null) {
            login_username.getEditText().setText(savedInstanceState.getString("login_username_key"));
            login_password.getEditText().setText(savedInstanceState.getString("login_password_key"));
            sentOTP = Integer.parseInt(savedInstanceState.getString("sent_otp_key"));
            if (sentOTP != 0) {
                otpLinearLayout.setVisibility(View.VISIBLE);
            } else otpLinearLayout.setVisibility(View.GONE);
        }
        super.onCreate(savedInstanceState);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        login_password.setEndIconOnClickListener(view -> {
            login_password.getEditText().setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            login_password.setEndIconDrawable(R.drawable.eye);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    login_password.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
                    login_password.setEndIconDrawable(R.drawable.close_eye);
                }
            }, 2000);
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_in();
            }
        });
        validateOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateOTP();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ForgotPassword.class));
            }
        });

    }

    public void sign_in() {
        cred = validateData();
        if (cred != null) {
            Log.e("sign_in", cred.toString());
            sign_in(cred);
        }
    }

    public Cred validateData() {
        cred = new Cred();
        cred.setEmail(login_username.getEditText().getText().toString());
        cred.setPassword(login_password.getEditText().getText().toString());

        String emailRegex = "[a-zA-Z0-9]{0,}([._-]?[a-zA-Z0-9]{1,})[@][a-z]{3,}(.com|.in)";
        Pattern p = Pattern.compile(emailRegex);
        Matcher m = p.matcher(cred.getEmail());

        if (!cred.getEmail().equals(""))
            if (!cred.getPassword().equals("")) {
                if (m.matches()) {
                    return cred;
                } else
                    Toast.makeText(this.getActivity(), "Enter valid email", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this.getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this.getActivity(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
        return null;
    }

    public void sign_in(Cred cred) {

        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(DBService.class)
                .login(cred)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonArray = response.body().get("data").getAsJsonArray();
                        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject().getAsJsonObject("loggedInUser");
                        apiToken = response.body().get("token").getAsString();
                        sentOTP = response.body().get("otp").getAsInt();
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            otpSentTime = LocalTime.now();
                            otpTimer();
                        }
                        if (!jsonObject.has("role_id")) {
                            Log.e("sentOTP", "" + sentOTP);
                            loggedInSponsor = new Sponsor();
                            Log.e("jsonObject", "" + jsonObject);
                            loggedInSponsor.setSponsor_id(jsonObject.get("sponsor_id").getAsInt());
                            loggedInSponsor.setSponsor_name(jsonObject.get("sponsor_name").getAsString());
                            loggedInSponsor.setSponsor_email(jsonObject.get("sponsor_email").getAsString());
                            otpLinearLayout.setVisibility(View.VISIBLE);
                        } else if (jsonObject.has("role_id")) {
                            Toast.makeText(getActivity(), "Checking if Admin", Toast.LENGTH_LONG).show();
                            Log.e("sentOTP", "" + sentOTP);
                            loggedInAdmin = new Admin();
                            loggedInAdmin.setAdmin_id(jsonObject.get("admin_id").getAsInt());
                            loggedInAdmin.setRole(new Role(jsonObject.get("role").getAsString()));
                            otpLinearLayout.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getActivity(), "No account found, kindly check email & password or Register with us", Toast.LENGTH_LONG).show();
                            RegisterFragment rf = new RegisterFragment();
                            bundle.putString("email", login_username.getEditText().getText().toString());
                            bundle.putString("password", login_password.getEditText().getText().toString());
                            rf.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, RegisterFragment.class, bundle).commit();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getActivity(), "DB Connection failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void otpTimer() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            otpSentTimeCounter = LocalTime.ofSecondOfDay(seconds);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm:ss");
            otpTimer = new CountDownTimer(120000, 1200) {
                @Override
                public void onTick(long l) {
                    otpExpireProgress.setProgress(progress);
                    timeCount.setText("" + otpSentTimeCounter.format(dtf));
                    otpSentTimeCounter = otpSentTimeCounter.minus(1, ChronoUnit.SECONDS);
                    progress--;
                }

                @Override
                public void onFinish() {
                    otpLinearLayout.setVisibility(View.GONE);
                    otpExpireMsg.setText("OTP EXPIRED, Click get OTP again to change password \"2 minutes\"");
                    ObjectAnimator animator = ObjectAnimator.ofInt(otpExpireMsg, "textColor", Color.RED);
                    animator.setDuration(2000);
                    animator.setEvaluator(new ArgbEvaluator());
                    animator.setRepeatCount(Animation.ABSOLUTE);
                    animator.setRepeatCount(Animation.INFINITE);
                    animator.start();
                }
            };
            otpTimer.start();
        }
    }

    public void validateOTP() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            validateOTPTime = LocalTime.now();
            long otpExpire = otpSentTime.until(validateOTPTime, ChronoUnit.MINUTES);
            if (otpExpire < 2) {
                if (Integer.parseInt(enteredOTP.getEditText().getText().toString()) == sentOTP) {
                    if (loggedInSponsor != null) {
                        saveData(null, apiToken, loggedInSponsor.getSponsor_id());
                        Toast.makeText(getActivity(), "Sponsor Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), SponsorHome.class));
                        getActivity().finish();
                    } else if (loggedInAdmin != null) {
                        Toast.makeText(getActivity(), "Admin Login successfull", Toast.LENGTH_SHORT).show();
                        if (loggedInAdmin.getRole().getRole().equals("Super_Admin")) {
                            saveData(loggedInAdmin.getRole().getRole(), apiToken, loggedInAdmin.getAdmin_id());
                            startActivity(new Intent(getActivity(), AdminHome.class));
                        } else if (loggedInAdmin.getRole().getRole().equals("Volunteer")) {
                            saveData(loggedInAdmin.getRole().getRole(), apiToken, loggedInAdmin.getAdmin_id());
                            startActivity(new Intent(getActivity(), VolunteerHome.class));
                        } else if (loggedInAdmin.getRole().getRole().equals("Guardian")) {
                            saveData(loggedInAdmin.getRole().getRole(), apiToken, loggedInAdmin.getAdmin_id());
                            startActivity(new Intent(getActivity(), GuardianHome.class));
                        }
                        getActivity().finish();
                    }
                } else {
                    Toast.makeText(getActivity(), "INVALID OTP", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            } else {
                Toast.makeText(getActivity(), "OTP EXPIRED, SignIn again...!", Toast.LENGTH_LONG).show();
                otpLinearLayout.setVisibility(View.GONE);
            }
        }
    }

    public void saveData(String role, String JWT_Token, int id) {
        myEdit.clear();
        myEdit.putString("API_Token", JWT_Token);
        if (remember_me.isChecked()) {
            if (role == null) {
                myEdit.putInt("sponsor_id", id);
                myEdit.putBoolean("sponsor_logged_in", true);
            } else if (role != null) {
                if (role.equals("Volunteer")) {
                    myEdit.putInt("admin_id", id);
                    myEdit.putBoolean("volunteer_logged_in", true);
                } else if (role.equals("Guardian")) {
                    myEdit.putInt("admin_id", id);
                    myEdit.putBoolean("guardian_logged_in", true).commit();
                } else if (role.equals("Super_Admin")) {
                    myEdit.putInt("admin_id", 4);
                    myEdit.putBoolean("super_admin_logged_in", true);
                }
            }
        }
        myEdit.commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("login_username_key", login_username.getEditText().getText().toString());
        outState.putString("login_password_key", login_password.getEditText().getText().toString());
        outState.putString("sent_otp_key", String.valueOf(sentOTP));
    }
}
