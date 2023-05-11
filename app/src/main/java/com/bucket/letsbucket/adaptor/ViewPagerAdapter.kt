package com.bucket.letsbucket.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bucket.letsbucket.R
import com.bucket.letsbucket.data.DatePage
import com.bucket.letsbucket.databinding.ItemViewpagerBinding
import com.bucket.letsbucket.util.LogUtil

class ViewPagerAdapter(private val listData: ArrayList<DatePage>) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolderPage>() {
    lateinit var binding: ItemViewpagerBinding


    inner class ViewHolderPage(val binding: ItemViewpagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: DatePage) {
            binding.tvTitle.text = data.title
//            binding.tvTitle.setTextSize(20f)
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

    override fun getItemCount(): Int = listData.size

}