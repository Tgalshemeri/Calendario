package com.treaf.calendario

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Calendario(context: Context?, attrs: AttributeSet? ) : FrameLayout(context, attrs)  {

    private lateinit var header: LinearLayout
    private  lateinit var btnPrev: Button
    private  lateinit var gridView: GridView
    private lateinit var daysGrid: GridView
    private  lateinit var textDate: TextView
    private  lateinit var btnNext : Button
    private var newColor:String? = null
    private  var textSize:Int? = null
    private  var textFont: Typeface? = null
    private  var selectionNum:Int? = null
    private var chosenDay : Int = 0
    private var chosenMonth:Int = 0
    private  var chosenYear:Int? = null
    private  var currentDate: Calendar = Calendar.getInstance()
    private var selectedDate : String? = null
    private  var listeners:OnDateSelectedListener? = null
    private var selectorColor:String? = null
    private var firstDayOfWeek:Int = 0
    private var selectorRes:Int? = null
    private var eventRes:Int? = R.drawable.default_indicator

    init {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.item_calendar , this)
        assignUIElements()
        assignClickHandlers()
        updateCalendar()
    }



    private fun assignClickHandlers() {
        btnNext.setOnClickListener {
            currentDate.add(Calendar.MONTH, 1)
            updateCalendar()

        }
        btnPrev.setOnClickListener {
            currentDate.add(Calendar.MONTH, -1)
            updateCalendar()
        }
    }


    private fun updateCalendar() {
        val cells = ArrayList<Date>()
        val calendar = currentDate.clone() as Calendar
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)
        while (cells.size < 42) {
            cells.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        val sdf = SimpleDateFormat("MMMM - yyyy").format(currentDate.time)
        textDate.text = sdf
        if(newColor != null) {
            textDate.setTextColor(Color.parseColor(newColor))
        }
        if (textSize != null) {
            textDate.textSize = textSize?.toFloat()!!
        }
        if (textFont != null) {
            textDate.typeface = textFont
        }
        val adapter = calendarioAdapter(context, cells , currentDate.get(Calendar.MONTH) , object:OnDateListener {
            override fun getSelectedDate(day: Int, month: Int, year: Int)  {
                chosenDay = day
                chosenMonth = month
                chosenYear = year
                selectedDate = "$chosenDay/$chosenMonth/$chosenYear"
                listeners?.onDateSelected(day, month, year)
            }
        })

        gridView.adapter = adapter
        (gridView.adapter as calendarioAdapter).notifyDataSetChanged()
        adapter.changeTextColor(newColor)
        adapter.changeTextSize(textSize)
        adapter.changeTextFont(textFont)
        adapter.selectionType(selectionNum)
        adapter.changeSelectorColor(selectorColor)
        adapter.setSelector(selectorRes)
        adapter.setEventIcon(eventRes)
        val daysAdapter = weekDaysAdapter(context , firstDayOfWeek)
        daysGrid.adapter = daysAdapter
        daysAdapter.changeTextColor(newColor)
        daysAdapter.changeTextFont(textFont)
        daysAdapter.changeTextSize(textSize)
    }


    private fun assignUIElements() {
        header = findViewById(R.id.calendar_header)
        gridView = findViewById(R.id.calendar_grid)
        btnNext = findViewById(R.id.btnNext)
        btnPrev = findViewById(R.id.btnPrev)
        textDate = findViewById(R.id.showDate)
        daysGrid = findViewById(R.id.days_grid)
    }

    fun changeTextColor(color:String){
        newColor = color
        updateCalendar()

    }
    fun changeTextSize(size:Int){
        textSize = size
        updateCalendar()
    }
    fun changeTextFont(font: Typeface){
        textFont = font
        updateCalendar()
    }
    fun setSelectionType(selectionKey:Int){
        selectionNum = selectionKey
        updateCalendar()
    }
    fun setSelectorColor(selectorColor:String){
        this.selectorColor = selectorColor
        updateCalendar()
    }

    fun addEvent(event:String ,day:Int , month:Int , year:Int , time:String ){
        val dbConnection = sqliteDB(context)
        dbConnection.insertIntoDB(event, day, month , year , time )
        updateCalendar()
    }
    fun addEvent(event:String ,day:Int , month:Int , year:Int ){
        val dbConnection = sqliteDB(context)
        dbConnection.insertIntoDB(event, day, month , year  )
        updateCalendar()
    }

    fun setStartDayOfWeek(startDay:Int){
        this.firstDayOfWeek = startDay
        updateCalendar()
    }


    fun setOnDateListener(listener: OnDateSelectedListener) {
        this.listeners = listener
    }
    interface OnDateSelectedListener {
        fun onDateSelected(day: Int, month: Int,year: Int)
    }

    fun setSelector(resId:Int) {
        this.selectorRes = resId
        updateCalendar()
    }
    fun setEventIndicator(resId: Int) {
        this.eventRes = resId
        updateCalendar()
    }
    fun removeEvent(event: String , day: Int , month: Int , year: Int){
        val db = sqliteDB(context)
        if (!db.dbIsEmpty()){
            db.removeEvent(event,day,month,year , null)
        }
        updateCalendar()
    }

    fun hideEventIndicator(value:Boolean){
        if(value){
            this.eventRes = null
            updateCalendar()
        } else {
            updateCalendar() //default view
        }
    }
   fun getEventsByDay(day: Int):Events {
       val db = sqliteDB(context)
       return db.getEventByDay(day)
   }
    fun getEventsByMonth(month: Int):Events {
        val db = sqliteDB(context)
        return db.getEventsByMonth(month)
    }
    fun getEventsByYear(year: Int):Events {
        val db = sqliteDB(context)
        return db.getEventByDay(year)
    }
    fun getEventsByDate(day: Int , month: Int , year: Int):Events {
        val db = sqliteDB(context)
        return db.getEventByDate(day,month, year)
    }
    fun searchEventsDate(event: String):ArrayList<String> {
        val db = sqliteDB(context)
      return  db.searchForEventsDate(event)
   }
}
