package com.example.sougame.app.util

import android.text.TextUtils
import com.example.sougame.data.model.bean.UserInfo
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import com.google.gson.reflect.TypeToken


/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/16 18:17
 * Description: com.example.sougame.app.util
 */
object CacheUtil {
    /**
     * 获取保存的账户信息
     */
    fun getUser(): UserInfo? {
        val kv = MMKV.mmkvWithID("app")
        val userStr = kv.decodeString("user")
        return if (TextUtils.isEmpty(userStr))
            null
        else
            Gson().fromJson(userStr, UserInfo::class.java)
    }

    /**
     * 设置账户信息
     */
    fun setUser(userResponse: UserInfo?) {
        val kv = MMKV.mmkvWithID("app")
        if (userResponse == null) {
            kv.encode("user", "")
            setIsLogin(false)
        } else {
            kv.encode("user", Gson().toJson(userResponse))
            setIsLogin(true)
        }

    }

    fun getUserList(): ArrayList<UserInfo> {
        val kv = MMKV.mmkvWithID("app")
        val userStr = kv.decodeString("userList")
        return if (TextUtils.isEmpty(userStr))
            arrayListOf()
        else
            Gson().fromJson(userStr, object : TypeToken<List<UserInfo>>() {}.type)
    }

//    fun setUserList(): ArrayList<UserInfo?>? {
//        val kv = MMKV.mmkvWithID("app")
//        val userStr = kv.decodeString("userList")
//        return if (TextUtils.isEmpty(userStr))
//            null
//        else
//            Gson().fromJson(userStr, object : TypeToken<List<UserInfo>>() {}.type)
//    }


    /**
     * 是否已经登录
     */
    fun isLogin(): Boolean {
        val kv = MMKV.mmkvWithID("app")
        return kv.decodeBool("login", false)
    }

    /**
     * 设置是否已经登录
     */
    fun setIsLogin(isLogin: Boolean) {
        val kv = MMKV.mmkvWithID("app")
        kv.encode("login", isLogin)
    }

    /**
     * 是否是第一次登陆
     */
    fun isFirst(): Boolean {
        val kv = MMKV.mmkvWithID("app")
        return kv.decodeBool("first", true)
    }

    /**
     * 是否是第一次登陆
     */
    fun setFirst(first: Boolean): Boolean {
        val kv = MMKV.mmkvWithID("app")
        return kv.encode("first", first)
    }


    fun setToken(token: String?) {
        val kv = MMKV.mmkvWithID("app")
        kv.encode("token", token)
    }

    fun getToken(): String? {
        val kv = MMKV.mmkvWithID("app")
        return kv.decodeString("token")
    }

    fun setUid(uid: String?) {
        val kv = MMKV.mmkvWithID("app")
        kv.encode("uid", uid)
    }

    fun getUid(): String? {
        val kv = MMKV.mmkvWithID("app")
        return kv.decodeString("uid")
    }

}