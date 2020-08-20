package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.GameListBean
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/19 0:02
 * Description: com.example.sougame.viewmodel
 */
class NewGameViewModel(application: Application) : BaseViewModel(application) {
    val newGameData: MutableLiveData<ResultState<ArrayList<GameListBean>>> = MutableLiveData()


    fun getNewGameData(n:Int) {
        request({ HttpRequestManger.instance.getGameListData(n = n) }, newGameData)
    }

}