package com.mikle.calendarplusrealm.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikle.calendarplusrealm.BloknotActivity
import com.mikle.calendarplusrealm.R
import com.mikle.calendarplusrealm.models.Notes
import io.realm.RealmResults
import com.mikle.calendarplusrealm.db.MyIntentConstants
import java.text.SimpleDateFormat




class MyAdapter(ListMain: RealmResults<Notes>, contextM: Context, selectedDate: Long, i: Int) :
    RecyclerView.Adapter<MyAdapter.MyHolder>() {
    var i: Int = 0
    var context = contextM
    var listArray = ListMain
    var selectedDate = selectedDate


    class MyHolder(itemView: View, contextViewHolder: Context, selectedDate: Long) :
        RecyclerView.ViewHolder(itemView) {
        val context = contextViewHolder
        val selectedDate = selectedDate
        var i = 0

        val tvId = itemView.findViewById<TextView>(R.id.tvId)
        val tvTitle: TextView = itemView.findViewById(R.id.title)
        val tvStart: TextView = itemView.findViewById(R.id.dataStart)
        val tvEnd: TextView = itemView.findViewById(R.id.dataFinish)
        val tvNote: TextView = itemView.findViewById(R.id.noteDescription)


        fun setData(item: Notes?) {

            val dateStart = SimpleDateFormat("HH:mm").format(item!!.dateStart)
            val dateFinish = SimpleDateFormat("HH:mm").format(item!!.dateFinish)

            tvId.text = item!!.id.toString()
            tvTitle.text = item!!.title.toString()
            tvStart.text = dateStart
            tvEnd.text = dateFinish
            tvNote.text = item!!.description


            itemView.setOnClickListener {

                val intentBloknot = Intent(context, BloknotActivity::class.java).apply {
                    putExtra(MyIntentConstants.INTENT_ID, item.id.toString())
                    putExtra(MyIntentConstants.INTENT_TITLE, item.title)
                    putExtra(MyIntentConstants.INTENT_DESCRIPTION, item.description)
                    putExtra(MyIntentConstants.INTENT_DATE_FINISH, item.dateFinish.toString())
                    putExtra(MyIntentConstants.INTENT_DATE_START, item.dateStart.toString())
                }
                context.startActivity(intentBloknot)
            }
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