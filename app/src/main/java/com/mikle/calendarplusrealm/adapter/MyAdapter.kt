package com.mikle.calendarplusrealm.adapter


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikle.calendarplusrealm.R
import com.mikle.calendarplusrealm.models.Notes
import io.realm.RealmResults
import com.mikle.calendarplusrealm.MainActivity


//public class Class2 extends Activity{}

class MyAdapter (selectedDate: Long, ListMain: RealmResults<Notes>, contextM: Context):RecyclerView.Adapter<MyAdapter.MyHolder>() {
    var context = contextM
    var listArray = ListMain
    var selectedDate = selectedDate


    class MyHolder( itemView: View,contextViewHolder: Context, selectedDate: Long): RecyclerView.ViewHolder(itemView){
        val context = contextViewHolder
        val selectedDate = selectedDate

       fun setData(item: Notes?){
           for (i in 0..23) {
                 Log.d("MyIntent", "item datestart - ${item!!.dateStart}")
               Log.d("MyIntent", "item datefinish - ${item!!.dateFinish}")

               var selectedTimestamp = selectedDate + 3600000 * i
               Log.d("MyIntent", "selectedDate + i = ${selectedTimestamp}")

               //var tvTasks : ?TextView = itemView.findViewWithTag("tasks${i}")

           //    tvInterval.text = "${i}!"
           //    tvTasks.text = "${i}!!!"
           }
         /*   tvId.text = item!!.id.toString()
           tvStart.text = item!!.dateStart
           tvEnd.text = item!!.dateFinish
           tvNote.text = item!!.description*/

       }

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.rcv_item, parent, false), context, selectedDate)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyHolder, position: Int) {
        holder.setData(listArray.get(position))

    }

    override fun getItemCount(): Int {
        return listArray.size

    }
}