package com.kaligotla.orphanslife.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaligotla.orphanslife.data.repository.MainRepository
import com.kaligotla.orphanslife.model.LoginBody
import com.kaligotla.orphanslife.model.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: MainRepository):ViewModel() {

    fun login(loginBody: LoginBody) = viewModelScope.launch {
        repository.login(loginBody).collect{
            it.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    Log.e("response", "res :${response}")
                    val otp = response.body()?.otp
                    Log.e("otp", "res :${otp.toString()}")
                    val token = response.body()?.token
                    Log.e("token", "res :${token.toString()}")
                    val data = response.body()?.data
                    Log.e("logged in user", "res :${data?.get(0)?.loggedInUser.toString()}")
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                }
            })
        }
    }
}