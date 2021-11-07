package com.mikle.calendarplusrealm.db

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyDb:Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val configuration = RealmConfiguration.Builder()
            .name("Notes.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(6)
            .build()
        Realm.setDefaultConfiguration(configuration)
    }
}