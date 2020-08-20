package com.example.sougame.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.common.ext.nav
import com.example.sougame.R
import com.example.sougame.app.ext.formatNum
import com.example.sougame.app.ext.loadRoundImage
import com.example.sougame.data.model.bean.GamePackBean

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/6 11:32
 * Description: com.example.sougame.ui.adapter
 */
class GamePackAdapter(
    val data: ArrayList<GamePackBean>,
    val actionId1: Int,
    val actionId2: Int,
    val action: (View, Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_TITLE = 0
        const val TYPE_PACK = 1
        const val TYPE_INFO = 2

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_TITLE -> return TitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_game_common, parent, false)
                , actionId1, actionId2
            )
            TYPE_PACK -> return PackViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_game_gift_bag, parent, false), action
            )
            else -> {
                return PackViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_game_gift_bag, parent, false), action
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


    class TitleViewHolder(val view: View, private val actionId1: Int, private val actionId2: Int) :
        RecyclerView.ViewHolder(view) {
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
                nav(view).navigate(actionId1, Bundle().apply {
                    putInt("gameid", bean.gameid)
                })
            }
            view.setOnClickListener {
                nav(view).navigate(
                    actionId2,
                    Bundle().apply {
                        putInt("gameid", bean.gameid)
                    })
            }
        }
    }


    class PackViewHolder(val view: View, val action: (View, Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
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

            tv_receive.text = if (bean.isget == 1) "查看" else "领取"

            tv_receive.setOnClickListener {
                action(it, adapterPosition)
            }
        }

    }


}