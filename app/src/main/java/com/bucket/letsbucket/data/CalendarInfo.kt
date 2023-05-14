package com.bucket.letsbucket.data

import java.util.*

class CalendarInfo(private var calendar: Calendar) {

    private val TAG = javaClass.simpleName

    var dateList = arrayListOf<String>()

    private var monthStartDate = 0
    private var monthDateCnt = 0

    fun makeCalendar() {
        dateList.clear()

        monthStartDate = calendar.get(Calendar.DAY_OF_WEEK) % 7
        monthDateCnt = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 0 until monthStartDate) {
            dateList.add(" ")
        }
        for (i in 1 .. monthDateCnt) {
            dateList.add(i.toString())
        }
    }
}