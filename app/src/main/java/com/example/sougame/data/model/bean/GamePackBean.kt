package com.example.sougame.data.model.bean

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/6 11:50
 * Description: com.example.sougame.data.model.bean
 */

data class GamePackBean(
    val accgrade: Int = 0,
    val content: String? = null,
    var currentaccgrade: Int = 0,
    val currentpay: String? = null,
    val gameid: Int,
    val gametypeid: Int? = null,
    val giftbagtypeid: Int? = null,
    val grade: Float? = null,
    val isget: Int? = null,
    val paylimit: Int? = null,
    val playnum: Int,
    val type: Int? = null,
    val viplevellimit: Int? = null,
    val gametypename: String? = null,
    val icon: String? = null,
    val name: String? = null,
    val qqgroup: String? = null,
    val smallrecommend_images: String? = null,
    var subject: String? = null,
    val img: String? = null,
    val code: String? = null,
    val expire: String? = null,
    val info: String? = null,
    var itemType: Int,
    val giftbagid: Int? = null,
    val id: Int? = null
)