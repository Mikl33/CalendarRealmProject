package com.mikle.calendarplusrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.mikle.calendarplusrealm.db.MyIntentConstants
import com.mikle.calendarplusrealm.models.Notes
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_bloknot.*

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class BloknotActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private var selectedDate: Long = 0
    private var selectedStartDate: Long = 0
    private var selectedFinishDate: Long = 0
    private var selectedId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bloknot)


        realm = Realm.getDefaultInstance()

        getMyIntents()

        setTime()
        setTime2()
        onClikeSave(View(this))
    }


    override fun onResume() {
        super.onResume()

    }

    fun setTime() {

        var startTime = findViewById<Button>(R.id.btnStartTime)

        startTime.setOnClickListener {
            var mTimePicker = MaterialTimePicker.Builder()

                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Установите время")
                .build()

            mTimePicker.addOnPositiveButtonClickListener {
                var calendarTime: Calendar = Calendar.getInstance()
                calendarTime.setTimeInMillis(selectedDate)

                calendarTime.set(Calendar.SECOND, 0)
                calendarTime.set(Calendar.MILLISECOND, 0)
                calendarTime.set(Calendar.MINUTE, mTimePicker.minute)
                calendarTime.set(Calendar.HOUR_OF_DAY, mTimePicker.hour)

                var userT = findViewById<TextView>(R.id.tvTimeStart)
                userT.text = SimpleDateFormat("HH:mm").format(calendarTime.time)

                selectedStartDate = calendarTime.timeInMillis
                Log.d("MyIntent", "Aбсолютное время начала $selectedStartDate")

            }
            mTimePicker.show(supportFragmentManager, "tag_picker")


        }
    }

    fun setTime2() {

        var endTime = findViewById<Button>(R.id.btnFinishTime)

        endTime.setOnClickListener {
            var mTimePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Установите время")
                .build()

            mTimePicker.addOnPositiveButtonClickListener {
                var calendarTime: Calendar = Calendar.getInstance()
                calendarTime.setTimeInMillis(selectedDate)
                calendarTime.set(Calendar.SECOND, 0)
                calendarTime.set(Calendar.MILLISECOND, 0)
                calendarTime.set(Calendar.MINUTE, mTimePicker.minute)
                calendarTime.set(Calendar.HOUR_OF_DAY, mTimePicker.hour)
                var userT = findViewById<TextView>(R.id.tvTimeFinish)
                selectedFinishDate = calendarTime.timeInMillis
                Log.d("MyIntent", "Aбсолютное время конца $selectedFinishDate")
                userT.text = SimpleDateFormat("HH:mm").format(calendarTime.time)
            }
            mTimePicker.show(supportFragmentManager, "tag_picker")
        }
    }


    /*   fun onClikeEdit(view: android.view.View) {
           floatingActionButton2.setOnClickListener{
               addNotesToDb(id)
           }
       }*/


    fun onClikeSave(view: android.view.View) {
        btAddNote.setOnClickListener {
            addNotesToDb()
        }
    }


    private fun addNotesToDb() {

        try {

            realm.beginTransaction()

            val notes = Notes()

            if (selectedId == 0) {
                val currentIdNumber: Number? = realm.where(Notes::class.java).max("id")
                val nextId: Int

                nextId = if (currentIdNumber == null) {
                    1
                } else {
                    currentIdNumber.toInt() + 1
                }

                notes.id = nextId
            } else {
                notes.id = selectedId
            }


            notes.dateStart = selectedStartDate
            notes.dateFinish = selectedFinishDate
            notes.title = editTextTitle.text.toString()
            notes.description = editTextNoteContent.text.toString()



            realm.copyToRealmOrUpdate(notes)
            realm.commitTransaction()

            Toast.makeText(this, "Notes Add", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } catch (e: Exception) {
            Toast.makeText(this, "Failid", Toast.LENGTH_LONG).show()
        }

    }

    fun getMyIntents() {
        val i = intent
        if (i != null) {
            if (i.getStringExtra("key1") !== null) { // Если новая задача
                selectedDate = i.getStringExtra("key1")!!.toLong()
                selectedId = 0
                Log.d("MyIntent", "Data putExtra   " + selectedDate)
                btAddNote.visibility = View.VISIBLE
                rewriteNote.visibility = View.GONE
            } else if (i.getStringExtra(MyIntentConstants.INTENT_ID) !== null) { // Если мы редактируем задачу
                editTextTitle.setText(i.getStringExtra(MyIntentConstants.INTENT_TITLE))
                editTextNoteContent.setText(i.getStringExtra(MyIntentConstants.INTENT_DESCRIPTION))

                selectedStartDate = i.getStringExtra(MyIntentConstants.INTENT_DATE_START)!!.toLong()
                tvTimeStart.setText(SimpleDateFormat("HH:mm").format(selectedStartDate))

                selectedFinishDate =
                    i.getStringExtra(MyIntentConstants.INTENT_DATE_FINISH)!!.toLong()
                tvTimeFinish.setText(SimpleDateFormat("HH:mm").format(selectedFinishDate))

                selectedId = i.getStringExtra(MyIntentConstants.INTENT_ID)!!.toInt()
                btAddNote.visibility = View.VISIBLE
                rewriteNote.visibility = View.GONE
            }

        }
    }


}


