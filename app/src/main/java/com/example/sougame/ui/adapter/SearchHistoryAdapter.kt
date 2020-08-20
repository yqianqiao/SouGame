package com.example.sougame.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.sougame.R
import com.example.sougame.data.model.bean.GameListBean

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/16 18:08
 * Description: com.example.sougame.ui.adapter
 */
class SearchHistoryAdapter(data: ArrayList<GameListBean>) :
    BaseQuickAdapter<GameListBean, BaseViewHolder>(R.layout.item_search_history) {
    override fun convert(holder: BaseViewHolder, item: GameListBean) {
        holder.setText(R.id.tv_search_history,item.subject)
    }
}