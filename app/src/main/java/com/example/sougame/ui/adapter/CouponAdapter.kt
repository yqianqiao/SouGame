package com.example.sougame.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.sougame.R
import com.example.sougame.data.model.bean.Coupon
import com.example.sougame.databinding.ItemCouponBinding

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/1 17:40
 * Description: com.example.sougame.ui.adapter
 */
class CouponAdapter :
    BaseQuickAdapter<Coupon, BaseDataBindingHolder<ItemCouponBinding>>(
        R.layout.item_coupon
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemCouponBinding>,
        item: Coupon
    ) {
        holder.dataBinding?.run {
            coupon = item
            tvDiscount.text = "${item.discount}折"
        }
    }
}