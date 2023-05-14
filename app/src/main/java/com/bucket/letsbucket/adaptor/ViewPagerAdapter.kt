package com.bucket.letsbucket.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bucket.letsbucket.R
import com.bucket.letsbucket.data.CalendarInfo
import com.bucket.letsbucket.databinding.ItemViewpagerBinding

class ViewPagerAdapter(private val context: Context, private val listData: ArrayList<CalendarInfo>) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolderPage>() {
    private val TAG = javaClass.simpleName

    lateinit var binding: ItemViewpagerBinding

    inner class ViewHolderPage(val binding: ItemViewpagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CalendarInfo) {
            data.makeCalendar()
            binding.layoutDateRecycler.adapter = CalendarAdapter(context, data.dateList)
            binding.layoutDateRecycler.layoutManager = GridLayoutManager(context, 7)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPage {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_viewpager, parent, false)
        return ViewHolderPage(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderPage, position: Int) {
        val viewHolder: ViewHolderPage = holder
        viewHolder.onBind(listData[position])
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

}