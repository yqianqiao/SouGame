package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.IntegralRecordBean
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/9 17:09
 * Description: com.example.sougame.viewmodel
 */
class IntegralListViewModel(application: Application) : BaseViewModel(application) {

    val integralRecord = MutableLiveData<ResultState<ArrayList<IntegralRecordBean>>>()


    fun getIntegralRecord(type: String) {
        request({ HttpRequestManger.instance.getIntegralRecord(type) }, integralRecord)
    }

}