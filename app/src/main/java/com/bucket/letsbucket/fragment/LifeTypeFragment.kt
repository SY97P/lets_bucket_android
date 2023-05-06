package com.bucket.letsbucket.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.bucket.letsbucket.adaptor.LifeTypeAdapter
import com.bucket.letsbucket.databinding.FragmentLifeTypeBinding
import com.bucket.letsbucket.util.DataUtil

class LifeTypeFragment : Fragment() {

    private lateinit var binding: FragmentLifeTypeBinding
    private lateinit var lifeTypeAdapter: LifeTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLifeTypeBinding.inflate(inflater, container, false)
        lifeTypeAdapter = LifeTypeAdapter(requireContext(), DataUtil.LIFE_TYPE_LIST)
        binding.lifeTypeList.adapter = lifeTypeAdapter
        binding.lifeTypeList.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        return binding.root
    }
}