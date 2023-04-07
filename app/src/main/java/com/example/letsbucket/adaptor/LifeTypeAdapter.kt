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
import com.example.letsbucket.data.LifeTypeItem

class LifeTypeAdapter(private val context: Context, private val dataSet: ArrayList<LifeTypeItem>) :
    RecyclerView.Adapter<LifeTypeAdapter.LifeTypeViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LifeTypeAdapter.LifeTypeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_life_type_recycler_view, parent, false)
        return LifeTypeViewHolder(this.context, view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: LifeTypeAdapter.LifeTypeViewHolder, position: Int) {
        holder.imgView.setImageResource(dataSet[position].lifeImage)
        holder.textView.text = context.getString(dataSet[position].lifeString)
    }

    inner class LifeTypeViewHolder(context: Context, view : View): RecyclerView.ViewHolder(view) {
        val imgView: ImageView = itemView.findViewById(R.id.img)
        val textView: TextView = itemView.findViewById(R.id.text)

        init {
            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val intent = Intent(context, LifeActivity::class.java)
                    intent.putExtra("LIFE_TYPE", adapterPosition)
                    startActivity(context, intent, null)
                }
            }
        }
    }
}