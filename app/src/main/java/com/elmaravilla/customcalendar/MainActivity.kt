package com.elmaravilla.customcalendar

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.treaf.calendario.Calendario


class MainActivity : FragmentActivity() , Calendario.OnDateSelectedListener {
    override fun onDateSelected(day: Int, month: Int, year: Int) {
        val eventsArray = customCalendario.getEventsByDate(day, month, year).getEvent
        val timeArray   = customCalendario.getEventsByDate(day, month, year).getTime
        if (eventsArray.isEmpty()){
            recyclerView.visibility = View.GONE
            eventsInfo.visibility = View.VISIBLE
            eventsInfo.text = "No Events"
            eventsInfo.textSize = 20f
            eventsInfo.setTextColor(Color.BLACK)
        } else {
            eventsInfo.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            val eventAdapter = eventsAdapter(this , eventsArray , timeArray)
            recyclerView.adapter = eventAdapter
            recyclerView.addItemDecoration(DividerItemDecoration(this , DividerItemDecoration.VERTICAL))

        //Removing Events By Clicking On The RecycleView
            recyclerView.addOnItemClickListener(object : OnItemClickListener {
                override fun onItemClicked(position: Int, view: View) {
                    customCalendario.removeEvent(eventsArray[position] , day, month, year)
                }

            })
        }


    }
    private lateinit var customCalendario: Calendario
    lateinit var recyclerView: RecyclerView
    lateinit var eventsInfo:TextView
    lateinit var btnAdd:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        customCalendario = findViewById(R.id.custom)
        recyclerView = findViewById(R.id.events_Recycler)
        eventsInfo = findViewById(R.id.eventsInfo)
        btnAdd = findViewById(R.id.btnAdd)
        recyclerView.visibility = View.GONE
        customCalendario.setStartDayOfWeek(2)
        customCalendario.setSelectionType(1)
        customCalendario.setOnDateListener(this)
        btnAdd.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            val newFragment = addEventDialog()
            newFragment.show(ft, "dialog")
        }


    }


    //Removing Events By Clicking On The RecycleView
    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }
    fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(p0: View) {
                p0?.setOnClickListener(null)
            }

            override fun onChildViewAttachedToWindow(p0: View) {
                p0?.setOnClickListener {
                    val holder = getChildViewHolder(p0)
                    onClickListener.onItemClicked(holder.adapterPosition, p0)
                }
            }

        })
    }

}
