package com.example.letsbucket.adaptor

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letsbucket.PopupDialog
import com.example.letsbucket.R
import com.example.letsbucket.data.BucketItem
import com.example.letsbucket.db.LifeBucketDB
import com.example.letsbucket.db.ThisYearBucketDB
import com.example.letsbucket.util.DataUtil
import com.example.letsbucket.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class BucketAdapter(
    private val context: Context,
    private val from: DataUtil.FROM_TYPE,
    private val lifeType: Int?,
    private val dataSet: ArrayList<BucketItem>
) :
    RecyclerView.Adapter<BucketAdapter.ThisYearViewHolder>() {

    private var TAG = "BucketAdapter"

    @SuppressLint("NotifyDataSetChanged")
    inner class ThisYearViewHolder(context: Context, view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = itemView.findViewById(R.id.text)
        val checkbox: ImageView = itemView.findViewById(R.id.checkbox)
        val removeBtn: ImageView = itemView.findViewById(R.id.remove)
        val layoutCheckBox: LinearLayout = itemView.findViewById(R.id.layout_checkbox)
        val layoutRemove: LinearLayout = itemView.findViewById(R.id.layout_remove)
        val toLeftAnim: Animation = AnimationUtils.loadAnimation(context, R.anim.translate_left)
        val toRightAnim: Animation = AnimationUtils.loadAnimation(context, R.anim.translate_right)
        var animToggle: Boolean by Delegates.observable(false) {
            property, oldValue, newValue ->
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
                LogUtil.d(TAG, adapterPosition.toString() + "is clicked")
                modifyBucketItem(adapterPosition)
            }

            view.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    LogUtil.d(TAG, "Long Click Event Start")
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
        return ThisYearViewHolder(this.context, view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ThisYearViewHolder, position: Int) {
        holder.textView.text = dataSet[position].itemText

        holder.checkbox.setOnClickListener {
            LogUtil.d(TAG, "체크박스 클릭")
            checkBucketItem(holder, position)
        }

        holder.removeBtn.setOnClickListener {
            LogUtil.d(TAG, "삭제 버튼 클릭")
            deleteBucketItem(holder, position)
        }
    }

    override fun getItemCount(): Int = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    private fun modifyBucketItem(adapterPosition: Int) {
        if (adapterPosition != RecyclerView.NO_POSITION) {
            var popup: PopupDialog? = null
            when (from) {
                DataUtil.FROM_TYPE.THIS_YEAR -> {
                    popup = PopupDialog(
                        context,
                        DataUtil.MODE_TYPE.MODIFY,
                        DataUtil.FROM_TYPE.THIS_YEAR,
                        lifeType,
                        adapterPosition
                    )
                }
                DataUtil.FROM_TYPE.LIFE -> {
                    popup = PopupDialog(
                        context,
                        DataUtil.MODE_TYPE.MODIFY,
                        DataUtil.FROM_TYPE.LIFE,
                        lifeType,
                        adapterPosition
                    )
                }
                else -> {}
            }
            popup!!.setOnDismissListener {
                LogUtil.d(TAG, "팝업 종료 -> 리스트 새로고침")
                notifyDataSetChanged()
//                        modifyToDB(adapterPosition)
            }
            popup.show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun checkBucketItem(holder: ThisYearViewHolder, position: Int) {
        dataSet[position].itemDone = !dataSet[position].itemDone

        if (dataSet[position].itemDone) {
            holder.checkbox.setImageResource(R.drawable.checked)
        } else {
            holder.checkbox.setImageResource(R.drawable.unchecked)
        }

        notifyDataSetChanged()

        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                when (from) {
                    DataUtil.FROM_TYPE.THIS_YEAR -> {
                        ThisYearBucketDB.getInstance(context)!!.thisYearBucketDao().updateDone(
                            dataSet[position].itemDone,
                            dataSet[position].itemId
                        )
                    }
                    DataUtil.FROM_TYPE.LIFE -> {
                        LifeBucketDB.getInstance(context)!!.lifebucketDao().updateDone(
                            dataSet[position].itemDone,
                            dataSet[position].itemId
                        )
                    }
                    else -> {}
                }
            }.await()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteBucketItem(holder: ThisYearViewHolder, position: Int) {
        var deletedItem: BucketItem? = null

        if (from == DataUtil.FROM_TYPE.THIS_YEAR) {
            deletedItem = dataSet[position]
            DataUtil.thisYearBucketList.removeAt(position)
        } else {
            deletedItem = dataSet[position]
            DataUtil.lifelist[lifeType!!].removeAt(position)
        }
        holder.animToggle = !holder.animToggle
        notifyDataSetChanged()

        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                when (from) {
                    DataUtil.FROM_TYPE.THIS_YEAR -> {
                        ThisYearBucketDB.getInstance(context)!!.thisYearBucketDao().deleteById(
                            deletedItem.itemId
                        )
                    }
                    DataUtil.FROM_TYPE.LIFE -> {
                        LifeBucketDB.getInstance(context)!!.lifebucketDao().deleteById(
                            deletedItem.itemId
                        )
                    }
                    else -> {}
                }
            }.await()
        }
    }
}