package com.kaligotla.orphanslife.ui.view.admin

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.kaligotla.orphanslife.R
import com.kaligotla.orphanslife.data.repository.PreferencesRepo
import com.kaligotla.orphanslife.databinding.ActivityAdminHomeBinding
import com.kaligotla.orphanslife.model.entity.Admin
import com.kaligotla.orphanslife.ui.view.LoginActivity
import com.kaligotla.orphanslife.ui.view.settings.AdminAccountSettings
import com.kaligotla.orphanslife.ui.viewmodel.DBViewModel
import com.kaligotla.orphanslife.ui.viewmodel.DBViewModelFactory
import com.kaligotla.orphanslife.ui.viewmodel.PreferencesViewModel
import com.kaligotla.orphanslife.ui.viewmodel.PreferencesViewModelFactory
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import kotlin.concurrent.thread

class AdminHome : AppCompatActivity() {

    private lateinit var binding: ActivityAdminHomeBinding
    private lateinit var preferencesViewModel: PreferencesViewModel
    private val dbViewModel: DBViewModel by viewModels {
        DBViewModelFactory.getInstance(this)
    }
    lateinit var loggedInAdminDetails: Admin
    var success: Int = 0
    var failed: Int = 0
    var new_req: Int = 0
    var approved: Int = 0
    var rejected: Int = 0
    lateinit var otpTimer: CountDownTimer

    data class MonthwiseDonations(
        val Year: Int = 2022,
        val Month: Int = 6,
        val donations: Double = 0.0
    )

    var monthwiseDonations = Array<MonthwiseDonations?>(12) { MonthwiseDonations() }

