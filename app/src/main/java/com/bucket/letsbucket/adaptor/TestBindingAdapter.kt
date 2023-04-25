package com.bucket.letsbucket.adaptor

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bucket.letsbucket.R

object TestBindingAdapter {
    @JvmStatic
    @BindingAdapter("imgRes")
    fun setImgRes(imageView: ImageView, imgRes: Int) {
        imageView.setImageResource(imgRes)
    }

    @JvmStatic
    @BindingAdapter("checkImgRes")
    fun setCheckImgRes(imageView: ImageView, done: Boolean) {
        if (done) {
            imageView.setImageResource(R.drawable.checked)
        } else {
            imageView.setImageResource(R.drawable.unchecked)
        }
    }
}