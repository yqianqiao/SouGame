package com.example.sougame.ui.adapter

import android.util.Log
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.sougame.R
import com.example.sougame.app.ext.formatNum
import com.example.sougame.app.ext.loadFilletImage
import com.example.sougame.app.ext.loadImage
import com.example.sougame.app.ext.setNbOnItemClickListener
import com.example.sougame.data.model.bean.BroadsideGameBean
import com.example.sougame.data.model.bean.EntityGoodsBean
import com.example.sougame.data.model.bean.GameGiftBag
import com.example.sougame.data.model.bean.ShakyBean
import com.example.sougame.databinding.ItemBroadsideIntegralBinding

/**
 *   Created by YX on 2020-07-17 15:34.
 */
/**
 * 更多游戏
 */
class BroadsideGameAdapter :
    BaseMultiItemQuickAdapter<BroadsideGameBean, BaseViewHolder>() {
    override fun convert(holder: BaseViewHolder, item: BroadsideGameBean) {
        when (holder.itemViewType) {
            BroadsideGameBean.TITLE -> holder.setText(R.id.tv_title, item.title)
            BroadsideGameBean.CONTENT -> {
                holder.getView<ImageView>(R.id.iv_icon).loadFilletImage(item.icon!!)
                holder.setText(R.id.tv_title, item.subject)
                    .setText(R.id.tv_info, item.gametypename)
                    .setText(R.id.tv_play_num, item.playnum?.formatNum())


            }
        }
    }

    init {
        addItemType(BroadsideGameBean.TITLE, R.layout.item_game_title)
        addItemType(BroadsideGameBean.CONTENT, R.layout.item_game_common)
    }
}

/**
 * 资讯
 */
class InformationAdapter : BaseMultiItemQuickAdapter<ShakyBean, BaseViewHolder>() {
    init {
        addItemType(ShakyBean.TITLE, R.layout.item_information_title)
        addItemType(ShakyBean.CONTENT, R.layout.item_information_content)
    }

    override fun convert(holder: BaseViewHolder, item: ShakyBean) {

        when (holder.itemViewType) {
            ShakyBean.TITLE -> {
                Log.e("aaaa", item.title)
                holder.setText(R.id.tv_title, "//${item.title}//")
            }
            ShakyBean.CONTENT -> {

                Log.e("aaaaccccc", item.title)

                holder.setText(R.id.tv_notice1, item.title)
                    .setText(R.id.tv_description, item.subject)

                addChildClickViewIds(R.id.tv_start)
            }
        }
    }
}

class BroadsideBagAdapter : BaseMultiItemQuickAdapter<GameGiftBag, BaseViewHolder>() {

    init {
        addItemType(GameGiftBag.TITLE, R.layout.item_broadside_bag)
        addItemType(GameGiftBag.CONTENT, R.layout.item_game_gift_bag)
    }

    override fun convert(holder: BaseViewHolder, item: GameGiftBag) {
        when (holder.itemViewType) {
            ShakyBean.TITLE -> {
                holder.setText(R.id.tv_title, item.title)
                    .setBackgroundResource(
                        R.id.tv_title,
                        if (item.type != 2) R.color.color_easy_red else R.drawable.bg_bag
                    )
                    .setTextColor(
                        R.id.tv_title,
                        if (item.type != 2) R.color.white else R.color.color_vip
                    )
            }
            ShakyBean.CONTENT -> {

                holder.setVisible(R.id.tv_tag, item.type != 2)
                    .setVisible(R.id.iv_vip, item.type == 2)
                    .setGone(R.id.tv_hint, item.type != 2)
                    .setText(
                        R.id.tv_hint,
                        "温馨提示:充值${item.paylimit}元即可获得该礼包！(${item.currentpay}/${item.paylimit})"
                    )
                    .setText(R.id.tv_title, item.name)
                    .setText(R.id.tv_info, item.content)


                addChildClickViewIds(R.id.tv_receive)
                setNbOnItemClickListener { adapter, view, position ->

                }
            }
        }
    }
}

class IntegralAdapter :
    BaseQuickAdapter<EntityGoodsBean, BaseDataBindingHolder<ItemBroadsideIntegralBinding>>(R.layout.item_broadside_integral) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemBroadsideIntegralBinding>,
        item: EntityGoodsBean
    ) {
        holder.dataBinding?.let {
            it.ivIcon.loadImage(item.images)
            it.tvTitle.text = item.subject
            it.tvInfo.text = item.accgrade.toString()
        }
    }

}