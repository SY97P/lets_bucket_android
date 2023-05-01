package com.bucket.letsbucket.fragment

import android.animation.Animator
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.bucket.letsbucket.R
import com.bucket.letsbucket.databinding.DialogAnimationPopupBinding
import com.bucket.letsbucket.util.DataUtil

class AnimationDialog (context: Context, animType: DataUtil.ANIM_TYPE): Dialog(context) {

    private var TAG = "AnimationDialog"

    private lateinit var binding: DialogAnimationPopupBinding

    private var animationType: DataUtil.ANIM_TYPE

    init {
        this.animationType = animType
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAnimationPopupBinding.inflate(layoutInflater)

        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        when (animationType) {
            DataUtil.ANIM_TYPE.FIRE_WORK -> {
                binding.layoutAnimation.setAnimation(R.raw.firework_animation)
                binding.layoutAnimation.repeatCount = 4
            }
            DataUtil.ANIM_TYPE.CLICK -> {
                binding.layoutAnimation.setAnimation(R.raw.click)
                binding.layoutAnimation.repeatCount = 1
            }
        }

        binding.layoutAnimation.addAnimatorListener(object: Animator.AnimatorListener {
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

        binding.layoutAnimation.setOnClickListener {
            dismiss()
        }

        setContentView(binding.root)
    }
}