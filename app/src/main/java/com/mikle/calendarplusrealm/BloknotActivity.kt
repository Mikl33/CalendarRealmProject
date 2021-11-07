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
import com.mikle.calendarplusrealm.models.Notes
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_bloknot.*
import kotlinx.android.synthetic.main.rcv_item.*
import org.json.JSONArray
import org.json.JSONException
import java.io.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class BloknotActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private var selectedDate: Long = 0
    private var selectedStartDate: Long = 0
    private var selectedFinishDate: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bloknot)

     //   Log.d("Log","mesage")
        realm = Realm.getDefaultInstance()

        getMyIntents()
      //  var startTime = findViewById<Button>(R.id.btnStartTime)
        setTime()
        setTime2()
        onClikeSave(View(this))
    }




    override fun onResume() {
        super.onResume()
      // realm = Realm.getDefaultInstance()
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

                mTimePicker.addOnPositiveButtonClickListener{
                  var  calendarTime: Calendar = Calendar.getInstance()
                    calendarTime.setTimeInMillis(selectedDate)

                    calendarTime.set(Calendar.SECOND, 0)
                    calendarTime.set(Calendar.MILLISECOND, 0)
                    calendarTime.set(Calendar.MINUTE, mTimePicker.minute)
                    calendarTime.set(Calendar.HOUR_OF_DAY, mTimePicker.hour)

                  var userT = findViewById<TextView>(R.id.textView)
                    userT.text = SimpleDateFormat("HH:mm").format(calendarTime.time)

                    selectedStartDate = calendarTime.timeInMillis
                    Log.d("MyIntent", "Aбсолютное время начала $selectedStartDate" )
                  //  var obsolute = selectedDate + obsolutTime
                }
                mTimePicker.show(supportFragmentManager,"tag_picker")


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

            mTimePicker.addOnPositiveButtonClickListener{
              var  calendarTime: Calendar = Calendar.getInstance()
                calendarTime.setTimeInMillis(selectedDate)
                calendarTime.set(Calendar.SECOND, 0)
                calendarTime.set(Calendar.MILLISECOND, 0)
                calendarTime.set(Calendar.MINUTE, mTimePicker.minute)
                calendarTime.set(Calendar.HOUR_OF_DAY, mTimePicker.hour)
              var userT = findViewById<TextView>(R.id.textView2)
                selectedFinishDate = calendarTime.timeInMillis
                Log.d("MyIntent", "Aбсолютное время конца $selectedFinishDate" )
                userT.text = SimpleDateFormat("HH:mm").format(calendarTime.time)
            }
            mTimePicker.show(supportFragmentManager,"tag_picker")
        }
    }




    fun onClikeSave(view: android.view.View) {
       floatingActionButton2.setOnClickListener{
            addNotesToDb()
       }
    }

    private fun addNotesToDb() {

        try {

            realm.beginTransaction()
            val currentIdNumber: Number? = realm.where(Notes::class.java).max("id", )
            val nextId: Long

            nextId = if(currentIdNumber == null){
                1
            } else{
                currentIdNumber.toLong() + 1
            }

            val notes = Notes()

            notes.id = nextId



            notes.dateStart = selectedStartDate
            notes.dateFinish= selectedFinishDate
            notes.title = editTextTitle.text.toString()
            notes.description = editTextNoteContent.text.toString()



            realm.copyToRealmOrUpdate(notes)
            realm.commitTransaction()

            Toast.makeText(this,"Notes Add", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()

        } catch (e:Exception){
            Toast.makeText(this,"Failid", Toast.LENGTH_LONG).show()
        }

    }

    fun getMyIntents(){
        val  i = intent
        if (i != null){
            selectedDate = i.getStringExtra("key1")!!.toLong()
            Log.d("MyIntent", "Data putExtra   " + selectedDate)

        /*    if (intent.getStringExtra("key2") !== null){
                //  var mesage: String = ""
                // var mesage = intent.getStringExtra("key")
                ed.setText(intent.getStringExtra("key3"))

            } else if (i.getStringExtra(MyIntentConstants.INTENT_TITLE_KEY) !== "" ) {
                ImageLayout.visibility = View.VISIBLE
                editTextZagolovok.setText(i.getStringExtra(MyIntentConstants.INTENT_TITLE_KEY))
                editTextNoteContent.setText(i.getStringExtra(MyIntentConstants.INTENT_NOTE_KEY))
                //            imageView.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstants.INTENT_URI_KEY)))
                btAddImageClick.visibility = View.GONE


                if (i.getStringExtra(MyIntentConstants.INTENT_URI_KEY) == "empty"){
                    ImageLayout.visibility = View.GONE
                    //  imageButtonEdite.visibility = View.GONE
                    // imageDelet.visibility = View.GONE
                    btAddImageClick.visibility = View.VISIBLE


                }

            }*/
        }
    }



}


