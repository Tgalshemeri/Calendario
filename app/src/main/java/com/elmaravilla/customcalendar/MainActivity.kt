package com.elmaravilla.customcalendar

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
//import androidx.fragment.app.FragmentActivity
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
            val eventAdapter = eventsAdapter(this , eventsArray , timeArray , object : ItemClick {
                override fun getItemClick(position: Int) {
                    customCalendario.removeEvent(eventsArray[position] , day, month, year)
                    recyclerView.adapter?.notifyDataSetChanged()
                    Toast.makeText(this@MainActivity , "Successfully" , Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }

            })
            recyclerView.adapter = eventAdapter
            recyclerView.addItemDecoration(DividerItemDecoration(this , DividerItemDecoration.VERTICAL))


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



}
