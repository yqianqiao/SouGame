package com.example.sougame.data.model.bean

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/1 17:25
 * Description: com.example.sougame.data.model.bean
 */

data class CouponBean(
    val countCoupon: Int,
    val lose: ArrayList<Coupon>,
    val unused: ArrayList<Coupon>
)

data class Coupon(
    val coupon_id: Int,
    val coupon_name: String,
    val coupon_type: Int,
    val create_time: String,
    val describe: String,
    val discount: String,
    val discount_type: Int,
    val effective_type: Int,
    val failure_time: String,
    val game_id: Int,
    val id: Int,
    val is_open: Int,
    val is_use: Int,
    val subject: Any,
    val uid: Int,
    val userful_life: String
)

