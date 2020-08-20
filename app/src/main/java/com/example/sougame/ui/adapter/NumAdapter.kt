package com.example.sougame.ui.adapter

import android.graphics.Color
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.sougame.R
import com.example.sougame.app.ext.setNbOnItemClickListener
import com.example.sougame.data.model.bean.InviteFriendsBean
import com.example.sougame.data.model.bean.NumBean
import com.example.sougame.databinding.ItemInviteFriendsBinding
import com.example.sougame.databinding.ItemNumBinding

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/3 15:47
 * Description: com.example.sougame.ui.adapter
 */
class NumAdapter :
    BaseQuickAdapter<NumBean, BaseDataBindingHolder<ItemNumBinding>>(
        R.layout.item_num
    ) {
    private var oldPosition = 0
    override fun convert(
        holder: BaseDataBindingHolder<ItemNumBinding>,
        item: NumBean
    ) {
        holder.dataBinding?.run {
            tvNum.text = item.num.toString()
            tvNum.setTextColor(if (item.isClickable) Color.WHITE else Color.parseColor("#333333"))
            tvNum.setBackgroundColor(
                if (item.isClickable) Color.parseColor("#fea509") else Color.parseColor(
                    "#efefef"
                )
            )

        }
        setNbOnItemClickListener { adapter, view, position ->
            LogUtils.e(position,oldPosition)
            if (position == oldPosition) return@setNbOnItemClickListener
            data[oldPosition].isClickable = false
            data[position].isClickable = true
            oldPosition = position
            notifyDataSetChanged()
        }
    }
}