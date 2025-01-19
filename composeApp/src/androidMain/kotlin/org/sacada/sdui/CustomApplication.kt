package org.sacada.sdui

import android.app.Application
import initKoin
import org.koin.core.component.KoinComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class CustomApplication : Application(), KoinComponent {
//    lateinit var database: AppDatabase
//        private set

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@CustomApplication)
        }
//        database = Room.databaseBuilder(
//            applicationContext, AppDatabase::class.java, "app_database"
//        ).fallbackToDestructiveMigration().build()
    }
}
