package com.example.sougame.data.model.bean

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/8 17:48
 * Description: com.example.sougame.data.model.bean
 */
data class VirtualGiftBean(
    val accgrade: Int,
    val id: Int,
    val content: String,
    val img: String,
    val name: String,
    val subject: String,

    val currentpay: String,
    val gametypename: String,
    val qqgroup: String,
    val smallrecommend_images: String,
    val currentaccgrade: Int,
    val gameid: Int,
    val gametypeid: Int,
    val giftbagtypeid: Int,
    val grade: Float,
    val isget: Int,
    val paylimit: Int,
    val playnum: Int,
    val type: Int,
    val viplevellimit: Int
)