package com.bucket.letsbucket.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bucket.letsbucket.adaptor.BucketAdapter
import com.bucket.letsbucket.databinding.FragmentThisyearBinding
import com.bucket.letsbucket.util.DataUtil

class ThisYearFragment : Fragment() {

    private var TAG: String = "TYFragment"

    private lateinit var binding: FragmentThisyearBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    }
}