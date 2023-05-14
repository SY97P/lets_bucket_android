package com.bucket.letsbucket.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.bucket.letsbucket.R
import com.bucket.letsbucket.dialog.AlertUtilDialog
import com.bucket.letsbucket.dialog.AnimationDialog
import com.bucket.letsbucket.listener.DismissListener

class AlertAndAnimationUtil(context: Context) {

    private var context: Context
    private lateinit var dismissListener: DismissListener

    init {
        this.context = context
    }

    fun setDismissListener(onDismissListener: DismissListener) {
        this.dismissListener = onDismissListener
    }

    fun build(animationType: DataUtil.ANIM_TYPE) {
        val alert = AlertUtilDialog(context, DataUtil.DIALOG_TYPE.BUCKET_DONE).let {
            it.build()
            it.show()
        }
        AnimationDialog(context, animationType).let {
            it.setOnDismissListener {
                alert.cancel()
                dismissListener.onDismiss()
            }
            it.show()
        }
    }
}