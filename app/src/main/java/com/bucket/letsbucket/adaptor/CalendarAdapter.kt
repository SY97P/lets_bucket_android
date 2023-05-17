package com.bucket.letsbucket.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bucket.letsbucket.R
import com.bucket.letsbucket.activity.LifeActivity
import com.bucket.letsbucket.data.CalendarInfo
import com.bucket.letsbucket.dialog.AlertUtilDialog
import com.bucket.letsbucket.listener.DismissListener
import com.bucket.letsbucket.util.DataUtil
import com.bucket.letsbucket.util.LogUtil

class CalendarAdapter(private val context: Context, private val dataList: ArrayList<CalendarInfo.DateInfo>)
    : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>(), DismissListener {

    private val TAG = javaClass.simpleName

    inner class CalendarViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var itemCalendarLayout: LinearLayout = itemView!!.findViewById(R.id.item_calendar_layout)
        var itemCalendarDateText: TextView = itemView!!.findViewById(R.id.item_calendar_date_text)
        var itemRecyclerView: RecyclerView = itemView!!.findViewById(R.id.item_calendar_recycler_view)

        fun bind(data: CalendarInfo.DateInfo) {
            itemCalendarDateText.text = data.day
            itemRecyclerView.adapter = BucketTypeAdapter(context, data)
            itemRecyclerView.layoutManager = GridLayoutManager(context, 2)
            if (!data.validDate) {
                itemView.findViewById<ConstraintLayout>(R.id.layout_date).background = null
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date_view, parent, false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemCalendarLayout.setOnClickListener {
            if(dataList[position].validDate) {
                LogUtil.d(TAG, dataList[position].getDateString())
                // TODO : AlertUtilDialog 작업
                AlertUtilDialog(context, DataUtil.DIALOG_TYPE.CALENDAR).let {
                    it.setBucketTypeList(dataList[position].bucketTypes)
                    it.build(dataList[position].getDateString())
                    it.setDismissListener(this)
                    it.show()
                }
            }
        }
    }

    override fun onDismiss() {

    }

    override fun onDismiss(itemIdx: Int) {
        val intent = Intent(context, LifeActivity::class.java)
        intent.putExtra("LIFE_TYPE", itemIdx)
        ContextCompat.startActivity(context, intent, null)
    }
}