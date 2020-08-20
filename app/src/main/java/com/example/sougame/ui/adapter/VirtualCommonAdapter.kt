package com.example.sougame.ui.adapter

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.common.ext.nav
import com.example.sougame.R
import com.example.sougame.app.ext.*
import com.example.sougame.app.util.CacheUtil
import com.example.sougame.data.model.bean.GamePackBean
import com.example.sougame.data.model.bean.LetterBean
import com.example.sougame.data.model.bean.VirtualGiftBean
import com.example.sougame.databinding.ItemLetterTextBinding
import com.example.sougame.databinding.ItemVirtualGiftBinding

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/8 16:20
 * Description: com.example.sougame.ui.adapter
 */
class LetterAdapter :
    BaseQuickAdapter<LetterBean, BaseDataBindingHolder<ItemLetterTextBinding>>(
        R.layout.item_letter_text
    ) {
    private var oldPosition = 0
    override fun convert(holder: BaseDataBindingHolder<ItemLetterTextBinding>, item: LetterBean) {
        holder.dataBinding?.run {
            tvLetter.text = item.letter
            tvLetter.setTextColor(if (item.isClickable) Color.WHITE else Color.parseColor("#333333"))
            tvLetter.setBackgroundColor(
                if (item.isClickable) Color.parseColor("#fea509") else Color.parseColor(
                    "#efefef"
                )
            )

        }
        setNbOnItemClickListener { adapter, view, position ->
            LogUtils.e(position, oldPosition)
            if (position == oldPosition) return@setNbOnItemClickListener
            data[oldPosition].isClickable = false
            data[position].isClickable = true
            oldPosition = position
            notifyDataSetChanged()
        }
    }
}

class VirtualGiftAdapter :
    BaseQuickAdapter<GamePackBean, BaseDataBindingHolder<ItemVirtualGiftBinding>>(
        R.layout.item_virtual_gift
    ) {

    override fun convert(
        holder: BaseDataBindingHolder<ItemVirtualGiftBinding>,
        item: GamePackBean
    ) {
        holder.dataBinding?.run {
            if (item.subject != null) {
                ivImg.visibility = View.VISIBLE
                tvSubject.visibility = View.VISIBLE
                ivImg.loadRoundImage(if (TextUtils.isEmpty(item.img)) item.icon else item.img)
                tvSubject.text = item.subject
            } else {
                ivImg.visibility = View.GONE
                tvSubject.visibility = View.GONE

            }
            tvName.text = item.name
            tvContent.text = item.content
            tvAccgrade.text = if (item.isget == 1) "查看" else "${item.accgrade}积分"
            if (item.currentaccgrade == 0)
                CacheUtil.getUser()?.let {
                    item.currentaccgrade = it.accgrade
                }
            tvAccgrade.setBackgroundColor(
                ContextCompat.getColor(
                    context, if (item.accgrade > item.currentaccgrade) {
                        R.color.color_7b
                    } else {
                        R.color.color_yellow
                    }
                )
            )


        }
    }
}

class GiftBagTypeAdapter(val data: ArrayList<GamePackBean>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_TITLE = 0
        const val TYPE_PACK = 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_TITLE -> return TitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_virtual_gift, parent, false)
            )
            TYPE_PACK -> return PackViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_game_gift_bag, parent, false)
            )
            else -> {
                return PackViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_game_gift_bag, parent, false)
                )
            }
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_TITLE -> (holder as TitleViewHolder).bind(data[position])
            TYPE_PACK -> (holder as PackViewHolder).bind(data[position])
        }
    }


    override fun getItemViewType(position: Int): Int {
        return data[position].itemType
    }


    class TitleViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val iv_icon = view.findViewById<ImageView>(R.id.iv_icon)
        private val tv_title = view.findViewById<TextView>(R.id.tv_title)
        private val tv_info = view.findViewById<TextView>(R.id.tv_info)
        private val tv_play_num = view.findViewById<TextView>(R.id.tv_play_num)
        private val tv_start = view.findViewById<TextView>(R.id.tv_start)
        fun bind(bean: GamePackBean) {
            iv_icon.loadRoundImage(bean.icon)
            tv_title.text = bean.subject
            tv_info.text = bean.gametypename
            tv_play_num.text = bean.playnum.formatNum()
            tv_start.setOnClickListener {
                nav(view).navigate(R.id.action_main_fragment_to_webFragment, Bundle().apply {
                    putInt("gameid", bean.gameid)
                })
            }
            view.setOnClickListener {
                nav(view).navigate(
                    R.id.action_main_fragment_to_gameDetailsFragment,
                    Bundle().apply {
                        putInt("gameid", bean.gameid)
                    })
            }
        }
    }


    class PackViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val tv_tag = view.findViewById<TextView>(R.id.tv_tag)
        private val tv_title = view.findViewById<TextView>(R.id.tv_title)
        private val tv_info = view.findViewById<TextView>(R.id.tv_info)
        private val tv_receive = view.findViewById<TextView>(R.id.tv_receive)
        private val iv_vip = view.findViewById<ImageView>(R.id.iv_vip)

        fun bind(bean: GamePackBean) {
            if (bean.type == 2) {
                tv_tag.visibility = View.INVISIBLE
                iv_vip.visibility = View.VISIBLE
            } else {
                tv_tag.visibility = View.VISIBLE
                iv_vip.visibility = View.INVISIBLE
            }
            tv_title.text = bean.name
            tv_info.text = bean.content

            tv_receive.setOnClickListener {

            }
        }

    }


}

