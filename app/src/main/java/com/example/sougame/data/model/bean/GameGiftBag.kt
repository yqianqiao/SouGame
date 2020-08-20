package com.example.sougame.data.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/20 22:04
 * Description: com.example.sougame.data.model.bean
 */
data class GameGiftBag(
    val accgrade: Int? = null,
    val content: String? = null,
    val currentaccgrade: Any? = null,
    val currentpay: String? = null,
    val gameid: Int? = null,
    val gametypeid: Int? = null,
    val gametypename: String? = null,
    val giftbagtypeid: Int? = null,
    val grade: Float? = null,
    val icon: String? = null,
    val isget: Int? = null,
    val name: String? = null,
    val paylimit: Int? = null,
    val playnum: Int? = null,
    val qqgroup: String? = null,
    val smallrecommend_images: String? = null,
    val subject: String? = null,
    val title: String? = null,
    val type: Int? = null,
    val viplevellimit: Int? = null
) : MultiItemEntity {
    companion object {
        const val TITLE = 0
        const val CONTENT = 1
    }

    override val itemType: Int
        get() = if (title != null) TITLE else CONTENT


}