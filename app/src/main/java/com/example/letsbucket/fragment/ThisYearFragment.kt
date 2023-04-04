package com.example.letsbucket.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsbucket.R
import com.example.letsbucket.ThisYearItem
import com.example.letsbucket.adaptor.ThisYearAdapter
import com.example.letsbucket.databinding.FragmentThisyearBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ThisYearFragment : Fragment() {

    private lateinit var binding: FragmentThisyearBinding
    private lateinit var itemList: ArrayList<ThisYearItem>
    private lateinit var adapter: ThisYearAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemList = ArrayList()

        itemList.add(ThisYearItem("하나"))
        itemList.add(ThisYearItem("둘"))
        itemList.add(ThisYearItem("셋"))
        itemList.add(ThisYearItem("넷"))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThisyearBinding.inflate(inflater, container, false)

        adapter = ThisYearAdapter(itemList)
        binding.thisYearBucketList.adapter = adapter
        binding.thisYearBucketList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }
}