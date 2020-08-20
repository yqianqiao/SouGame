package com.example.sougame.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.sougame.R
import com.example.sougame.app.ext.loadFilletImage
import com.example.sougame.data.model.bean.OpenServiceBean
import com.example.sougame.data.model.bean.RechargeRecordBean
import com.example.sougame.databinding.ItemOpenServiceBinding
import com.example.sougame.databinding.ItemRechargeRecordBinding

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/1 20:20
 * Description: com.example.sougame.ui.adapter
 */
class RechargeRecordAdapter() :
    BaseQuickAdapter<RechargeRecordBean, BaseDataBindingHolder<ItemRechargeRecordBinding>>(
        R.layout.item_recharge_record
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemRechargeRecordBinding>,
        item: RechargeRecordBean
    ) {
        holder.dataBinding?.run {
            ivIcon.loadFilletImage(item.icon)
            tvTitle.text = item.subject
            tvTime.text = item.paytime
            tvPal.text = "￥${item.pay}"
        }
    }


}