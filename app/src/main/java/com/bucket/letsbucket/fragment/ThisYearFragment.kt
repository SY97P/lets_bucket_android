package com.bucket.letsbucket.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.bucket.letsbucket.R
import com.bucket.letsbucket.adaptor.ViewPagerAdapter
import com.bucket.letsbucket.data.DatePage
import com.bucket.letsbucket.databinding.FragmentThisyearBinding
import com.bucket.letsbucket.util.LogUtil
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class ThisYearFragment : Fragment() {

    private var TAG: String = javaClass.simpleName

    private lateinit var binding: FragmentThisyearBinding

    private lateinit var adapter: ViewPagerAdapter

    private lateinit var calendar: Calendar
    private var mPosition: Int by Delegates.observable(0) { property, oldValue, newValue ->
        if (oldValue < newValue) {
            calendar.run {
                add(Calendar.MONTH, 1)
                time
            }
            val datetime = SimpleDateFormat("yyyy년 M월", Locale.KOREA).format(calendar.time)
            binding.calendarYearMonthText.text = datetime
        } else if (oldValue > newValue) {
            calendar.run {
                add(Calendar.MONTH, -1)
                time
            }
            val datetime = SimpleDateFormat("yyyy년 M월", Locale.KOREA).format(calendar.time)
            binding.calendarYearMonthText.text = datetime
        }
    }

    private val pageChangeCallback = object: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            mPosition = position
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calendar = Calendar.getInstance()

        val list: ArrayList<DatePage> = arrayListOf(
            DatePage("1 page"),
            DatePage("2 page")
        )
        adapter = ViewPagerAdapter(list)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_thisyear, container, false)

        binding.viewPager.adapter = adapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        setupBinding()

        return binding.root
    }

    private fun setupBinding() {
        binding.let {
            it.viewPager.registerOnPageChangeCallback(pageChangeCallback)
        }
    }
}