package com.example.letsbucket.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsbucket.PopupDialog
import com.example.letsbucket.R
import com.example.letsbucket.adaptor.ThisYearAdapter
import com.example.letsbucket.databinding.FragmentThisyearBinding
import com.example.letsbucket.util.DataUtil

class ThisYearFragment : Fragment() {

    private lateinit var binding: FragmentThisyearBinding
    private lateinit var thisYearAdapter: ThisYearAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThisyearBinding.inflate(inflater, container, false)
        binding.fab.setColorFilter(R.color.white)
        setupBinding()
        return binding.root
    }

    private fun setupBinding() {
        binding.fab.setOnClickListener(View.OnClickListener {
            PopupDialog(requireContext()).show()
        })

        binding.thisYearBucketList.apply {
            thisYearAdapter = ThisYearAdapter(DataUtil.thisYearBucketList)
            adapter = thisYearAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}