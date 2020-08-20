package com.example.sougame.data.model.bean

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/20 11:46
 * Description: com.example.sougame.data.model.bean
 */
data class OpenServiceBean (
    val gameid: Int,
    val icon: String,
    val is_show: Int,
    val opentime: String,
    val serverid: Int,
    val servername: String,
    val subject: String,
    val time: String
)