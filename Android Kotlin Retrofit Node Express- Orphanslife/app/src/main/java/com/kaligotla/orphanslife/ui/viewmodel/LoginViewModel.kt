package com.kaligotla.orphanslife.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import com.kaligotla.orphanslife.data.repository.MainRepository
import com.kaligotla.orphanslife.model.FromDB
import com.kaligotla.orphanslife.model.LoginBody
import com.kaligotla.orphanslife.model.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: MainRepository):ViewModel() {

    var fromDB = FromDB()

    fun Login(loginBody: LoginBody) = viewModelScope.launch {
        repository.login(loginBody).collect{
            it.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    Log.e("onResponse", "res :${response}")
                    fromDB.otp = response.body()?.otp
                    Log.e("onResponse", "res :${fromDB.otp.toString()}")
                    fromDB.token = response.body()?.token
                    Log.e("onResponse", "res :${fromDB.token.toString()}")
                    fromDB.loggedInUser = response.body()?.data
                    Log.e("onResponse", "res :${fromDB.loggedInUser.toString()}")
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                }
            })
        }
    }

}