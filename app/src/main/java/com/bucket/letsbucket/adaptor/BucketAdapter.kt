package com.bucket.letsbucket.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bucket.letsbucket.util.DataChangedListener
import com.bucket.letsbucket.R
import com.bucket.letsbucket.data.BucketItem
import com.bucket.letsbucket.data.DetailData
import com.bucket.letsbucket.db.LifeBucketDB
import com.bucket.letsbucket.fragment.AnimationDialog
import com.bucket.letsbucket.util.DataUtil
import com.bucket.letsbucket.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class BucketAdapter(
    private val context: Context,
    private val dataSet: ArrayList<BucketItem>
) :
    RecyclerView.Adapter<BucketAdapter.ThisYearViewHolder>(), DataChangedListener {

    private var TAG = "BucketAdapter"

    @SuppressLint("NotifyDataSetChanged")
    inner class ThisYearViewHolder(context: Context, view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = itemView.findViewById(R.id.text)
        val checkbox: ImageView = itemView.findViewById(R.id.checkbox)
        val removeBtn: ImageView = itemView.findViewById(R.id.remove)
        val layoutCheckBox: LinearLayout = itemView.findViewById(R.id.layout_checkbox)
        val layoutRemove: LinearLayout = itemView.findViewById(R.id.layout_remove)

        val toLeftAnim: Animation = AnimationUtils.loadAnimation(context, R.anim.translate_left)
        var animToggle: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
            if (newValue) {
                layoutRemove.visibility = View.VISIBLE
                layoutCheckBox.visibility = View.GONE
                layoutRemove.startAnimation(toLeftAnim)
            } else {
                layoutRemove.visibility = View.GONE
                layoutCheckBox.visibility = View.VISIBLE
                layoutCheckBox.startAnimation(toLeftAnim)
                layoutRemove.visibility = View.GONE
            }
        }

        init {
            // click -> 수정
            view.setOnClickListener {
                if (!dataSet[adapterPosition].itemText.equals("올해 목표를 세워보세요!") && !dataSet[adapterPosition].itemText.equals(
                        "꼭 이루고 싶은 걸 적어보세요"
                    )
                ) {
                    modifyBucketItem(adapterPosition)
                }
            }

            view.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION && !dataSet[adapterPosition].itemText.equals(
                        "올해 목표를 세워보세요!"
                    ) && !dataSet[adapterPosition].itemText.equals("꼭 이루고 싶은 걸 적어보세요")
                ) {
                    animToggle = !animToggle
                    true
                } else {
                    false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThisYearViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bucket_recycler_view, parent, false)
        // 데이터 변경 감지 리스너 등록
        DataUtil.DATA_CHANGED_LISTENER = this
        return ThisYearViewHolder(this.context, view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ThisYearViewHolder, position: Int) {
        holder.textView.text = dataSet[position].itemText
        if (dataSet[position].itemDone) {
            holder.checkbox.setImageResource(R.drawable.checked)
        } else {
            holder.checkbox.setImageResource(R.drawable.unchecked)
        }
        if (!dataSet[position].itemText.equals("올해 목표를 세워보세요!") && !dataSet[position].itemText.equals("꼭 이루고 싶은 걸 적어보세요")) {
            holder.checkbox.visibility = View.GONE
        }

        holder.checkbox.setOnClickListener {
            if (!dataSet[position].itemText.equals("올해 목표를 세워보세요!") && !dataSet[position].itemText.equals("꼭 이루고 싶은 걸 적어보세요")) {
                checkBucketItem(holder, position)
            }
        }

        holder.removeBtn.setOnClickListener {
            if (!dataSet[position].itemText.equals("올해 목표를 세워보세요!") && !dataSet[position].itemText.equals("꼭 이루고 싶은 걸 적어보세요")) {
                deleteBucketItem(holder, position)
            }
        }
    }

    override fun getItemCount(): Int = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    private fun modifyBucketItem(adapterPosition: Int) {
        if (adapterPosition != RecyclerView.NO_POSITION) {
            val intent = Intent(context, com.bucket.letsbucket.activity.DetailActivity::class.java)
            val data = dataSet[adapterPosition]
            intent.putExtra(
                "DATA", DetailData(
                    data.itemId, data.itemText, data.itemDone, data.itemType,
                    data.itemDoneDate, data.itemTargetDate, data.itemUri, data.itemDetailText,
                    adapterPosition,
                )
            )
            startActivity(context, intent, null)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun checkBucketItem(holder: ThisYearViewHolder, position: Int) {
        dataSet[position].itemDone = !dataSet[position].itemDone

        if (dataSet[position].itemDone) {
            holder.checkbox.setImageResource(R.drawable.checked)
            val alert = AlertDialog.Builder(context, R.style.AlertDialogStyle)
                .setIcon(R.drawable.basic)
                .setTitle("버킷리스트 달성!!")
                .setMessage("축하해요!!")
                .show()
            AnimationDialog(context, DataUtil.ANIM_TYPE.FIRE_WORK).let {
                it.setOnDismissListener {
                    alert.cancel()
                }
                it.show()
            }
        } else {
            holder.checkbox.setImageResource(R.drawable.unchecked)
        }

        notifyDataSetChanged()

        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                LifeBucketDB.getInstance(context)!!.lifebucketDao().updateDone(
                    dataSet[position].itemDone,
                    dataSet[position].itemId
                )
            }.await()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteBucketItem(holder: ThisYearViewHolder, position: Int) {
        var deletedItem: BucketItem? = null

        deletedItem = dataSet[position]
        DataUtil.LIFE_LIST[deletedItem.itemType!!].removeAt(position)

        holder.animToggle = !holder.animToggle
        notifyDataSetChanged()

        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                LifeBucketDB.getInstance(context)!!.lifebucketDao().deleteById(
                    deletedItem.itemId
                )
            }.await()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun dataChanged() {
        LogUtil.d(TAG, "데이터 변경 감지! -> notifyDataSetChange()")
        notifyDataSetChanged()
    }
}