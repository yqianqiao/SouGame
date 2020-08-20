package com.example.sougame.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.sougame.R
import com.example.sougame.app.ext.loadFilletImage
import com.example.sougame.data.model.bean.OpenServiceBean
import com.example.sougame.databinding.ItemOpenServiceBinding

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/20 11:40
 * Description: com.example.sougame.ui.adapter
 */
class OpenServiceAdapter(data: ArrayList<OpenServiceBean>) :
    BaseQuickAdapter<OpenServiceBean, BaseDataBindingHolder<ItemOpenServiceBinding>>(
        R.layout.item_open_service,
        data
    ) {
     var type = 1
    override fun convert(
        holder: BaseDataBindingHolder<ItemOpenServiceBinding>,
        item: OpenServiceBean
    ) {
        holder.dataBinding?.run {
            ivIcon.loadFilletImage(item.icon)
            tvTitle.text = item.subject
            tvInfo.text = item.servername
            tvText.text = if (type == 1) "已开服${item.opentime}" else "${item.opentime}开服"
            tvStart.text = if (type == 1) "开始游戏" else "开服提醒"
        }
    }


}