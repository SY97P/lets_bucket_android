package com.example.letsbucket.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letsbucket.PopupDialog
import com.example.letsbucket.R
import com.example.letsbucket.data.BucketItem
import com.example.letsbucket.util.DataUtil
import com.example.letsbucket.util.DataUtil.TAG

class BucketAdapter(private val context: Context, private val from: DataUtil.FROM_TYPE, private val lifeType: Int?, private val dataSet: ArrayList<BucketItem>) :
    RecyclerView.Adapter<BucketAdapter.ThisYearViewHolder>() {

    private var TAG = DataUtil.TAG + "BucketAdapter"

    inner class ThisYearViewHolder(context: Context, view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = itemView.findViewById(R.id.text)
        val checkbox: ImageView = itemView.findViewById(R.id.checkbox)

        init {
            // click -> 수정
            view.setOnClickListener {
                Log.d(TAG, adapterPosition.toString() + "is clicked")
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    var popup: PopupDialog? = null
                    when (from) {
                        DataUtil.FROM_TYPE.THIS_YEAR -> {
                            popup = PopupDialog(context, DataUtil.MODE_TYPE.MODIFY, DataUtil.FROM_TYPE.THIS_YEAR, lifeType, adapterPosition)
                        }
                        DataUtil.FROM_TYPE.LIFE -> {
                            popup = PopupDialog(context, DataUtil.MODE_TYPE.MODIFY, DataUtil.FROM_TYPE.LIFE, lifeType, adapterPosition)
                        }
                    }
                    popup.setOnDismissListener {
                        Log.d(TAG, "팝업 종료")
                    }
                    popup.show()
                }
            }

            view.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    if (from == DataUtil.FROM_TYPE.THIS_YEAR) {
                        DataUtil.thisYearBucketList.removeAt(adapterPosition)
                    } else {
                        DataUtil.lifelist[lifeType!!].removeAt(adapterPosition)
                    }
                    true
                } else {
                    false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThisYearViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bucket_recycler_view, parent, false)
        return ThisYearViewHolder(this.context, view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ThisYearViewHolder, position: Int) {
        holder.textView.text = dataSet[position].itemText

        holder.checkbox.setOnClickListener {
            Log.d(TAG, "체크박스 클릭")
            dataSet[position].itemDone = !dataSet[position].itemDone
            notifyDataSetChanged()
        }

        if (dataSet[position].itemDone) {
            holder.checkbox.setImageResource(R.drawable.checked)
        } else {
            holder.checkbox.setImageResource(R.drawable.unchecked)
        }
    }

    override fun getItemCount(): Int = dataSet.size
}