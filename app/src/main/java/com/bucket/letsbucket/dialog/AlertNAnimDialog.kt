package com.bucket.letsbucket.dialog

import android.content.Context
import com.bucket.letsbucket.listener.DismissListener
import com.bucket.letsbucket.util.DataUtil

class AlertNAnimDialog(context: Context) {

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
            it.build(null)
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