package com.bucket.letsbucket.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.bucket.letsbucket.R
import com.bucket.letsbucket.fragment.AnimationDialog

class AlertAndAnimationUtil(context: Context, dismissListener: AlertAndAnimationDismissListener) {

    private var context: Context
    private var aaalistener: AlertAndAnimationDismissListener

    init {
        this.context = context
        this.aaalistener = dismissListener
    }

    fun build(title: String, message: String, animationType: DataUtil.ANIM_TYPE) {
        val alert = AlertDialog.Builder(context, R.style.AlertDialogStyle)
            .setIcon(R.drawable.basic)
            .setTitle(title)
            .setMessage(message)
            .show()
        AnimationDialog(context, animationType).let {
            it.setOnDismissListener {
                alert.cancel()
                aaalistener.onDismiss()
            }
            it.show()
        }
    }
}