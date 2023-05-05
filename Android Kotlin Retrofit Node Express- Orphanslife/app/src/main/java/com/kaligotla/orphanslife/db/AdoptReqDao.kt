package com.kaligotla.orphanslife.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kaligotla.orphanslife.model.entity.AdoptReq

@Dao
interface AdoptReqDao {

    @Query("select * from adopt_req;")
    fun getAllAdoptRequests(): LiveData<List<AdoptReq>>
}