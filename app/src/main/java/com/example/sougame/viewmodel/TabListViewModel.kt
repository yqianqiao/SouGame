package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.CouponBean
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/1 16:44
 * Description: com.example.sougame.viewmodel
 */
class TabListViewModel(application: Application) : BaseViewModel(application) {

    val couponList = MutableLiveData<ResultState<CouponBean>>()

    fun getCouponList() {
        request({ HttpRequestManger.instance.getCouponList() }, couponList)
    }
}