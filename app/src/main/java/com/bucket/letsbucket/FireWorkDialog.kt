package com.bucket.letsbucket

import android.animation.Animator
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.bucket.letsbucket.databinding.LayoutFireworkDialogBinding
import com.bucket.letsbucket.util.LogUtil
import com.google.android.material.snackbar.Snackbar

class FireWorkDialog (context: Context): Dialog(context) {

    private var TAG = "FireWorkDialog"

    private lateinit var binding: LayoutFireworkDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutFireworkDialogBinding.inflate(layoutInflater)

        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.layoutFirework.addAnimatorListener(object: Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                dismiss()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })

        setContentView(binding.root)
    }
}