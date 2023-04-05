package com.example.letsbucket.adaptor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letsbucket.PopupDialog
import com.example.letsbucket.R
import com.example.letsbucket.data.ThisYearItem
import com.example.letsbucket.util.DataUtil

class ThisYearAdapter(private val context: Context, private val dataSet: ArrayList<ThisYearItem>) :
    RecyclerView.Adapter<ThisYearAdapter.ThisYearViewHolder>() {

    inner class ThisYearViewHolder(context: Context, view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = itemView.findViewById(R.id.text)
        val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)

        init {
            // click -> 수정
            view.setOnClickListener {
                Log.d("mylog", adapterPosition.toString())
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    PopupDialog(context, DataUtil.POPUP_TYPE.MODIFY, adapterPosition).show()
                }
            }

            view.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    DataUtil.thisYearBucketList.removeAt(adapterPosition)
                    true
                } else {
                    false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThisYearViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_thisyear_recycler_view, parent, false)
        return ThisYearViewHolder(this.context, view)
    }

    override fun onBindViewHolder(holder: ThisYearViewHolder, position: Int) {
        holder.textView.text = dataSet[position].itemText
        holder.checkbox.isChecked = dataSet[position].itemDone

        holder.checkbox.setOnClickListener(null)
        holder.checkbox.isChecked = dataSet[position].itemDone
        holder.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            dataSet[position].itemDone = isChecked
        })
    }

    override fun getItemCount(): Int = dataSet.size
}