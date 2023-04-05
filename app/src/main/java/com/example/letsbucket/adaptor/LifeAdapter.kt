package com.example.letsbucket.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.letsbucket.R
import com.example.letsbucket.activity.LifeActivity
import com.example.letsbucket.data.LifeItem

class LifeAdapter(private val context: Context, private val dataSet: ArrayList<LifeItem>) :
    RecyclerView.Adapter<LifeAdapter.LifeViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LifeAdapter.LifeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_life_recycler_view, parent, false)
        return LifeViewHolder(this.context, view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: LifeAdapter.LifeViewHolder, position: Int) {
        holder.imgView.setImageResource(dataSet[position].lifeImage)
        holder.textView.text = dataSet[position].lifeText
    }

    inner class LifeViewHolder(context: Context, view : View): RecyclerView.ViewHolder(view) {
        val imgView: ImageView = itemView.findViewById(R.id.img)
        val textView: TextView = itemView.findViewById(R.id.text)

        init {
            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    startActivity(context, Intent(context, LifeActivity::class.java), null)
                }
            }
        }
    }
}