package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.GameDetailsBean
import com.example.sougame.data.model.bean.GameGiftBag
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/15 18:26
 * Description: com.example.sougame.viewmodel
 */
class WebViewModel(application: Application) :BaseViewModel(application) {

    val gameDetailsData = MutableLiveData<ResultState<GameDetailsBean>>()


    fun getGameDetailsData(gameId: Int) {
        request({ HttpRequestManger.instance.getGameDetailsData(gameId) }, gameDetailsData)
    }
}