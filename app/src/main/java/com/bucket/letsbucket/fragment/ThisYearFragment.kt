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
import com.bucket.letsbucket.data.CalendarInfo
import com.bucket.letsbucket.databinding.FragmentThisyearBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class ThisYearFragment : Fragment() {

    private var TAG: String = javaClass.simpleName

    private final val BOUND = 240

    private lateinit var binding: FragmentThisyearBinding

    private lateinit var adapter: ViewPagerAdapter

    private lateinit var calendar: Calendar
    private var mPosition: Int by Delegates.observable(BOUND/2+1) { property, oldValue, newValue ->
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
        calendar.add(Calendar.MONTH, -(BOUND/2))

        val list: ArrayList<CalendarInfo> = arrayListOf()
        for (i in 0 .. BOUND) {
            list.add(CalendarInfo(calendar.clone() as Calendar))
            calendar.add(Calendar.MONTH, 1)
        }

        calendar.add(Calendar.MONTH, -(BOUND/2+1))
        adapter = ViewPagerAdapter(requireContext(), list)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_thisyear, container, false)

        binding.viewPager.adapter = adapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.viewPager.setCurrentItem(mPosition, true)

        setupBinding()

        return binding.root
    }

    private fun setupBinding() {
        binding.let {
            it.viewPager.registerOnPageChangeCallback(pageChangeCallback)
            it.calendarYearMonthText.text = SimpleDateFormat("yyyy년 M월", Locale.KOREA)
                .format(calendar.time)
        }
    }
}