    inner class WebAppInterface {
        @JavascriptInterface fun getSuccess(): Int { return success }
        @JavascriptInterface fun getFailed(): Int { return failed }

        @JavascriptInterface fun getNewReq(): Int { return new_req }
        @JavascriptInterface fun getApproved(): Int { return approved }
        @JavascriptInterface fun getRejected(): Int { return rejected }

        @JavascriptInterface fun getJanDonationYear(): Int { return monthwiseDonations[0]!!.Year }
        @JavascriptInterface fun getJanDonationMonth(): Int { return monthwiseDonations[0]!!.Month-1 }
        @JavascriptInterface fun getJanDonations(): Double { return monthwiseDonations[0]!!.donations }

        @JavascriptInterface fun getFebDonationYear(): Int { return monthwiseDonations[1]!!.Year }
        @JavascriptInterface fun getFebDonationMonth(): Int { return monthwiseDonations[1]!!.Month-1 }
        @JavascriptInterface fun getFebDonations(): Double { return monthwiseDonations[1]!!.donations }

        @JavascriptInterface fun getMarDonationYear(): Int { return monthwiseDonations[2]!!.Year }
        @JavascriptInterface fun getMarDonationMonth(): Int { return monthwiseDonations[2]!!.Month-1 }
        @JavascriptInterface fun getMarDonations(): Double { return monthwiseDonations[2]!!.donations }

        @JavascriptInterface fun getAprDonationYear(): Int { return monthwiseDonations[3]!!.Year }
        @JavascriptInterface fun getAprDonationMonth(): Int { return monthwiseDonations[3]!!.Month-1 }
        @JavascriptInterface fun getAprDonations(): Double { return monthwiseDonations[3]!!.donations }

        @JavascriptInterface fun getMayDonationYear(): Int { return monthwiseDonations[4]!!.Year }
        @JavascriptInterface fun getMayDonationMonth(): Int { return monthwiseDonations[4]!!.Month-1 }
        @JavascriptInterface fun getMayDonations(): Double { return monthwiseDonations[4]!!.donations }

        @JavascriptInterface fun getJunDonationYear(): Int { return monthwiseDonations[5]!!.Year }
        @JavascriptInterface fun getJunDonationMonth(): Int { return monthwiseDonations[5]!!.Month-1 }
        @JavascriptInterface fun getJunDonations(): Double { return monthwiseDonations[5]!!.donations }

        @JavascriptInterface fun getJulDonationYear(): Int { return monthwiseDonations[6]!!.Year }
        @JavascriptInterface fun getJulDonationMonth(): Int { return monthwiseDonations[6]!!.Month-1 }
        @JavascriptInterface fun getJulDonations(): Double { return monthwiseDonations[6]!!.donations }

        @JavascriptInterface fun getAugDonationYear(): Int { return monthwiseDonations[7]!!.Year }
        @JavascriptInterface fun getAugDonationMonth(): Int { return monthwiseDonations[7]!!.Month-1 }
        @JavascriptInterface fun getAugDonations(): Double { return monthwiseDonations[7]!!.donations }

        @JavascriptInterface fun getSepDonationYear(): Int { return monthwiseDonations[8]!!.Year }
        @JavascriptInterface fun getSepDonationMonth(): Int { return monthwiseDonations[8]!!.Month-1 }
        @JavascriptInterface fun getSepDonations(): Double { return monthwiseDonations[8]!!.donations }

        @JavascriptInterface fun getOctDonationYear(): Int { return monthwiseDonations[9]!!.Year }
        @JavascriptInterface fun getOctDonationMonth(): Int { return monthwiseDonations[9]!!.Month-1 }
        @JavascriptInterface fun getOctDonations(): Double { return monthwiseDonations[9]!!.donations }

        @JavascriptInterface fun getNovDonationYear(): Int { return monthwiseDonations[10]!!.Year }
        @JavascriptInterface fun getNovDonationMonth(): Int { return monthwiseDonations[10]!!.Month-1 }
        @JavascriptInterface fun getNovDonations(): Double { return monthwiseDonations[10]!!.donations }

        @JavascriptInterface fun getDecDonationYear(): Int { return monthwiseDonations[11]!!.Year }
        @JavascriptInterface fun getDecDonationMonth(): Int { return monthwiseDonations[11]!!.Month-1 }
        @JavascriptInterface fun getDecDonations(): Double { return monthwiseDonations[11]!!.donations }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        preferencesViewModel = ViewModelProvider(
            this,
            PreferencesViewModelFactory(PreferencesRepo.getInstance(applicationContext))
        )[PreferencesViewModel::class.java]
        setSupportActionBar(binding.toolbarTop)
        dbViewModel.adminDetails(
            preferencesViewModel.API_Token,
            preferencesViewModel.LoggedInUserID
        )
        dbViewModel.adminDetailsById.observe(this) {
            if (it == null) {
                Toast.makeText(this@AdminHome, "Account not found", Toast.LENGTH_SHORT).show()
            } else {
                loggedInAdminDetails = Gson().fromJson(it.data[0], Admin::class.java)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        updateAdminDetails()
    }

    override fun onResume() {
        super.onResume()
        binding.tokenPref.text =
            "API Authentication Token - " + preferencesViewModel.API_Token.length
        gettingGraphValues()
    }

    private fun updateAdminDetails() {
        GlobalScope.launch {
            delay(500L)
            withContext(Dispatchers.Main) {
                binding.idPref.text =
                    "Admin - ID: " + loggedInAdminDetails.admin_id + " Name: " + loggedInAdminDetails.admin_name
            }
        }
        Thread.sleep(100L)
    }

    fun gettingGraphValues() {
        dbViewModel.successPaymentCount(preferencesViewModel.API_Token)
        dbViewModel.success.observe(this) {
            if (it == null) {
                Toast.makeText(this@AdminHome, "Account not found", Toast.LENGTH_SHORT).show()
            } else {
                success = it.data[0].get("success_payments").asInt
            }
        }

        dbViewModel.failedPaymentsCount(preferencesViewModel.API_Token)
        dbViewModel.failed.observe(this) {
            if (it == null) {
                Toast.makeText(this@AdminHome, "Account not found", Toast.LENGTH_SHORT).show()
            } else {
                failed = it.data[0].get("failed_payments").asInt
            }
        }

        dbViewModel.newAdoptReqsCount(preferencesViewModel.API_Token)
        dbViewModel.newReq.observe(this) {
            if (it == null) {
                Toast.makeText(this@AdminHome, "Account not found", Toast.LENGTH_SHORT).show()
            } else {
                new_req = it.data[0].get("new_req").asInt
            }
        }

        dbViewModel.approvedAdoptReqsCount(preferencesViewModel.API_Token)
        dbViewModel.approved.observe(this) {
            if (it == null) {
                Toast.makeText(this@AdminHome, "Account not found", Toast.LENGTH_SHORT).show()
            } else {
                approved = it.data[0].get("approved").asInt
            }
        }

        dbViewModel.rejectedAdoptReqsCount(preferencesViewModel.API_Token)
        dbViewModel.rejected.observe(this) {
            if (it == null) {
                Toast.makeText(this@AdminHome, "Account not found", Toast.LENGTH_SHORT).show()
            } else {
                rejected = it.data[0].get("rejected").asInt
            }
        }
        dbViewModel.monthwiseDonationsTotal(preferencesViewModel.API_Token)
        dbViewModel.monthwiseDonations.observe(this) {
            if (it == null) {
                Toast.makeText(this@AdminHome, "Account not found", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("it.data.toString()",it.data.toString())
                for (donations in it.data) {
                    if (donations.get("Month").asInt==1) {
                        monthwiseDonations[0] = Gson().fromJson(donations, MonthwiseDonations::class.java)
                    }

                    if (donations.get("Month").asInt==2) {
                        monthwiseDonations[1] = (Gson().fromJson(donations, MonthwiseDonations::class.java))
                    }

                    if (donations.get("Month").asInt==3) {
                        monthwiseDonations[2] = (Gson().fromJson(donations, MonthwiseDonations::class.java))
                    }

                    if (donations.get("Month").asInt==4) {
                        monthwiseDonations[3] = (Gson().fromJson(donations, MonthwiseDonations::class.java))
                    }

                    if (donations.get("Month").asInt==5) {
                        monthwiseDonations[4] = (Gson().fromJson(donations, MonthwiseDonations::class.java))
                    }

                    if (donations.get("Month").asInt==6) {
                        monthwiseDonations[5] = (Gson().fromJson(donations, MonthwiseDonations::class.java))
                    }

                    if (donations.get("Month").asInt==7) {
                        monthwiseDonations[6] = (Gson().fromJson(donations, MonthwiseDonations::class.java))
                    }

                    if (donations.get("Month").asInt==8) {
                        monthwiseDonations[7] = (Gson().fromJson(donations, MonthwiseDonations::class.java))
                    }

                    if (donations.get("Month").asInt==9) {
                        monthwiseDonations[8] = (Gson().fromJson(donations, MonthwiseDonations::class.java))
                    }

                    if (donations.get("Month").asInt==10) {
                        monthwiseDonations[9] = (Gson().fromJson(donations, MonthwiseDonations::class.java))
                    }

                    if (donations.get("Month").asInt==11) {
                        monthwiseDonations[10] = (Gson().fromJson(donations, MonthwiseDonations::class.java))
                    }

                    if (donations.get("Month").asInt==12) {
                        monthwiseDonations[11] = (Gson().fromJson(donations, MonthwiseDonations::class.java))
                    }
                }
                Log.e("monthwiseDonations", monthwiseDonations.asList().toString())
            }
        }
        updatingGraphValues()
    }

    fun updatingGraphValues() {
        GlobalScope.launch {
            delay(500L)
            withContext(Dispatchers.Main) {
                val donationDetails = "file:///android_asset/donation_payment_details.html"
                binding.donationDetailsWebView.loadUrl(donationDetails)
                binding.donationDetailsWebView.addJavascriptInterface(WebAppInterface(), "Android")
                binding.donationDetailsWebView.settings.javaScriptEnabled = true

                val adoptRequestDetails = "file:///android_asset/adopt_request_details.html"
                binding.adoptRequestDetailsWebView.loadUrl(adoptRequestDetails)
                binding.adoptRequestDetailsWebView.addJavascriptInterface(WebAppInterface(), "Android")
                binding.adoptRequestDetailsWebView.settings.javaScriptEnabled = true

                val monthwiseDonations = "file:///android_asset/monthwise_donations.html"
                binding.monthwiseDonationDetailsWebView.loadUrl(monthwiseDonations)
                binding.monthwiseDonationDetailsWebView.addJavascriptInterface(WebAppInterface(), "Android")
                binding.monthwiseDonationDetailsWebView.settings.javaScriptEnabled = true
            }
            Thread.sleep(100L)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        menu!!.removeItem(R.id.Home)
        menu.removeItem(R.id.donate)
        menu.removeItem(R.id.adopt)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                item.isVisible = true
                finish()
                true
            }
            R.id.Home -> {
                intent = Intent(this, AdminHome::class.java)
                startActivity(intent)
                return true
            }
            R.id.settings -> {
                intent = Intent(this, AdminAccountSettings::class.java)
                startActivity(intent)
                return true
            }
            R.id.Logout -> {
                Toast.makeText(this@AdminHome, "Logout", Toast.LENGTH_SHORT).show()
                GlobalScope.launch {
                    preferencesViewModel.setSavedKey(false)
                    preferencesViewModel.setAPI_Token("")
                    preferencesViewModel.setLoggedInUserID(0)
                    preferencesViewModel.setRole("")
                }
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}