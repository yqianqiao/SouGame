package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.BannerBean
import com.example.sougame.data.model.bean.GameListBean
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/12 11:42
 * Description: com.example.sougame.viewmodel
 */
class HomeViewModel(application: Application) : BaseViewModel(application) {

    var bannerData: MutableLiveData<ResultState<BannerBean>> = MutableLiveData()

    fun getBannerData() {
        request({ HttpRequestManger.instance.getBannerData() }, bannerData)
    }
}