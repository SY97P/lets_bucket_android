package com.bucket.letsbucket.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bucket.letsbucket.R
import com.bucket.letsbucket.data.DatePage
import com.bucket.letsbucket.databinding.ItemViewpagerBinding
import com.bucket.letsbucket.fragment.ThisYearFragment
import com.bucket.letsbucket.util.LogUtil

class ViewPagerAdapter(private val listData: ArrayList<DatePage>) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolderPage>() {
    private val TAG = javaClass.simpleName

    lateinit var binding: ItemViewpagerBinding

    inner class ViewHolderPage(val binding: ItemViewpagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: DatePage) {
            binding.tvTitle.text = data.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPage {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_viewpager, parent, false)
        return ViewHolderPage(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderPage, position: Int) {
        val viewHolder: ViewHolderPage = holder
        viewHolder.onBind(listData[position % listData.size])
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

}