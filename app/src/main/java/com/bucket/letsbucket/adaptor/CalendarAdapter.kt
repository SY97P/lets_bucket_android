package com.bucket.letsbucket.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bucket.letsbucket.R
import com.bucket.letsbucket.data.CalendarInfo
import com.bucket.letsbucket.util.LogUtil

class CalendarAdapter(private val context: Context, private val dataList: ArrayList<CalendarInfo.DateInfo>)
    : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private val TAG = javaClass.simpleName

    inner class CalendarViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var itemCalendarDateText: TextView = itemView!!.findViewById(R.id.item_calendar_date_text)
        fun bind(data: CalendarInfo.DateInfo) {
            itemCalendarDateText.text = data.day.toString()
            if (!data.validDate) {
                itemView.findViewById<ConstraintLayout>(R.id.layout_date).background = null
            } else {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date_view, parent, false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(dataList[position], position, context)
        holder.itemView.setOnClickListener {
            // TODO : AlertUtilDialog 작업
        }
    }
}