package com.example.letsbucket.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.google.android.material.chip.ChipDrawable.Delegate
import kotlin.properties.Delegates

class ThisYearFragment : Fragment() {

    private var TAG: String = DataUtil.TAG + "ThisYearFragment"

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
        Log.d(TAG, "onCreateView")
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupBinding() {
        binding.fab.setOnClickListener(View.OnClickListener {
            PopupDialog(requireContext(), DataUtil.POPUP_TYPE.ADD, DataUtil.NONE).let {
//                it.setOnPopupDialogListener(object: PopupDialog.PopupDialogInterface{
//                    override fun itemListChangedListener(isChanged: Boolean) {
//                        thisYearAdapter.notifyDataSetChanged()
//                    }
//                })
                it.setOnDismissListener {
                    thisYearAdapter.notifyDataSetChanged()
                }
                it.show()
            }
        })

        binding.thisYearBucketList.apply {
            thisYearAdapter = ThisYearAdapter(requireContext(), DataUtil.thisYearBucketList)
            adapter = thisYearAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}