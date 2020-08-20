package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.ProgressBean
import com.example.sougame.data.model.bean.SignBean
import com.example.sougame.data.model.bean.UserInfo
import com.example.sougame.data.repository.request.HttpRequestManger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/28 16:09
 * Description: com.example.sougame.viewmodel
 */
class MyViewModel(app: Application) : BaseViewModel(app) {


    var getUserInfo = MutableLiveData<ResultState<UserInfo>>()
    var progressData = MutableLiveData<ResultState<ProgressBean>>()
    var signRuquest = MutableLiveData<ResultState<SignBean>>()


    fun getUserInfo() {
        request({
            HttpRequestManger.instance.getUserInfo()
        }, getUserInfo)
    }

    fun getProgressData() {
        request({
            HttpRequestManger.instance.getProgress()
        }, progressData)
    }

    fun sign() {
        request({
            HttpRequestManger.instance.sign()
        }, signRuquest)
    }

    fun checkUpdate() {
        viewModelScope.launch {
            ToastUtils.showShort(withContext(Dispatchers.IO) {
                delay(1000)
                "当前已是最新版本"
            })
        }
    }

}