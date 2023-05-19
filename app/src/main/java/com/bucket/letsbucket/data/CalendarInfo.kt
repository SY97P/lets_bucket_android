package com.bucket.letsbucket.data

import com.bucket.letsbucket.util.DataUtil
import com.bucket.letsbucket.util.LogUtil
import java.util.*

class CalendarInfo(private var calendar: Calendar) {

    private val TAG = javaClass.simpleName

    var dateList = arrayListOf<DateInfo>()

    private val mCalendar: Calendar = calendar

    private var monthStartDate = 0
    private var monthDateCnt = 0

    inner class DateInfo(private val dateinfo: String?) {
        var year: String = ""
        var month: String = ""
        var day: String = ""
        var bucketTypes: ArrayList<Int> = arrayListOf()
        var validDate = false

        init {
            if (dateinfo != null) {
                validDate = true
                val data = dateinfo.split("/")
                year = data[0]
                month = data[1]
                day = data[2]
                for (typeIdx in 0 until DataUtil.BUCKET_LIST.size) {
                    DataUtil.BUCKET_LIST[typeIdx].forEach {
                        if (dateinfo.equals(it.itemTargetDate) || dateinfo.equals(it.itemDoneDate)) {
                            bucketTypes.add(typeIdx)
                        }
                    }
                }
            } else {
                validDate = false
            }
        }

        fun getDateString() = "$year/$month/$day"
    }

    fun makeCalendar() {
        dateList.clear()

//        LogUtil.d(TAG, mCalendar.time.toString())

        monthStartDate = mCalendar.get(Calendar.DAY_OF_WEEK)
        monthDateCnt = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)

//        LogUtil.d(TAG, "$monthStartDate, $monthDateCnt")

        val year = mCalendar.get(Calendar.YEAR).toString()
        val month = (mCalendar.get(Calendar.MONTH)+1).toString()

        for (i in 1 until monthStartDate) {
            dateList.add(
                DateInfo(null)
            )
        }

        for (i in 1 .. monthDateCnt) {
            dateList.add(
                DateInfo("$year/$month/$i")
            )
        }
    }
}