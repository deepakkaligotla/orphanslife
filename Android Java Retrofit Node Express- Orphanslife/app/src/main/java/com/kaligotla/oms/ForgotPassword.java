package com.kaligotla.oms;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaligotla.oms.AdminView.Admin.Admin;
import com.kaligotla.oms.Essentials.Constants;
import com.kaligotla.oms.Essentials.DBService;
import com.kaligotla.oms.SponsorView.Sponsor;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPassword extends AppCompatActivity {

    TextInputLayout forgotUsername, forgotPasswordOTP, newPassword, confirmNewPassword;
    MaterialButton changePassword;
    MaterialTextView otpExpireMsg, timeCount;
    RelativeLayout timer;
    CircularProgressIndicator otpExpireProgress;
    Sponsor sponsor;
    Admin admin;
    int sentOTP;
    LocalTime otpSentTime, validateOTPTime, otpSentTimeCounter;
    Cred cred;
    ShapeableImageView forgotPasswordHome;
    long otpExpire;
    int progress = 100, seconds=120;
    CountDownTimer otpTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forgot_password);
        forgotPasswordHome = findViewById(R.id.forgotPasswordHome);
        forgotUsername = findViewById(R.id.forgotUsername);
        timer = findViewById(R.id.timer);
        forgotPasswordOTP = findViewById(R.id.forgotPasswordOTP);
        otpExpireProgress = findViewById(R.id.otpExpireProgress);
        timeCount = findViewById(R.id.timeCount);
        newPassword = findViewById(R.id.newPassword);
        confirmNewPassword = findViewById(R.id.confirmNewPassword);
        changePassword = findViewById(R.id.changePassword);
        otpExpireMsg = findViewById(R.id.otpExpireMsg);

        if(savedInstanceState!=null) {
            forgotUsername.getEditText().setText(savedInstanceState.getString("forgot_username_key"));
            forgotPasswordOTP.getEditText().setText(savedInstanceState.getString("forgot_otp_key"));
            newPassword.getEditText().setText(savedInstanceState.getString("new_password_key"));
            confirmNewPassword.getEditText().setText(savedInstanceState.getString("confirm_password_key"));
            sentOTP = Integer.parseInt(savedInstanceState.getString("sent_otp_key"));
            otpExpire = Long.parseLong(savedInstanceState.getString("otp_expire_key"));
            if(sentOTP!=0) {
                timer.setVisibility(View.VISIBLE);
                forgotPasswordOTP.setVisibility(View.VISIBLE);
                newPassword.setVisibility(View.VISIBLE);
                confirmNewPassword.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.VISIBLE);
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sponsor = new Sponsor();
        admin = new Admin();
        cred = new Cred();

        forgotPasswordHome.setOnClickListener(view -> {
            startActivity(new Intent(ForgotPassword.this, MainActivity.class));
        });

        newPassword.setEndIconOnClickListener(view -> {
            newPassword.getEditText().setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            newPassword.setEndIconDrawable(R.drawable.eye);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    newPassword.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newPassword.setEndIconDrawable(R.drawable.close_eye);
                }
            }, 2000);
        });

        confirmNewPassword.setEndIconOnClickListener(view -> {
            confirmNewPassword.getEditText().setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            confirmNewPassword.setEndIconDrawable(R.drawable.eye);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    confirmNewPassword.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmNewPassword.setEndIconDrawable(R.drawable.close_eye);
                }
            }, 2000);
        });
    }

    public void forgotPasswordGetOTP(View view) {
        cred.setEmail(forgotUsername.getEditText().getText().toString());
        Log.e("forgot password email", cred.getEmail());
        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(DBService.class)
                .forgotPasswordSendOTP(cred)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonArray jsonArray = response.body().getAsJsonArray("data");
                        JsonObject jsonObject;
                        if (jsonArray != null) {
                            sentOTP = response.body().get("otp").getAsInt();
                            Log.e("sentOTP", "" + sentOTP);
                            jsonObject = jsonArray.get(0).getAsJsonObject();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                otpSentTime = LocalTime.now();
                                otpSentTimeCounter = LocalTime.ofSecondOfDay(seconds);
                                timer.setVisibility(View.VISIBLE);
                                forgotPasswordOTP.setVisibility(View.VISIBLE);
                                newPassword.setVisibility(View.VISIBLE);
                                confirmNewPassword.setVisibility(View.VISIBLE);
                                changePassword.setVisibility(View.VISIBLE);
                                otpExpireMsg.setText("OTP sent to your email, use it to change your password within 2 minutes");
                                Toast.makeText(ForgotPassword.this, "Please check your email for OTP", Toast.LENGTH_SHORT).show();
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm:ss");
                                otpTimer = new CountDownTimer(120000,1200) {
                                    @Override
                                    public void onTick(long l) {
                                        otpExpireProgress.setProgress(progress);
                                        timeCount.setText(""+otpSentTimeCounter.format(dtf));
                                        otpSentTimeCounter = otpSentTimeCounter.minus(1, ChronoUnit.SECONDS);
                                        progress--;
                                    }
                                    @Override
                                    public void onFinish() {
                                        timer.setVisibility(View.GONE);
                                        forgotPasswordOTP.setVisibility(View.GONE);
                                        newPassword.setVisibility(View.GONE);
                                        confirmNewPassword.setVisibility(View.GONE);
                                        changePassword.setVisibility(View.GONE);
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
                        } else
                            Toast.makeText(ForgotPassword.this, "Invalid Email / Email is not registered with us", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(ForgotPassword.this, "Failed to connect with DB, please try again later", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void changePassword(View view) {
        cred.setPassword(newPassword.getEditText().getText().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            validateOTPTime = LocalTime.now();
            otpExpire = otpSentTime.until(validateOTPTime, ChronoUnit.MINUTES);
            if (otpExpire < 2) {
                otpExpireMsg.setVisibility(View.GONE);
                if (cred.getPassword().equals(confirmNewPassword.getEditText().getText().toString())) {
                    if (Integer.parseInt(forgotPasswordOTP.getEditText().getText().toString()) == sentOTP) {
                        new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create())
                                .baseUrl(Constants.BASE_URL)
                                .build()
                                .create(DBService.class)
                                .changePassword(cred)
                                .enqueue(new Callback<JsonObject>() {

                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        String status = response.body().get("status").getAsString();
                                        JsonObject data;
                                        if (status.equals("success")) {
                                            data = response.body().getAsJsonObject("data");
                                            if (data.get("affectedRows").getAsInt() == 1) {
                                                Toast.makeText(ForgotPassword.this, "Password Successfully changed", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                                            }
                                        } else
                                            Toast.makeText(ForgotPassword.this, "Could not change the password, please try again later", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {

                                    }
                                });
                    } else
                        Toast.makeText(ForgotPassword.this, "Invalid OTP", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ForgotPassword.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ForgotPassword.this, "OTP EXPIRED, Try again...!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("forgot_username_key",forgotUsername.getEditText().getText().toString());
        outState.putString("forgot_otp_key",forgotPasswordOTP.getEditText().getText().toString());
        outState.putString("new_password_key",newPassword.getEditText().getText().toString());
        outState.putString("confirm_password_key",confirmNewPassword.getEditText().getText().toString());
        outState.putString("sent_otp_key",String.valueOf(sentOTP));
        outState.putString("otp_expire_key",String.valueOf(otpExpire));
    }
}

