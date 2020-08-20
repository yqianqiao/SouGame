package com.example.sougame.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.sougame.R
import com.example.sougame.app.ext.loadFilletImage
import com.example.sougame.data.model.bean.GameGiftBag
import com.example.sougame.data.model.bean.OpenServiceBean
import com.example.sougame.databinding.ItemGameGiftBagBinding
import com.example.sougame.databinding.ItemOpenServiceBinding

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/20 20:43
 * Description: com.example.sougame.ui.adapter
 */
class GameGiftBagAdapter(data: ArrayList<GameGiftBag>) :
    BaseQuickAdapter<GameGiftBag, BaseDataBindingHolder<ItemGameGiftBagBinding>>(
        R.layout.item_game_gift_bag,
        data
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemGameGiftBagBinding>,
        item: GameGiftBag
    ) {
        holder.dataBinding?.run {
            if (item.type == 2) {
                tvTag.visibility = View.INVISIBLE
                ivVip.visibility = View.VISIBLE
            }
            tvTitle.text = item.name
            tvInfo.text = item.content
        }
    }
}