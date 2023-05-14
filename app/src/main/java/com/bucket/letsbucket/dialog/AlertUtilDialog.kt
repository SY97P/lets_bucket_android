package com.bucket.letsbucket.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.bucket.letsbucket.BuildConfig
import com.bucket.letsbucket.R
import com.bucket.letsbucket.listener.DismissListener
import com.bucket.letsbucket.util.DataUtil
import com.bucket.letsbucket.util.LogUtil

class AlertUtilDialog(private val context: Context, private val type: DataUtil.DIALOG_TYPE) {

    private val TAG = javaClass.simpleName

    private lateinit var dialog: AlertDialog.Builder

    private lateinit var dismissListener: DismissListener

    private final val data = arrayOf<String>("🎃 개발자: 박세영", "📋 버전 : ${BuildConfig.VERSION_NAME}")
    private final val wayItems = arrayOf("갤러리에서 선택할래요", "카메라로 찍을래요")
    private var selectedItem: Int? = null

    fun setDismissListener(onDismissListener: DismissListener) {
        this.dismissListener = onDismissListener
    }

    fun build() {
        dialog = AlertDialog.Builder(context, R.style.AlertDialogStyle).setIcon(R.drawable.basic)
        when (type) {
            DataUtil.DIALOG_TYPE.BUCKET_DONE -> {
                dialog
                    .setTitle("버킷리스트 달성!!")
                    .setMessage("축하해요!!")
            }
            DataUtil.DIALOG_TYPE.APP_INFO -> {
                dialog
                    .setTitle("어플리케이션 정보")
                    .setItems(data, null)
            }
            DataUtil.DIALOG_TYPE.SNAPSHOT -> {
                dialog
                    .setTitle("인증샷 선택하기")
                    .setSingleChoiceItems(wayItems, -1) { dialog, which ->
                        selectedItem = which
                    }
                    .setPositiveButton("확인") { dialog, which ->
                        LogUtil.d(TAG, "${selectedItem} Task Selected")
                        dismissListener.onDismiss(selectedItem!!)
                    }
                    .setNegativeButton("취소") { dialog, which ->
                        LogUtil.d(TAG, "Cancel Image Select Task")
                    }
            }
            DataUtil.DIALOG_TYPE.CALENDAR -> {

            }
            DataUtil.DIALOG_TYPE.DEFAULT -> {

            }
        }
    }

    fun show(): AlertDialog {
        return dialog.show()
    }
}