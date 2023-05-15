package com.kaligotla.orphanslife.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kaligotla.orphanslife.model.entity.*

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
}