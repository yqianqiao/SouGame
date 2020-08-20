package com.example.sougame.data.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/16 18:18
 * Description: com.example.sougame.data.model.bean
 */
@Parcelize
data class UserInfo(
    var token: String,
    var uid: String,

    var accgrade: Int,
    val birthday: String,
    var card: String,
    val couponcount: Int,
    val current: Int,
    val discountpay: Float,
    val education: String,
    val email: String,
    val extern: Int,
    val icon: String,
    val income: String,
    val location: String,
    val logintypelimit: Int,
    val minus: Int,
    val nextLevelpay: Int,
    val nextsignaccgrade: Int,
    val nickname: String,
    val occupation: String,
    val pay: String,
    val percentage: String,
    val phone: String,
    val qqnumber: String,
    var realname: String,
    val sex: Int,
    val subscribeweixinpublic: Int,
    val svip: Int,
    val svippop: String,
    val username: String,
    val viplevel: Int
) : Parcelable