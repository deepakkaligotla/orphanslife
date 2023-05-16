package com.kaligotla.orphanslife.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.kaligotla.orphanslife.data.repository.MainRepository
import com.kaligotla.orphanslife.model.response.LoginBody
import com.kaligotla.orphanslife.model.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: MainRepository) :ViewModel() {
    var _createPostLiveData: MutableLiveData<LoginResponse?> = MutableLiveData()
    val createPostLiveData get() = _createPostLiveData

    fun login(loginBody: LoginBody): Any = viewModelScope.launch {
        repository.login(loginBody).collect{
            it.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val otp: String = response.body()?.otp.toString()
                        Log.e("response.body()?.otp.toString()",response.body()?.otp.toString())
                        if(response.body()?.data?.isNotEmpty() == true) {
                            val data: List<JsonObject> = response.body()?.data!!
                            Log.e("logged in user", "res :${data.get(0).get("loggedInUser")}")
                            _createPostLiveData.value = response.body()
                        } else {
                            _createPostLiveData.value = null
                            Log.e("No Account found","No Account found")
                        }
                    } else {
                        _createPostLiveData.value = null
                        Log.d("failed", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                    _createPostLiveData.value = null
                }
            })
        }
    }
}