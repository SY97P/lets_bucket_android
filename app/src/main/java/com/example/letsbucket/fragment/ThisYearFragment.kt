package com.example.letsbucket.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsbucket.PopupDialog
import com.example.letsbucket.adaptor.BucketAdapter
import com.example.letsbucket.databinding.FragmentThisyearBinding
import com.example.letsbucket.util.DataUtil

class ThisYearFragment : Fragment() {

    private var TAG: String = "TYFragment"

    private lateinit var binding: FragmentThisyearBinding
    private lateinit var bucketAdapter: BucketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThisyearBinding.inflate(inflater, container, false)
        setupBinding()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupBinding() {
        binding.fab.setOnClickListener(View.OnClickListener {
            PopupDialog(requireContext(), DataUtil.MODE_TYPE.ADD, DataUtil.FROM_TYPE.THIS_YEAR,null, null).let {
                it.setOnDismissListener {
                    bucketAdapter.notifyDataSetChanged()
                }
                it.show()
            }
        })

        binding.thisYearBucketList.apply {
            bucketAdapter = BucketAdapter(requireContext(), DataUtil.FROM_TYPE.THIS_YEAR, null, DataUtil.thisYearBucketList)
            adapter = bucketAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}