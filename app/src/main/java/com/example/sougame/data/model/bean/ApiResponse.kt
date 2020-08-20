package com.example.sougame.data.model.bean

import com.example.common.network.BaseResponse

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/16 11:49
 * Description: 服务器返回数据的基类
 */
class ApiResponse<T>(var code: Int, var message: String, var response: T) : BaseResponse<T>() {
    override fun isSuccess() = code == 1

    override fun getResponseCode() = code

    override fun getResponseData() = response

    override fun getResponseMsg() = message

}