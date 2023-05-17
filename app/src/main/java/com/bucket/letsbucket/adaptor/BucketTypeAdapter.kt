package com.bucket.letsbucket.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bucket.letsbucket.R
import com.bucket.letsbucket.data.CalendarInfo
import com.bucket.letsbucket.dialog.AlertUtilDialog
import com.bucket.letsbucket.util.DataUtil
import com.bucket.letsbucket.util.LogUtil

class BucketTypeAdapter(private val context: Context, private val dateinfo: CalendarInfo.DateInfo)
    : RecyclerView.Adapter<BucketTypeAdapter.BucketTypeHolder>() {

    private val TAG = javaClass.simpleName

    inner class BucketTypeHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgView: ImageView = itemView.findViewById(R.id.item_bucket_type_img)
        fun onBind(data: Int) {
            imgView.setImageResource(DataUtil.LIFE_TYPE_LIST[data].lifeImage)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BucketTypeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bucket_type, parent, false)
        return BucketTypeHolder(view)
    }

    override fun getItemCount(): Int = dateinfo.bucketTypes.size

    override fun onBindViewHolder(holder: BucketTypeHolder, position: Int) {
        holder.onBind(dateinfo.bucketTypes[position])
        holder.itemView.setOnClickListener {
            LogUtil.d(TAG, dateinfo.getDateString() + " " + dateinfo.bucketTypes.forEach { it })
        }
    }


}