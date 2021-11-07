package com.mikle.calendarplusrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mikle.calendarplusrealm.adapter.MyAdapter
import com.mikle.calendarplusrealm.models.Notes
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import org.json.JSONArray
import org.json.JSONException
import java.io.InputStream
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*
import kotlin.collections.ArrayList

class MainActivity :  AppCompatActivity() {


private  lateinit var  addNotes: FloatingActionButton
private  lateinit var  notseRV: RecyclerView
private  lateinit var  results: RealmResults<Notes>
private  lateinit var realm: Realm
private var datePick = ""
    private var tMilis = null
   private var selectedDate: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()
        addNotes = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        notseRV = findViewById(R.id.recyclerV)
        notseRV.layoutManager = LinearLayoutManager(this)

        getAllNotes()
        curentDate()

        val calendarView = findViewById<CalendarView>(R.id.calendarView)

        calendarView.setOnDateChangeListener {view, year, month, dayOfMonth ->
            datePick =  "$dayOfMonth.${month + 1}.$year"


            var dat  = SimpleDateFormat("dd.MM.yyyy")
            val date: Date = dat.parse(datePick )
            selectedDate = date.time

            Log.d("MyIntent", "$selectedDate")
            Log.d("MyIntent", "выбранное время $datePick")

            notseRV.adapter = MyAdapter(selectedDate!!, results, this)
            notseRV.adapter!!.notifyDataSetChanged()
        }



    }

    fun curentDate() {
       var tC: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")

        var currentT = Calendar.getInstance()
        currentT.set(Calendar.SECOND, 0)
        currentT.set(Calendar.MILLISECOND, 0)
        currentT.set(Calendar.MINUTE, 0)
        currentT.set(Calendar.HOUR_OF_DAY, 0)

        var obsolutTime = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(currentT.timeInMillis)
        var oT = tC.parse(obsolutTime)
        selectedDate = oT.time

        Log.d("MyIntent","ТЕкущее время $obsolutTime")
        Log.d("MyIntent","ТЕкущее время в миллисекундах $selectedDate")

        notseRV.adapter = MyAdapter(selectedDate!!, results, this)
        notseRV.adapter!!.notifyDataSetChanged()
    }
    fun onClikNotes(view: View) {
        if (datePick !== null){
            var intentA = Intent(this, BloknotActivity::class.java)
                intentA.putExtra("key", "$datePick")
                intentA.putExtra("key1","$selectedDate")

            // startActivityForResult(intent,100)
            startActivity(intentA)
        } else{
            var intentB = Intent(this, BloknotActivity::class.java)
            intentB.putExtra("key2", "$tMilis")
            startActivity(intentB)
        }
    }

    fun addN(view: View) {

        var intent = Intent(this, BloknotActivity::class.java)
        startActivity(intent)
    }

    private fun getAllNotes() {
        results = realm.where<Notes>(Notes::class.java).findAll()
        //var finalList: RealmList<Notes> = realm.copyFromRealm(results)
        var test = results.asJSON()

        notseRV.adapter = MyAdapter(selectedDate, results, this)
        notseRV.adapter!!.notifyDataSetChanged()
    }

    public fun getSelectedDate():Long {
        return selectedDate
    }
}
