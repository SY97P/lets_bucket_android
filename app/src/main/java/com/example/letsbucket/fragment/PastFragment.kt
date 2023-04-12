package com.example.letsbucket.fragment

import android.os.Bundle

import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsbucket.R

import com.example.letsbucket.adaptor.LifeTypeAdapter
import com.example.letsbucket.databinding.FragmentLifeTypeBinding
import com.example.letsbucket.databinding.FragmentPastBinding
import com.example.letsbucket.util.DataUtil

class PastFragment : Fragment() {

    private lateinit var binding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_past, container, false)
        return binding.root
    }
}