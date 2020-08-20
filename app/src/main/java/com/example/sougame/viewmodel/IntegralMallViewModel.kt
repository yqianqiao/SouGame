package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.SignBean
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 *   Created by YX on 2020-07-27 14:50.
 */
class IntegralMallViewModel(application: Application) :BaseViewModel(application){

    var signRuquest = MutableLiveData<ResultState<SignBean>>()
    fun sign() {
        request({
            HttpRequestManger.instance.sign()
        }, signRuquest)
    }
}