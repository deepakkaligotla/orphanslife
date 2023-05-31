package com.kaligotla.orphanslife.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
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

    var _adminDetailsById: MutableLiveData<NodeDBResponse?> = MutableLiveData()
    val adminDetailsById get() = _adminDetailsById
    var _success: MutableLiveData<NodeDBResponse?> = MutableLiveData()
    val success get() = _success
    var _failed: MutableLiveData<NodeDBResponse?> = MutableLiveData()
    val failed get() = _failed
    var _newReq: MutableLiveData<NodeDBResponse?> = MutableLiveData()
    val newReq get() = _newReq
    var _approved: MutableLiveData<NodeDBResponse?> = MutableLiveData()
    val approved get() = _approved
    var _rejected: MutableLiveData<NodeDBResponse?> = MutableLiveData()
    val rejected get() = _rejected
    var _monthwiseDonations: MutableLiveData<NodeDBResponse?> = MutableLiveData()
    val monthwiseDonations get() = _monthwiseDonations
    var _orphanages: MutableLiveData<NodeDBResponse?> = MutableLiveData()
    val orphanages get() = _orphanages
    var _admins: MutableLiveData<NodeDBResponse?> = MutableLiveData()
    val admins get() = _admins
    var _locations: MutableLiveData<NodeDBResponse?> = MutableLiveData()
    val locations get() = _locations

    fun adminDetails(apiToken: String, id: Int): Any = viewModelScope.launch {
        dbRepo.getAdminDetailsByID(apiToken,id).collect{
            it.enqueue(object : Callback<NodeDBResponse> {
                override fun onResponse(call: Call<NodeDBResponse>, response: Response<NodeDBResponse>) {
                    if (response.isSuccessful) {
                        Log.e("response.body()",response.body().toString())
                        if(response.body()?.data?.isNotEmpty() == true) {
                            _adminDetailsById.value = response.body()
                        } else {
                            _adminDetailsById.value = null
                            Log.e("No Account found","No Account found")
                        }
                    } else {
                        _adminDetailsById.value = null
                        Log.d("failed", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<NodeDBResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                    _adminDetailsById.value = null
                }
            })
        }
    }

    fun successPaymentCount(apiToken: String): Any = viewModelScope.launch {
        dbRepo.getSuccessPaymentCount(apiToken).collect{
            it.enqueue(object : Callback<NodeDBResponse> {
                override fun onResponse(call: Call<NodeDBResponse>, response: Response<NodeDBResponse>) {
                    if (response.isSuccessful) {
                        if(response.body()?.data?.isNotEmpty() == true) {
                            _success.value = response.body()
                        } else {
                            _success.value = null
                            Log.e("No Success Payment Count","No Success Payment Count")
                        }
                    } else {
                        _success.value = null
                        Log.d("failed", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<NodeDBResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                    _success.value = null
                }
            })
        }
    }

    fun failedPaymentsCount(apiToken: String): Any = viewModelScope.launch {
        dbRepo.getFailedPaymentsCount(apiToken).collect{
            it.enqueue(object : Callback<NodeDBResponse> {
                override fun onResponse(call: Call<NodeDBResponse>, response: Response<NodeDBResponse>) {
                    if (response.isSuccessful) {
                        if(response.body()?.data?.isNotEmpty() == true) {
                            _failed.value = response.body()
                        } else {
                            _failed.value = null
                            Log.e("No Failed Payment Count","No Failed Payment Count")
                        }
                    } else {
                        _failed.value = null
                        Log.d("failed", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<NodeDBResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                    _failed.value = null
                }
            })
        }
    }

    fun newAdoptReqsCount(apiToken: String): Any = viewModelScope.launch {
        dbRepo.getNewAdoptReqsCount(apiToken).collect{
            it.enqueue(object : Callback<NodeDBResponse> {
                override fun onResponse(call: Call<NodeDBResponse>, response: Response<NodeDBResponse>) {
                    if (response.isSuccessful) {
                        if(response.body()?.data?.isNotEmpty() == true) {
                            _newReq.value = response.body()
                        } else {
                            _newReq.value = null
                            Log.e("No Account found","No Account found")
                        }
                    } else {
                        _newReq.value = null
                        Log.d("failed", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<NodeDBResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                    _newReq.value = null
                }
            })
        }
    }

    fun approvedAdoptReqsCount(apiToken: String): Any = viewModelScope.launch {
        dbRepo.getApprovedAdoptReqsCount(apiToken).collect{
            it.enqueue(object : Callback<NodeDBResponse> {
                override fun onResponse(call: Call<NodeDBResponse>, response: Response<NodeDBResponse>) {
                    if (response.isSuccessful) {
                        if(response.body()?.data?.isNotEmpty() == true) {
                            _approved.value = response.body()
                        } else {
                            _approved.value = null
                            Log.e("No Account found","No Account found")
                        }
                    } else {
                        _approved.value = null
                        Log.d("failed", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<NodeDBResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                    _approved.value = null
                }
            })
        }
    }

    fun rejectedAdoptReqsCount(apiToken: String): Any = viewModelScope.launch {
        dbRepo.getRejectedAdoptReqsCount(apiToken).collect{
            it.enqueue(object : Callback<NodeDBResponse> {
                override fun onResponse(call: Call<NodeDBResponse>, response: Response<NodeDBResponse>) {
                    if (response.isSuccessful) {
                        if(response.body()?.data?.isNotEmpty() == true) {
                            _rejected.value = response.body()
                        } else {
                            _rejected.value = null
                            Log.e("No Account found","No Account found")
                        }
                    } else {
                        _rejected.value = null
                        Log.d("failed", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<NodeDBResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                    _rejected.value = null
                }
            })
        }
    }

    fun monthwiseDonationsTotal(apiToken: String): Any = viewModelScope.launch {
        dbRepo.getMonthwiseDonationsTotal(apiToken).collect{
            it.enqueue(object : Callback<NodeDBResponse> {
                override fun onResponse(call: Call<NodeDBResponse>, response: Response<NodeDBResponse>) {
                    if (response.isSuccessful) {
                        if(response.body()?.data?.isNotEmpty() == true) {
                            _monthwiseDonations.value = response.body()
                        } else {
                            _monthwiseDonations.value = null
                            Log.e("No Monthwise Donations found","No Monthwise Donations found")
                        }
                    } else {
                        _monthwiseDonations.value = null
                        Log.d("failed", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<NodeDBResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                    _monthwiseDonations.value = null
                }
            })
        }
    }

    fun getOrphanages(apiToken: String): Any = viewModelScope.launch {
        dbRepo.getOrphanages(apiToken).collect{
            it.enqueue(object : Callback<NodeDBResponse> {
                override fun onResponse(call: Call<NodeDBResponse>, response: Response<NodeDBResponse>) {
                    if (response.isSuccessful) {
                        if(response.body()?.data?.isNotEmpty() == true) {
                            _orphanages.value = response.body()
                        } else {
                            _orphanages.value = null
                            Log.e("No Account found","No Account found")
                        }
                    } else {
                        _orphanages.value = null
                        Log.d("failed", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<NodeDBResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                    _orphanages.value = null
                }
            })
        }
    }

    fun getAdmins(apiToken: String): Any = viewModelScope.launch {
        dbRepo.getAdmins(apiToken).collect{
            it.enqueue(object : Callback<NodeDBResponse> {
                override fun onResponse(call: Call<NodeDBResponse>, response: Response<NodeDBResponse>) {
                    if (response.isSuccessful) {
                        if(response.body()?.data?.isNotEmpty() == true) {
                            _admins.value = response.body()
                        } else {
                            _admins.value = null
                            Log.e("No Account found","No Account found")
                        }
                    } else {
                        _admins.value = null
                        Log.d("failed", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<NodeDBResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                    _admins.value = null
                }
            })
        }
    }

    fun getLocations(apiToken: String): Any = viewModelScope.launch {
        dbRepo.getLocations(apiToken).collect{
            it.enqueue(object : Callback<NodeDBResponse> {
                override fun onResponse(call: Call<NodeDBResponse>, response: Response<NodeDBResponse>) {
                    if (response.isSuccessful) {
                        if(response.body()?.data?.isNotEmpty() == true) {
                            _locations.value = response.body()
                        } else {
                            _locations.value = null
                            Log.e("No Account found","No Account found")
                        }
                    } else {
                        _locations.value = null
                        Log.d("failed", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<NodeDBResponse>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                    _locations.value = null
                }
            })
        }
    }
}