package com.example.sougame.data.model.bean

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/1 20:14
 * Description: com.example.sougame.data.model.bean
 */
data class RechargeRecordBean(
    val gameid: Int,
    val icon: String,
    val orderid: Int,
    val pay: Int,
    val paytime: String,
    val propsname: String,
    val status: Int,
    val subject: String
)