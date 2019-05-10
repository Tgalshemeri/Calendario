package com.treaf.calendario

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class weekDaysAdapter (context: Context, var startDayOfWeek: Int) : BaseAdapter() {


    private var sevenDays = arrayOf("SUN" , "MON" , "TUE" , "WED" , "THUR" , "FRI" , "SAT")
    private val inflater = LayoutInflater.from(context)
    private var textColor: String? = null
    private var textFont:Typeface? = null
    private var textSize:Int? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null)
            view = inflater.inflate(R.layout.days_layout , parent , false)
        val textDay = view?.findViewById<View>(R.id.textDayID) as TextView
        textDay.setTextColor(Color.BLACK)
        when(startDayOfWeek){
            //SUN
            1 -> sevenDays = arrayOf("SUN" , "MON" , "TUE" , "WED" , "THUR" , "FRI" , "SAT")
            //SAT
            0 -> sevenDays = arrayOf("SAT" , "SUN" , "MON" , "TUE" , "WED" , "THUR" , "FRI")
            //MON
            2 -> sevenDays = arrayOf("MON" , "TUE" , "WED" , "THUR" , "FRI" , "SAT" , "SUN")
        }
        textDay.text = sevenDays[position]
        if (textColor != null){
           textDay.setTextColor(Color.parseColor(textColor))
        }
        if (textFont != null){
            textDay.typeface = textFont
        }
        if (textSize != null){
            textDay.textSize = textSize?.toFloat()!!
        }


        return view
    }

    override fun getItem(position: Int): Any {
        return sevenDays[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return sevenDays.size
    }

    fun changeTextColor(Color:String?){
        this.textColor = Color
    }
    fun changeTextSize(size:Int?){
        this.textSize = size
    }
    fun changeTextFont(font:Typeface?){
        this.textFont = font
    }

}