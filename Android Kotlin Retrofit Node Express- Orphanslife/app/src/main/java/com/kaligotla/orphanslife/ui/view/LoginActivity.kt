package com.kaligotla.orphanslife.ui.view

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kaligotla.orphanslife.data.repository.PreferencesRepo
import com.kaligotla.orphanslife.databinding.ActivityLoginBinding
import com.kaligotla.orphanslife.model.entity.Admin
import com.kaligotla.orphanslife.model.entity.Sponsor
import com.kaligotla.orphanslife.model.response.LoginBody
import com.kaligotla.orphanslife.ui.view.admin.AdminHome
import com.kaligotla.orphanslife.ui.view.guardian.GuardianHome
import com.kaligotla.orphanslife.ui.view.sponsor.SponsorHome
import com.kaligotla.orphanslife.ui.view.volunteer.VolunteerHome
import com.kaligotla.orphanslife.ui.viewmodel.LoginViewModel
import com.kaligotla.orphanslife.ui.viewmodel.LoginViewModelFactory
import com.kaligotla.orphanslife.ui.viewmodel.PreferencesViewModel
import com.kaligotla.orphanslife.ui.viewmodel.PreferencesViewModelFactory
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory.getInstance(this)
    }
    private lateinit var preferencesViewModel: PreferencesViewModel

    lateinit var otpFromRep: String
    lateinit var tokenFromRep: String
    lateinit var role: String
    lateinit var loggedInSponsor: Sponsor
    lateinit var loggedInAdmin: Admin
    lateinit var authenticatedUserFromRep: JsonObject
    lateinit var otpSentTime: LocalTime
    lateinit var validateOTPTime: LocalTime
    lateinit var otpSentTimeCounter: LocalTime
    lateinit var otpTimer: CountDownTimer
    var progressTime: Double = 100.0
    var seconds: Int = 120
    private lateinit var intent: Intent

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        preferencesViewModel = ViewModelProvider(
            this,
            PreferencesViewModelFactory(PreferencesRepo.getInstance(applicationContext))
        )[PreferencesViewModel::class.java]
        checkIfUserHasSavedDetails()
        makeButtonNotClickableAtFirst()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkIfUserHasSavedDetails() {
        if (preferencesViewModel.savedKey.equals(true)) {
            if (!preferencesViewModel.API_Token.equals("")) {
                if (preferencesViewModel.role.equals("Super_Admin")) {
                    intent = Intent(this, AdminHome::class.java)
                    startActivity(intent)
                } else if (preferencesViewModel.role.equals("Sponsor")) {
                    intent = Intent(this, SponsorHome::class.java)
                    startActivity(intent)
                } else if (preferencesViewModel.role.equals("Volunteer")) {
                    intent = Intent(this, VolunteerHome::class.java)
                    startActivity(intent)
                } else if (preferencesViewModel.role.equals("Guardian")) {
                    intent = Intent(this, GuardianHome::class.java)
                    startActivity(intent)
                } else {
                    initViews()
                }
            } else {
                initViews()
            }
        } else {
            initViews()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        handleClick()
    }

    private fun makeButtonNotClickableAtFirst() {
        binding.otpLinearLayout.visibility = View.GONE
        binding.otpExpireMsg.visibility = View.GONE
        binding.loginbutton.isEnabled = false
        val watcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val email = binding.editEmail.text.toString()
                val password = binding.editPassword.text.toString()
                if (email.isEmpty()) binding.editEmail.error = "Cannot be Empty"
                if (password.isEmpty()) binding.editPassword.error = "Cannot be Empty"
                binding.loginbutton.isEnabled = !(email.isEmpty() || password.isEmpty())
                val userOTP = binding.validateOtp.text.toString()
                if (userOTP.isEmpty() || userOTP.length > 6 || userOTP.length < 6) binding.validateOtp.error =
                    "Should be 6 digits"
                binding.validateOtpButton.isEnabled = (!(userOTP.isEmpty()) && userOTP.length == 6)
            }

            override fun afterTextChanged(s: Editable) {}
        }
        binding.editEmail.addTextChangedListener(watcher)
        binding.editPassword.addTextChangedListener(watcher)
        binding.validateOtp.addTextChangedListener(watcher)
        binding.rememberMe.setOnCheckedChangeListener { _, b ->
            if (b) {
                Log.e("remember Me state", "is checked")
            } else if (!b) {
                Log.e("remember Me state", "not checked")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleClick() {
        binding.loginbutton.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            val loginBody = LoginBody(email, password)
            binding.otpLinearLayout.visibility = View.VISIBLE
            loginViewModel.login(loginBody)
            loginViewModel.createPostLiveData.observe(this) {
                if (it == null) {
                    Toast.makeText(this@LoginActivity, "Account not found", Toast.LENGTH_SHORT).show()
                } else {
                    otpSentTime = LocalTime.now()
                    otpFromRep = it.otp
                    tokenFromRep = it.token
                    authenticatedUserFromRep = it.data.get(0).getAsJsonObject("loggedInUser")
                    Toast.makeText(this@LoginActivity, "OTP sent to registered email", Toast.LENGTH_SHORT)
                        .show()
                    binding.editEmail.isFocusable = false
                    binding.editPassword.isFocusable = false
                    otpTimer()
                }
            }
            progressTime = 100.0
        }

        binding.validateOtpButton.setOnClickListener {
            validateOTPTime = LocalTime.now()
            val otpExpire = otpSentTime.until(validateOTPTime, ChronoUnit.MINUTES)
            if(otpExpire<2) {
                if (otpFromRep == binding.validateOtp.text.toString()) {
                    //checking Sponsor
                    if (authenticatedUserFromRep.has("sponsor_id")) {
                        Toast.makeText(this@LoginActivity, "Sponsor Successfully LoggedIn", Toast.LENGTH_SHORT)
                            .show()
                        loggedInSponsor = Gson().fromJson(authenticatedUserFromRep, Sponsor::class.java)
                        role = "Sponsor"
                        rememberMe()
                        intent = Intent(this, SponsorHome::class.java)
                        startActivity(intent)
                    }
                    //Checking Admin
                    else if (authenticatedUserFromRep.has("admin_id")) {
                        Toast.makeText(this@LoginActivity, "Admin Successfully LoggedIn", Toast.LENGTH_SHORT)
                            .show()
                        loggedInAdmin = Gson().fromJson(authenticatedUserFromRep, Admin::class.java)
                        if (authenticatedUserFromRep.get("role_id").asInt == 1) {
                            role = "Volunteer"
                            rememberMe()
                            intent = Intent(this, VolunteerHome::class.java)
                            startActivity(intent)
                        } else if (authenticatedUserFromRep.get("role_id").asInt == 2) {
                            role = "Guardian"
                            rememberMe()
                            intent = Intent(this, GuardianHome::class.java)
                            startActivity(intent)
                        } else if (authenticatedUserFromRep.get("role_id").asInt == 3) {
                            role = "Super_Admin"
                            rememberMe()
                            intent = Intent(this, AdminHome::class.java)
                            startActivity(intent)
                        }
                    }
                } else Toast.makeText(this@LoginActivity, "Incorrect OTP", Toast.LENGTH_SHORT)
                    .show()
            } else Toast.makeText(this@LoginActivity, "OTP EXPIRED", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun rememberMe() {
        if (binding.rememberMe.isChecked) {
            Log.e("remember Me state", "is checked")
            preferencesViewModel.setSavedKey(true)
            preferencesViewModel.setAPI_Token(tokenFromRep)
            if(role.equals("Sponsor")) {
                preferencesViewModel.setLoggedInUserID(loggedInSponsor.sponsor_id)
            } else if(role.equals("Volunteer")|| role.equals("Guardian") || role.equals("Super_Admin")) {
                preferencesViewModel.setLoggedInUserID(loggedInAdmin.admin_id)
            }
            preferencesViewModel.setRole(role)
        } else if (!binding.rememberMe.isChecked) {
            Log.e("remember Me state", "not checked")
            preferencesViewModel.setSavedKey(false)
            preferencesViewModel.setAPI_Token(tokenFromRep)
            if(role.equals("Sponsor")) {
                preferencesViewModel.setLoggedInUserID(loggedInSponsor.sponsor_id)
            } else if(role.equals("Volunteer")|| role.equals("Guardian") || role.equals("Super_Admin")) {
                preferencesViewModel.setLoggedInUserID(loggedInAdmin.admin_id)
            }
            preferencesViewModel.setRole(role)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun otpTimer() {
        otpSentTimeCounter = LocalTime.ofSecondOfDay(seconds.toLong())
        val dtf = DateTimeFormatter.ofPattern("mm:ss")
        otpTimer = object : CountDownTimer(120000, 1000) {
            override fun onTick(l: Long) {
                binding.otpExpireProgress.progress = progressTime.roundToInt()
                binding.timeCount.text = "" + otpSentTimeCounter.format(dtf)
                otpSentTimeCounter = otpSentTimeCounter.minus(1, ChronoUnit.SECONDS)
                progressTime -= 0.83333333333
                if(progressTime<75.0 && progressTime>50.0) {
                    binding.otpExpireProgress.setIndicatorColor(Color.BLUE)
                    binding.otpExpireMsg.text = "That's okay take your time 😇"
                    binding.otpExpireMsg.setTextColor(Color.WHITE)
                    binding.otpExpireMsg.visibility = View.VISIBLE
                }
                if(progressTime<50.0 && progressTime>30.0) {
                    binding.otpExpireProgress.setIndicatorColor(Color.YELLOW)
                    binding.otpExpireMsg.text = "Hmm I know it is not easy take your time 😮‍💨"
                    binding.otpExpireMsg.setTextColor(Color.YELLOW)
                    binding.otpExpireMsg.visibility = View.VISIBLE
                }
                if(progressTime<30.0 && progressTime>20.0) {
                    binding.otpExpireProgress.setIndicatorColor(Color.RED)
                    binding.otpExpireMsg.text = "Are you there, don't leave me alone 🥺"
                    binding.otpExpireMsg.setTextColor(Color.RED)
                    binding.otpExpireMsg.visibility = View.VISIBLE
                }
                if(progressTime<20.0 && progressTime>10.0) {
                    binding.otpExpireProgress.setIndicatorColor(Color.RED)
                    binding.otpExpireMsg.text = "I am scared are you okay 😰, \nif you didnt get the otp click on Login again and \ncheck your spam folder too"
                    val animator = ObjectAnimator.ofInt(binding.otpExpireMsg, "textColor", Color.RED)
                    animator.duration = 500
                    animator.setEvaluator(ArgbEvaluator())
                    animator.repeatCount = Animation.ABSOLUTE
                    animator.repeatCount = Animation.INFINITE
                    animator.start()
                    binding.otpExpireMsg.visibility = View.VISIBLE
                    binding.otpExpireProgress.setIndicatorColor(Color.WHITE)
                }
                if(progressTime<10.0) {
                    binding.otpExpireMsg.text = "Gone it is over, I know you are here to fingering me!!! 😤"
                    val animator = ObjectAnimator.ofInt(binding.otpExpireMsg, "textColor", Color.RED)
                    animator.duration = 200
                    animator.setEvaluator(ArgbEvaluator())
                    animator.repeatCount = Animation.ABSOLUTE
                    animator.repeatCount = Animation.INFINITE
                    animator.start()
                    binding.otpExpireMsg.visibility = View.VISIBLE
                    binding.otpExpireProgress.setIndicatorColor(Color.WHITE)
                }
            }

            override fun onFinish() {
                binding.otpLinearLayout.visibility = View.GONE
                binding.otpExpireMsg.text = "You stupid idiot your OTP EXPIRED \n Click login again cloud face fellow get another otp \n\"& remember otp is valid only for 2 minutes\""
                val animator = ObjectAnimator.ofInt(binding.otpExpireMsg, "textColor", Color.RED)
                animator.duration = 1000
                animator.setEvaluator(ArgbEvaluator())
                animator.repeatCount = Animation.ABSOLUTE
                animator.repeatCount = Animation.INFINITE
                animator.start()
            }
        }
        otpTimer.start()
    }
}