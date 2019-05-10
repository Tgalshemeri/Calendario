package com.elmaravilla.customcalendar

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class eventsAdapter(val context: Context , val eventsArray:ArrayList<String>, val timeArrayList: ArrayList<String>) : RecyclerView.Adapter<eventsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.event_item , p0 , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return  eventsArray.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.eventText.text = eventsArray[p1]
        p0.eventText.textSize = 18f
        p0.eventText.setTextColor(Color.BLACK)
        p0.timeText.text = timeArrayList[p1]
        p0.timeText.textSize = 18f
        p0.timeText.setTextColor(Color.BLACK)
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val eventText = itemView.findViewById<TextView>(R.id.textTitle)
        val timeText = itemView.findViewById<TextView>(R.id.textTime)
    }


}