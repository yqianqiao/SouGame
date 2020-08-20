package com.example.common.network

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/10 23:16
 * Description: com.example.common.network
 */
abstract class BaseResponse<T> {
    //抽象方法，用户的基类继承该类时，需要重写该方法
    abstract fun isSuccess(): Boolean

    abstract fun getResponseData(): T

    abstract fun getResponseCode(): Int

    abstract fun getResponseMsg(): String
}