package com.mikle.calendarplusrealm.TimePIker

import android.app.Application
import android.widget.Button
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.mikle.calendarplusrealm.R
import java.util.*

class UserTime : Application() {
    override fun onCreate() {
        super.onCreate()

      /*  fun setTime() {
            var startTime : Button? = null
            startTime?.setOnClickListener {
                var mTimePicker = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Установите время")
                    .build()

                mTimePicker.addOnPositiveButtonClickListener {
                    var calendarTime: Calendar = Calendar.getInstance()
                    calendarTime.set(Calendar.SECOND, 0)
                    calendarTime.set(Calendar.MILLISECOND, 0)
                    calendarTime.set(Calendar.MINUTE, mTimePicker.minute)
                    calendarTime.set(Calendar.HOUR_OF_DAY, mTimePicker.hour)
                }
            //    mTimePicker.show(supportFragmentManager, "tag_picker")
            }
        }*/


    }
}