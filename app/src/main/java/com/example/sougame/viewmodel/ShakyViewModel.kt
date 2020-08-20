package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.ShakyBean
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/19 0:05
 * Description: com.example.sougame.viewmodel
 */
class ShakyViewModel(application: Application) : BaseViewModel(application) {
    val shakyData = MutableLiveData<ResultState<ArrayList<ShakyBean>>>()


    fun getShaky(informationtypeid: Int) {
        request({ HttpRequestManger.instance.getShakyData(informationtypeid) }, shakyData)
    }
}