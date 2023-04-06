package com.example.letsbucket.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsbucket.adaptor.LifeTypeAdapter
import com.example.letsbucket.databinding.FragmentLifeTypeBinding
import com.example.letsbucket.util.DataUtil

class LifeTypeFragment : Fragment() {

    private lateinit var binding: FragmentLifeTypeBinding
    private lateinit var lifeTypeAdapter: LifeTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLifeTypeBinding.inflate(inflater, container, false)
        lifeTypeAdapter = LifeTypeAdapter(requireContext(), DataUtil.lifeTypeList)
        binding.lifeTypeList.adapter = lifeTypeAdapter
        binding.lifeTypeList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }
}