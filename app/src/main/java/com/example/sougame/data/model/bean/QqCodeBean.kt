package com.example.sougame.data.model.bean

/**
 *   Created by YX on 2020-08-12 16:34.
 */
data class QqCodeBean(
    val ret:Int,
    val openid:String,
    val access_token:String,
    val pay_token:String,
    val expires_in:Int,
    val code:String,
    val proxy_code:String,
    val proxy_expires_in:String,
    val pf:String,
    val pfkey:String,
    val msg:String,
    val login_cost:String,
    val query_authority_cost:String,
    val authority_cost:String,
    val expires_time:String
)