package com.treaf.calendario

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class calendarioAdapter(context: Context, days: ArrayList<Date>, currentMonth: Int, clicked: OnDateListener) :
    ArrayAdapter<Date>(context,R.layout.custom_calendar_day , days  ) {
    private var clicked = clicked
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var currentMonth = currentMonth
    private var newColor:String? = null
    private var textSize:Int? = null
    private var newFont: Typeface? = null
    private var selectionNum:Int? = null
    private var selectedDay:Int? = null
    private var selectorColor:String? = null
    private var drawableRes:Int? = null
    private var eventRes:Int? = null


    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var view = view
        val calendar = Calendar.getInstance()
        val date = getItem(position)
        calendar.time = date
        val day = calendar.get(Calendar.DATE)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val today = Date()
        val calendarToday = Calendar.getInstance()
        calendarToday.time = today

        if (view == null) {
            view = inflater.inflate(R.layout.custom_calendar_day, parent, false)
            val textDate = view?.findViewById<View>(R.id.textDate) as TextView
            textDate.textSize = 16F
            textDate.gravity = Gravity.CENTER
            textDate.tag = position
            textDate.text = (calendar.get(Calendar.DATE)).toString()



            if (month != currentMonth){
                textDate.visibility = View.GONE
            }

            if (day == calendarToday.get(Calendar.DATE) && month == calendarToday.get(Calendar.MONTH)
                && year == calendarToday.get(Calendar.YEAR))
            {
                if (drawableRes != null){
                    textDate.setBackgroundResource(drawableRes!!)
                } else {
                    textDate.setBackgroundResource(R.drawable.selectorbox)
                }
            }


            textDate.setOnClickListener {
                clicked.getSelectedDate(Integer.parseInt(textDate.text.toString()) , calendar.get(Calendar.MONTH) + 1 , calendar.get(
                    Calendar.YEAR))
                when(selectionNum){

                    1 -> {
                        // Single Choice
                        var i = 0
                        while (i < 42){
                            if (parent.getChildAt(i).background != null){
                                selectedDay = i
                            }
                            i++
                        }
                        if (selectedDay == null) {
                            if (drawableRes != null){
                                textDate.setBackgroundResource(drawableRes!!)
                            } else {
                                textDate.setBackgroundResource(R.drawable.selectorbox)
                            }
                            if (selectorColor != null) {
                                textDate.background?.setColorFilter(
                                    Color.parseColor(selectorColor),
                                    PorterDuff.Mode.SRC_ATOP
                                )
                            }
                        } else {
                            if (parent.getChildAt(selectedDay!!).background != null){
                                parent.getChildAt(selectedDay!!).setBackgroundResource(0)
                                if (drawableRes != null){
                                    textDate.setBackgroundResource(drawableRes!!)
                                } else{
                                    textDate.setBackgroundResource(R.drawable.selectorbox)
                                }
                                if (selectorColor != null) {
                                    textDate.background?.setColorFilter(
                                        Color.parseColor(selectorColor),
                                        PorterDuff.Mode.SRC_ATOP
                                    )
                                }
                            }
                        }

                    }
                    2 -> {
                        // Multiple Choice
                        if (parent.getChildAt(textDate.tag as Int).background != null){
                            parent.getChildAt(textDate.tag as Int).setBackgroundResource(0)
                        } else{
                            if (drawableRes != null){
                                textDate.setBackgroundResource(drawableRes!!)
                            } else{
                                textDate.setBackgroundResource(R.drawable.selectorbox)

                            }
                            if (selectorColor != null) {
                                textDate.background?.setColorFilter(
                                    Color.parseColor(selectorColor),
                                    PorterDuff.Mode.SRC_ATOP
                                )
                            }
                        }
                    }

                }

            }
            if (newColor != null){
                textDate.setTextColor(Color.parseColor(newColor!!))
            } else {
                textDate.setTextColor(Color.BLACK)
            }
            if (textSize != null){
                textDate.textSize = textSize!!.toFloat()
            }
            if (newFont !=null){
                textDate.typeface = newFont
            }
            if (selectorColor != null){
                textDate.background?.setColorFilter(Color.parseColor(selectorColor) , PorterDuff.Mode.SRC_ATOP)
            }

            val db = sqliteDB(context)
            val currentDay = Integer.parseInt(textDate.text.toString())
            if (!db.dbIsEmpty()) {
                if (db.getEvents(currentDay , calendar.get(Calendar.MONTH)+1 , calendar.get(Calendar.YEAR))){
                  //  Toast.makeText(context , "There are events" , Toast.LENGTH_SHORT).show()
                    if (eventRes != null) {
                        textDate.setCompoundDrawablesWithIntrinsicBounds(0 , 0, 0,eventRes!!)
                    }
                }
            }

        }



        return view


    }

    fun changeTextColor(color: String?){
        newColor = color
    }
    fun changeTextSize(newSize:Int?){
        textSize = newSize
    }
    fun changeTextFont(font: Typeface?){
        newFont = font
    }
    fun selectionType(selection: Int?){
        // None - Single - Multiple
        selectionNum = selection // 0 = None 1 = Single 2 = Multiple
    }
    fun changeSelectorColor(color: String?){
        selectorColor = color
    }

    fun setSelector(resId:Int?){
        drawableRes = resId
    }
    fun setEventIcon(resId: Int?){
        eventRes = resId
    }

}