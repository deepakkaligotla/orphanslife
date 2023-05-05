package com.kaligotla.orphanslife.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kaligotla.orphanslife.global.ApplicationScope
import com.kaligotla.orphanslife.model.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [Admin::class, Sponsor::class, Child::class, Orphanage::class, Role::class,
               AdoptiveStatus::class, AdoptReq::class, Location::class, Donation::class,
               OrphanageActivities::class],
    version = 1
)
abstract class OrphanslifeDB: RoomDatabase() {

    abstract fun getAdminDao(): AdminDao
    abstract fun getSponsorDao(): SponsorDao
    abstract fun getChildDao(): ChildDao
    abstract fun getOrphanageDao(): OrphanageDao
    abstract fun getRoleDao(): RoleDao
    abstract fun getAdoptiveStatusDao(): AdoptiveStatusDao
    abstract fun getAdoptReqDao(): AdoptReqDao
    abstract fun getDonationDao(): DonationDao
    abstract fun getLocationDao(): LocationDao
    abstract fun getOrphanageActivitiesDao(): OrphanageActivitiesDao

    class Callback @Inject constructor(private val orphanslifeDB: Provider<OrphanslifeDB>, @ApplicationScope private val applicationScope: CoroutineScope) : androidx.room.RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val adminDao = orphanslifeDB.get().getAdminDao()
            val sponsoDao = orphanslifeDB.get().getSponsorDao()
            applicationScope.launch {

            }
        }
    }
}