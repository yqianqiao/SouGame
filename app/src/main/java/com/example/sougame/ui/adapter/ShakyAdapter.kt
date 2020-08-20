package com.example.sougame.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.sougame.R
import com.example.sougame.app.ext.loadImage
import com.example.sougame.app.ext.loadFilletImage
import com.example.sougame.data.model.bean.GameListBean
import com.example.sougame.data.model.bean.ShakyBean
import com.example.sougame.databinding.ItemNoticeBinding
import com.example.sougame.databinding.ItemShakyBinding

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/20 10:34
 * Description: com.example.sougame.ui.adapter
 */
class ShakyAdapter(data: MutableList<ShakyBean>) :
    BaseQuickAdapter<ShakyBean, BaseDataBindingHolder<ItemShakyBinding>>(
        R.layout.item_shaky,
        data
    ) {

    override fun convert(holder: BaseDataBindingHolder<ItemShakyBinding>, item: ShakyBean) {
        holder.dataBinding?.let {
            it.ivImg.loadFilletImage(item.cover!!)
            it.tvTitle.text = item.subject
            it.tvInfo.text = "活动时间：${item.begin} - ${item.end}"
        }
    }
}

class NoticeAdapter(data: MutableList<ShakyBean>) :
    BaseQuickAdapter<ShakyBean, BaseDataBindingHolder<ItemNoticeBinding>>(
        R.layout.item_notice,
        data
    ) {

    override fun convert(holder: BaseDataBindingHolder<ItemNoticeBinding>, item: ShakyBean) {
        holder.dataBinding?.let {
            it.tvNotice.text = item.informationtypename
            it.tvDescription.text = item.subject
            it.tvTime.text = item.addtime
        }
    }
}


