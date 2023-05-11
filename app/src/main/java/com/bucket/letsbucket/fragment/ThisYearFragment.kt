package com.bucket.letsbucket.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bucket.letsbucket.R
import com.bucket.letsbucket.activity.MainActivity
import com.bucket.letsbucket.databinding.FragmentThisyearBinding
import com.bucket.letsbucket.util.LogUtil
import java.util.*

class ThisYearFragment : Fragment() {

    private var TAG: String = javaClass.simpleName

    private lateinit var binding: FragmentThisyearBinding

    private lateinit var mContext: Context

    private var pageIndex = 0
    private lateinit var currentDate: Date

    companion object {
        var instance: ThisYearFragment? = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mContext = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThisyearBinding.inflate(inflater, container, false)
        setupBinding()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupBinding() {
        // FirstFragment에서 ViewPager2의 Adapter를 통해 setCurrentItem(Int.MAX_VALUE / 2) 을 호출하면
        // Adapter의 createFragment()에서 Int.MAX_VALUE/2 번째 CalendarFragment를 반환
        // 때문에 ThisYearFragment의 pageIndex에는 position 값(Int.MAX_VALUE/2)가 들어감.
        // 또한 FirstFramgent-ViewPager2 의 첫 번째 ThisYearFragment가 될 것이므로
        // 현재 월의 달력화면이 된다.
        pageIndex -= (Int.MAX_VALUE / 2)
        LogUtil.d(TAG, "Calendar Index: $pageIndex")
        val date = Calendar.getInstance().run {
            add(Calendar.MONTH, pageIndex)
            time
        }
        currentDate = date
        LogUtil.d(TAG, "$currentDate")
        var datetime: String = SimpleDateFormat(
            mContext.getString(R.string.calendar_year_month_format),
            Locale.KOREA
        ).format(date.time)
        binding.calendarYearMonthText.text = datetime
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}