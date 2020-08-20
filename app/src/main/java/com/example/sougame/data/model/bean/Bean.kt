package com.example.sougame.data.model.bean

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/3 16:26
 * Description: com.example.sougame.data.model.bean
 */

data class NumBean(
    var num: Int,
    var isClickable: Boolean
)

data class LetterBean(
    var letter: String,
    var isClickable: Boolean
)

data class SignBean(
    val days: Int,
    val accgrade: Int
)

data class GiftBagBean(
    val giftbagtypeid: Int,
    val giftbagid: Int,
    val name: String,
    val subject: String,
    val icon: String,
    val content: String,
    val code: String,
    val expire: String,
    val info: String
)
