package com.example.sougame.ui.adapter

import android.os.Bundle
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.common.ext.nav
import com.example.sougame.R
import com.example.sougame.app.ext.loadFilletImage
import com.example.sougame.app.ext.setNbOnItemClickListener
import com.example.sougame.data.model.bean.BroadsideGameBean
import com.example.sougame.data.model.bean.GameListBean

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/15 16:39
 * Description:
 */
class SearchAdapter(data: MutableList<GameListBean>) :
    BaseQuickAdapter<GameListBean, BaseViewHolder>(R.layout.item_search, data) {

    override fun convert(holder: BaseViewHolder, item: GameListBean) {
        holder.getView<ImageView>(R.id.iv_icon).loadFilletImage(item.icon)
        holder.setText(R.id.tv_title, item.subject)
//        addChildClickViewIds()

    }
}

class BackPageAdapter(data: MutableList<BroadsideGameBean>) :
    BaseQuickAdapter<BroadsideGameBean, BaseViewHolder>(R.layout.item_search, data) {

    override fun convert(holder: BaseViewHolder, item: BroadsideGameBean) {
        holder.getView<ImageView>(R.id.iv_icon).loadFilletImage(item.icon!!)
        holder.setText(R.id.tv_title, item.subject)
//        addChildClickViewIds()

    }
}