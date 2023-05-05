package com.kaligotla.orphanslife.global

import android.app.Application
import androidx.room.Room
import com.kaligotla.orphanslife.db.OrphanslifeDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: OrphanslifeDB.Callback)
            = Room.databaseBuilder(application, OrphanslifeDB::class.java, "orphanslife")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun providesAdminDao(orphandlifeDB: OrphanslifeDB) =
        orphandlifeDB.getAdminDao()
    @ApplicationScope
    @Provides
    @Singleton
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope