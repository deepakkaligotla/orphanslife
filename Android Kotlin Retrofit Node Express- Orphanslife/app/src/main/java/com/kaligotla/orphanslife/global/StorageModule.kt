package com.kaligotla.orphanslife.global

import com.kaligotla.orphanslife.preferences.PreferenceImpl
import com.kaligotla.orphanslife.preferences.PreferenceStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    abstract fun bindsPreferenceStorage(preferenceStorageImpl: PreferenceImpl): PreferenceStorage

}