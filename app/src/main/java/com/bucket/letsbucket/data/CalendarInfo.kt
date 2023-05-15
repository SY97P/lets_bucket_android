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

    inner class DateInfo(private val dateinfo: String) {
        var year: Int = 0
        var month: Int = 0
        var day: Int = 0
        var bucketTypes: ArrayList<Int> = arrayListOf()
        var validDate = false

        init {
            if (!dateinfo.isBlank()) {
                validDate = true
                for (typeIdx in 0 until DataUtil.BUCKET_LIST.size) {
                    DataUtil.BUCKET_LIST[typeIdx].forEach {
                        if (dateinfo.equals(it.itemTargetDate) || dateinfo.equals(it.itemDoneDate)) {
                            bucketTypes.add(typeIdx)
                        }
                    }
                }
                LogUtil.d(TAG, "DateInfo: $dateinfo -> bucketTypes: ${bucketTypes.size}")
            } else {
                validDate = false
            }
        }
    }

    fun makeCalendar() {
        dateList.clear()

        monthStartDate = mCalendar.get(Calendar.DAY_OF_WEEK) % 8
        monthDateCnt = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 1 until monthStartDate) {
            dateList.add(
                DateInfo("")
            )
        }

        for (i in 1 .. monthDateCnt) {
            dateList.add(
                DateInfo("${mCalendar.get(Calendar.YEAR)}/${mCalendar.get(Calendar.MONTH)}/$i")
            )
        }
    }
}