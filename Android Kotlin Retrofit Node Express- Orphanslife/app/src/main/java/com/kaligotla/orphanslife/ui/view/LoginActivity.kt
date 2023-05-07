package com.kaligotla.orphanslife.ui.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.kaligotla.orphanslife.databinding.ActivityLoginBinding
import com.kaligotla.orphanslife.model.response.LoginBody
import com.kaligotla.orphanslife.ui.view.admin.AdminHome
import com.kaligotla.orphanslife.ui.view.guardian.GuardianHome
import com.kaligotla.orphanslife.ui.view.sponsor.SponsorHome
import com.kaligotla.orphanslife.ui.view.volunteer.VolunteerHome
import com.kaligotla.orphanslife.ui.viewmodel.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel : LoginViewModel by viewModels {
        LoginViewModelFactory.getInstance(this)
    }
    private val preferencesViewModel : PreferencesViewModel by viewModels {
        PreferencesViewModelFactory.getInstance(applicationContext)
    }
    lateinit var otpFromRep: String
    lateinit var tokenFromRep: String
    lateinit var authenticatedUserFromRep: JsonObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        checkIfUserHasSavedDetails()
    }

    private fun checkIfUserHasSavedDetails(){
        preferencesViewModel.savedKey.observe(this){ it ->
            if (it == true){
                preferencesViewModel.API_Token.observe(this){ token->
                    if(token!=""){
                        preferencesViewModel.role.observe(this){ role->
                            if(role=="Super_Admin"){
                                val intent = Intent(this, AdminHome::class.java)
                                startActivity(intent)
                            } else if(role=="Sponsor"){
                                val intent = Intent(this, SponsorHome::class.java)
                                startActivity(intent)
                            } else if(role=="Volunteer"){
                                val intent = Intent(this, VolunteerHome::class.java)
                                startActivity(intent)
                            } else if(role=="Guardian"){
                                val intent = Intent(this, GuardianHome::class.java)
                                startActivity(intent)
                            } else {
                                makeButtonNotClickableAtFirst()
                                initViews()
                            }
                        }
                    } else {
                        makeButtonNotClickableAtFirst()
                        initViews()
                    }
                }
            } else{
                makeButtonNotClickableAtFirst()
                initViews()
            }
        }
    }

    private fun initViews(){
        handleClick()
    }

    private fun makeButtonNotClickableAtFirst(){
        binding.validateOtp.visibility = android.view.View.GONE
        binding.validateOtpButton.visibility = android.view.View.GONE
        binding.loginbutton.isEnabled = false
        val watcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val email = binding.editEmail.text.toString()
                val password = binding.editPassword.text.toString()
                if(email.isEmpty()) binding.editEmail.error = "Cannot be Empty"
                if(password.isEmpty()) binding.editPassword.error = "Cannot be Empty"
                binding.loginbutton.isEnabled = !(email.isEmpty() || password.isEmpty())
                val userOTP = binding.validateOtp.text.toString()
                if(userOTP.isEmpty() || userOTP.length>6 || userOTP.length<6) binding.validateOtp.error = "Should be 6 digits"
                binding.validateOtpButton.isEnabled = (!(userOTP.isEmpty()) && userOTP.length==6)
            }
            override fun afterTextChanged(s: Editable) {}
        }
        binding.editEmail.addTextChangedListener(watcher)
        binding.editPassword.addTextChangedListener(watcher)
        binding.validateOtp.addTextChangedListener(watcher)
    }

    private fun handleClick(){
        binding.loginbutton.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            val loginBody = LoginBody(email,password)
            binding.validateOtp.visibility = android.view.View.VISIBLE
            binding.validateOtpButton.visibility = android.view.View.VISIBLE
            loginViewModel.login(loginBody)
            loginViewModel.createPostLiveData.observe(this) {
                if (it == null) {
                    Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show()
                } else {
                    otpFromRep = it.otp
                    tokenFromRep = it.token
                    authenticatedUserFromRep = it.data.get(0).getAsJsonObject("loggedInUser")
                    Toast.makeText(this, "OTP sent to registered email", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.validateOtpButton.setOnClickListener{
            if(otpFromRep == binding.validateOtp.text.toString()) {
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT)
                    .show()
                preferencesViewModel.setSavedKey(true)
                preferencesViewModel.setAPI_Token(tokenFromRep)
                if(authenticatedUserFromRep.has("sponsor_id")) {
                    preferencesViewModel.setLoggedInUserID(authenticatedUserFromRep.get("sponsor_id").asInt)
                    preferencesViewModel.setRole("Sponsor")
                } else if(authenticatedUserFromRep.has("admin_id")) {
                    preferencesViewModel.setLoggedInUserID(authenticatedUserFromRep.get("admin_id").asInt)
                    if(authenticatedUserFromRep.get("role_id").asInt==1) {
                        preferencesViewModel.setRole("Volunteer")
                        Log.e("Volunteer logged In","Volunteer logged In")
                    } else if(authenticatedUserFromRep.get("role_id").asInt==2) {
                        preferencesViewModel.setRole("Guardian")
                        Log.e("Guardian logged In","Guardian logged In")
                    } else if(authenticatedUserFromRep.get("role_id").asInt==3) {
                        preferencesViewModel.setRole("Super_Admin")
                        Log.e("Super Admin logged In","Super Admin logged In")
                    }
                }
                checkIfUserHasSavedDetails()
            } else Toast.makeText(this, "Incorrect OTP", Toast.LENGTH_SHORT)
                .show()
        }
    }
}