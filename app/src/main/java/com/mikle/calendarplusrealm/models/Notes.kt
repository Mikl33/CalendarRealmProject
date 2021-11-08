package com.mikle.calendarplusrealm.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass


@RealmClass
open class Notes: RealmObject() {
    @PrimaryKey()
    var id: Int? = null
    var dateStart: Long? = 1
    var dateFinish: Long? = 0
    var title: String? = ""
    var description: String? = ""
}