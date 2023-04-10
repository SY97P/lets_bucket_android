package com.example.letsbucket.adaptor

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

object TestBindingAdapter {
    @JvmStatic
    @BindingAdapter("imgRes")
    fun setImgRes(imageView: ImageView, imgRes: Int) {
        imageView.setImageResource(imgRes)
    }
}