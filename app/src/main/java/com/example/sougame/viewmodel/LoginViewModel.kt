package com.example.sougame.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.callback.livedata.StringLiveData
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.RegisterBean
import com.example.sougame.data.model.bean.UserInfo
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/28 10:01
 * Description: com.example.sougame.viewmodel
 */
class LoginViewModel(app: Application) : BaseViewModel(app) {


    var loginResult = MutableLiveData<ResultState<UserInfo>>()

    var registerResult = MutableLiveData<ResultState<RegisterBean>>()
    var phoneLoginResult = MutableLiveData<ResultState<Any>>()
    var bindPhoneCodeResult = MutableLiveData<ResultState<Any>>()
    var bindPhoneResult = MutableLiveData<ResultState<Any>>()


    fun loginReq(
        username: String? = null,
        password: String? = null,
        type: String? = null,
        phone: String? = null,
        code: String? = null,
        weixincode: String? = null,
        qqcode: String? = null,
        qqopenid: String? = null,
        qqaccesstoken: String? = null
    ) {
        request({
            HttpRequestManger.instance.login(
                username, phone, code, password, type, weixincode, qqcode, qqopenid, qqaccesstoken

            )
        }, loginResult)
    }


    fun registerReq(username: String, password: String, type: String) {
        request({
            HttpRequestManger.instance.register(
                username = username,
                password = password,
                type = type
            )

        }, registerResult)
    }

    fun phoneLogin(phone: String) {
        request({ HttpRequestManger.instance.phoneLogin(phone) }, phoneLoginResult)
    }


    fun bindPhoneCode(phone: String) {
        request({
            HttpRequestManger.instance.bindPhoneCode(phone)
        }, bindPhoneCodeResult)
    }

    fun bindPhone(phone: String, code: String) {
        request({
            HttpRequestManger.instance.bindPhone(phone, code)
        }, bindPhoneResult)
    }

}