package com.example.sougame.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.sougame.R
import com.example.sougame.app.ext.loadImage
import com.example.sougame.data.model.bean.EntityGoodsBean
import com.example.sougame.databinding.ItemEntityGoodsBinding

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/9 15:14
 * Description: com.example.sougame.ui.adapter
 */
class EntityGoodsAdapter :
    BaseQuickAdapter<EntityGoodsBean, BaseDataBindingHolder<ItemEntityGoodsBinding>>(
        R.layout.item_entity_goods
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemEntityGoodsBinding>,
        item: EntityGoodsBean
    ) {
        holder.dataBinding?.run {
            ivImg.loadImage(item.images)
            tvName.text = item.subject
            tvAccgrade.text = item.accgrade.toString()
            tvInventory.text = "剩余：${item.inventory}"

        }
    }
}