package com.bucket.letsbucket.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bucket.letsbucket.R

class CalendarAdapter(private val context: Context, private val dataList: ArrayList<String>)
    : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private val TAG = javaClass.simpleName

    inner class CalendarViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var itemCalendarDateText: TextView = itemView!!.findViewById(R.id.item_calendar_date_text)
        fun bind(data: String, position: Int, context: Context) {
            itemCalendarDateText.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date_view, parent, false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(dataList[position], position, context)
    }
}