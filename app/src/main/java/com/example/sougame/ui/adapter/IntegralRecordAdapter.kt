package com.example.sougame.ui.adapter

import android.text.TextUtils
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.sougame.R
import com.example.sougame.data.model.bean.IntegralRecordBean
import com.example.sougame.data.model.bean.RechargeRecordBean
import com.example.sougame.databinding.ItemIntegralRecordBinding

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/1 20:52
 * Description: com.example.sougame.ui.adapter
 */
class IntegralRecordAdapter :
    BaseQuickAdapter<IntegralRecordBean, BaseDataBindingHolder<ItemIntegralRecordBinding>>(
        R.layout.item_integral_record
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemIntegralRecordBinding>,
        item: IntegralRecordBean
    ) {
        holder.dataBinding?.run {
            tvTitle.text = item.content
            if (TextUtils.equals("增加", item.type)) {
                tvTitle.setTextColor(ContextCompat.getColor(context, R.color.color_yellow))
                tvInfo.text = "获得"
                tvNum.text = "+${item.accgrade}积分"
                tvNum.setTextColor(ContextCompat.getColor(context, R.color.color_yellow))
            } else {
                tvTitle.setTextColor(ContextCompat.getColor(context, R.color.color_easy_red))
                tvInfo.text = "消耗"
                tvNum.text = "-${item.accgrade}积分"
                tvNum.setTextColor(ContextCompat.getColor(context, R.color.color_easy_red))
            }

            tvTime.text = item.addtime

        }
    }


}