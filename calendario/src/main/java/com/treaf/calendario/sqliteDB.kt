package com.treaf.calendario

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.collections.ArrayList

class sqliteDB(context: Context?) : SQLiteOpenHelper(context , "events.db" , null , 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table IF NOT EXISTS calendar (id INTEGER primary key , event TEXT , day INTEGER , month INTEGER , year INTEGER , time TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("Drop table if EXISTS calendar")
        onCreate(db)
    }

    fun insertIntoDB(event: String, day: Int, month: Int, year: Int , clock: String) {
        val db = this.writableDatabase as SQLiteDatabase
        val contentValues = ContentValues()
        contentValues.put("event", event)
        contentValues.put("day", day)
        contentValues.put("month", month)
        contentValues.put("year", year)
        contentValues.put("time" , clock)
        db.insert("calendar", null, contentValues)
    }
    fun insertIntoDB(event: String, day: Int, month: Int, year: Int) {
        val db = this.writableDatabase as SQLiteDatabase
        val contentValues = ContentValues()
        contentValues.put("event", event)
        contentValues.put("day", day)
        contentValues.put("month", month)
        contentValues.put("year", year)

        db.insert("calendar", null, contentValues)
    }
    fun removeEvent(event: String, day: Int, month: Int, year: Int, time: String?) {
        val db = this.writableDatabase as SQLiteDatabase
        db.execSQL("delete from calendar where event = '$event' and day = '$day' and month = '$month' and year = '$year' and time = '$time' ")

    }

    fun getEventByDay(day: Int) : Events{
        val array = ArrayList<String>()
        val datList = ArrayList<String>()
        val timeList = ArrayList<String>()
        val events = Events(array , datList , timeList )
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT * from calendar where day='$day'",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            val event = cursor.getString(cursor.getColumnIndex("event"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))
            val month = cursor.getInt(cursor.getColumnIndex("month"))
            val year = cursor.getInt(cursor.getColumnIndex("year"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val date = "$day $month $year"
            array.add(event)
            datList.add(date)
            timeList.add(time)
            if (!cursor.getString(cursor.getColumnIndex("time")).isNullOrBlank()){
                timeList.add(cursor.getString(cursor.getColumnIndex("time")))
            }
            cursor.moveToNext()
        }
        cursor.close()
        return events.getBoth(array, datList, timeList)
    }
    fun getEventByDate(day: Int , month: Int , year: Int) : Events{
        val array = ArrayList<String>()
        val datList = ArrayList<String>()
        val timeList = ArrayList<String>()
        val events = Events(array , datList , timeList )
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT * from calendar where day='$day' and month='$month' and year='$year'",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            val event = cursor.getString(cursor.getColumnIndex("event"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))
            val month = cursor.getInt(cursor.getColumnIndex("month"))
            val year = cursor.getInt(cursor.getColumnIndex("year"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val date = "$day $month $year"
            array.add(event)
            datList.add(date)
            timeList.add(time)
            cursor.moveToNext()
        }
        cursor.close()
        return events.getBoth(array , datList , timeList )
    }
    fun getEventsByMonth(month:Int):Events {
        val array = ArrayList<String>()
        val datList = ArrayList<String>()
        val timeList = ArrayList<String>()
        val events = Events(array , datList, timeList )
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT * from calendar where  month='$month'",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            val event = cursor.getString(cursor.getColumnIndex("event"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))
            val month = cursor.getInt(cursor.getColumnIndex("month"))
            val year = cursor.getInt(cursor.getColumnIndex("year"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val date = "$day $month $year"
            array.add(event)
            datList.add(date)
            timeList.add(time)

            cursor.moveToNext()
        }
        cursor.close()
        return events.getBoth(array, datList, timeList)
    }
fun getEventsByYear(year: Int):Events {
    val array = ArrayList<String>()
    val datList = ArrayList<String>()
    val timeList = ArrayList<String>()
    val events = Events(array , datList , timeList)
    val db = this.readableDatabase

    val cursor = db.rawQuery("SELECT * from calendar where year='$year'",null)
    cursor.moveToFirst()
    while (!cursor.isAfterLast){
        val event = cursor.getString(cursor.getColumnIndex("event"))
        val day = cursor.getInt(cursor.getColumnIndex("day"))
        val month = cursor.getInt(cursor.getColumnIndex("month"))
        val year = cursor.getInt(cursor.getColumnIndex("year"))
        val time = cursor.getString(cursor.getColumnIndex("time"))
        val date = "$day $month $year"

        array.add(event)
        datList.add(date)
        timeList.add(time)

        cursor.moveToNext()
    }
    cursor.close()
    return events.getBoth(array, datList, timeList)
}


    fun dbIsEmpty(): Boolean {
        var empty = true
        val db = this.readableDatabase
        val cur = db.rawQuery("SELECT COUNT(*) FROM calendar", null);
        if (cur != null && cur.moveToFirst()) {
            empty = (cur.getInt(0) == 0)
        }
        cur.close()

        return empty;
    }



    fun getEvents(day: Int, month: Int, year: Int): Boolean {
        var checked = false
       val db = this.readableDatabase
        val cursor = db.
            rawQuery("SELECT event FROM calendar Where day='$day' and month='$month' and year='$year'" , null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            checked = true
            cursor.moveToNext()
        }
        cursor.close()
        return checked
    }

    fun searchForEventsDate (event: String) : ArrayList<String> {
        val dateList = ArrayList<String>()
        var date:String? = null
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM calendar WHERE event='$event'" , null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            val day = cursor.getString(cursor.getColumnIndex("day"))
            val month = cursor.getString(cursor.getColumnIndex("month"))
            val year = cursor.getString(cursor.getColumnIndex("year"))

            date = "$day-$month-$year"
            dateList.add(date)
            cursor.moveToNext()
        }
        cursor.close()

        return dateList
    }



}