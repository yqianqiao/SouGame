package com.example.sougame.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.sougame.R
import com.example.sougame.data.model.bean.GameGiftBag
import com.example.sougame.data.model.bean.GameType
import com.example.sougame.databinding.ItemGameGiftBagBinding
import com.example.sougame.databinding.ItemGameTypeTextBinding

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/22 11:23
 * Description: com.example.sougame.ui.adapter
 */
class GameTypeTextAdapter(data: ArrayList<GameType>) :
    BaseQuickAdapter<GameType, BaseDataBindingHolder<ItemGameTypeTextBinding>>(
        R.layout.item_game_type_text,
        data
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemGameTypeTextBinding>,
        item: GameType
    ) {
        holder.dataBinding?.tvGameType?.text = item.name

    }
}


class GameTypeStringAdapter :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemGameTypeTextBinding>>(
        R.layout.item_game_type_text
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemGameTypeTextBinding>,
        item: String
    ) {
        holder.dataBinding?.tvGameType?.text = item

    }
}