package com.example.sougame.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.sougame.R
import com.example.sougame.data.model.bean.Coupon
import com.example.sougame.data.model.bean.InviteFriendsBean
import com.example.sougame.databinding.ItemCouponBinding
import com.example.sougame.databinding.ItemInviteFriendsBinding

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/2 20:07
 * Description: com.example.sougame.ui.adapter
 */
class InviteFriendsAdapter(data:ArrayList<InviteFriendsBean>) :
    BaseQuickAdapter<InviteFriendsBean, BaseDataBindingHolder<ItemInviteFriendsBinding>>(
        R.layout.item_invite_friends,
        data
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemInviteFriendsBinding>,
        item: InviteFriendsBean
    ) {
        holder.dataBinding?.run {
            bean = item
        }
    }
}