package com.kaligotla.orphanslife.ui.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.kaligotla.orphanslife.R
import com.kaligotla.orphanslife.databinding.ActivityLoginBinding
import com.kaligotla.orphanslife.db.OrphanslifeDB
import com.kaligotla.orphanslife.model.entity.Admin
import com.kaligotla.orphanslife.model.response.LoginBody
import com.kaligotla.orphanslife.ui.view.admin.AdminHome
import com.kaligotla.orphanslife.ui.view.guardian.GuardianHome
import com.kaligotla.orphanslife.ui.view.sponsor.SponsorHome
import com.kaligotla.orphanslife.ui.view.volunteer.VolunteerHome
import com.kaligotla.orphanslife.ui.viewmodel.LoginViewModel
import com.kaligotla.orphanslife.ui.viewmodel.PreferencesViewModel
import com.kaligotla.orphanslife.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel : LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private val preferencesViewModel : PreferencesViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkIfUserHasSavedDetails()
        initViews()
    }

    private fun checkIfUserHasSavedDetails(){
        preferencesViewModel.savedKey.observe(this){ it ->
            if (it == true){
                preferencesViewModel.LoggedInUserID.observe(this){ id ->
                    if(id!=0) {
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
                                    } else if(role==""){
                                        makeButtonNotClickableAtFirst()
                                        initViews()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else{
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
                if(email.isEmpty()) binding.editEmail.error = "Invalid Email"
                if(password.isEmpty()) binding.editPassword.error = "Invalid Password"
                binding.loginbutton.isEnabled = !(email.isEmpty() || password.isEmpty())
            }
            override fun afterTextChanged(s: Editable) {

            }
        }
        binding.editEmail.addTextChangedListener(watcher)
        binding.editPassword.addTextChangedListener(watcher)
    }

    private fun handleClick(){
        binding.loginbutton.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            val loginBody = LoginBody(email,password)
            loginViewModel.login(loginBody)
            binding.validateOtp.visibility = android.view.View.VISIBLE
            binding.validateOtpButton.isEnabled = false
            binding.validateOtpButton.visibility = android.view.View.VISIBLE
        }

        binding.validateOtpButton.setOnClickListener{
            val watcher: TextWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val userOTP = binding.validateOtp.text.toString()
                    if(userOTP.isEmpty()) binding.validateOtp.error = "Invalid OTP"
                    binding.validateOtpButton.isEnabled = (!userOTP.isEmpty() && userOTP.length==6)
                    checkIfUserHasSavedDetails()
                }
                override fun afterTextChanged(s: Editable) {

                }
            }
            binding.validateOtp.addTextChangedListener(watcher)
        }
    }
}