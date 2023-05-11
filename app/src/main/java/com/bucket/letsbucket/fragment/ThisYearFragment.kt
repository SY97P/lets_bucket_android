package com.bucket.letsbucket.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.bucket.letsbucket.R
import com.bucket.letsbucket.activity.MainActivity
import com.bucket.letsbucket.adaptor.ViewPagerAdapter
import com.bucket.letsbucket.data.DatePage
import com.bucket.letsbucket.databinding.FragmentThisyearBinding
import com.bucket.letsbucket.util.LogUtil
import java.util.*
import kotlin.collections.ArrayList

class ThisYearFragment : Fragment() {

    private var TAG: String = javaClass.simpleName

    private lateinit var binding: FragmentThisyearBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_thisyear, container, false)

        val list: ArrayList<DatePage> = arrayListOf(
            DatePage("1 page"),
            DatePage("2 page")
        )
        binding.viewPager.adapter = ViewPagerAdapter(list)
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        return binding.root
    }
}