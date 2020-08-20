package com.example.sougame.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.sougame.R
import com.example.sougame.data.model.bean.GameListBean

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/17 10:56
 * Description: 热门推荐adapter
 */
class HotAdapter(data: ArrayList<GameListBean>) :
    BaseQuickAdapter<GameListBean, BaseViewHolder>(R.layout.item_search_hot, data) {

    override fun convert(holder: BaseViewHolder, item: GameListBean) {
        holder.setText(R.id.tv_search_title, item.subject)
            .setText(R.id.tv_search_description, item.description)
    }
}