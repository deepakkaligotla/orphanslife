package com.kaligotla.orphanslife.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.kaligotla.orphanslife.data.repository.DBRepo
import com.kaligotla.orphanslife.model.response.LoginResponse
import com.kaligotla.orphanslife.model.response.NodeDBResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DBViewModel (private val dbRepo: DBRepo): ViewModel() {

    var _createPostLiveData: MutableLiveData<NodeDBResponse?> = MutableLiveData()
    val createPostLiveData get() = _createPostLiveData

    fun adminDetails(apiToken: String, id: Int): Any = viewModelScope.launch {
        dbRepo.getAdminDetailsByID(apiToken,id).collect{
            it.enqueue(object : Callback<NodeDBResponse> {
                override fun onResponse(call: Call<NodeDBResponse>, response: Response<NodeDBResponse>) {
                    if (response.isSuccessful) {
                        if(response.body()?.data?.isNotEmpty() == true) {
                            val data: List<JsonObject> = response.body()?.data!!
                            Log.e("logged in user", "res :${data.get(0)}")
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

                override fun onFailure(call: Call<NodeDBResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                    _createPostLiveData.value = null
                }
            })
        }
    }
}