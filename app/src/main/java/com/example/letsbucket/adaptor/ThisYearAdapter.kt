package com.example.letsbucket.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letsbucket.R
import com.example.letsbucket.ThisYearItem

class ThisYearAdapter(private val dataSet: ArrayList<ThisYearItem>):
    RecyclerView.Adapter<ThisYearAdapter.ThisYearViewHolder>() {

    inner class ThisYearViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = itemView.findViewById(R.id.this_year_bucket_text)

        init {
            // click listener attached
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThisYearViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_thisyear_recycler_view, parent, false)
        return ThisYearViewHolder(view)
    }

    override fun onBindViewHolder(holder: ThisYearViewHolder, position: Int) {
        holder.textView.text = dataSet[position].text
    }

    override fun getItemCount(): Int = dataSet.size
}