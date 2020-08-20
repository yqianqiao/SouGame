package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.GameListBean
import com.example.sougame.data.model.bean.GameType
import com.example.sougame.data.model.bean.HomeBean
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/19 0:00
 * Description: com.example.sougame.viewmodel
 */
class HotViewModel(application: Application) : BaseViewModel(application) {

    val homeData: MutableLiveData<ResultState<HomeBean>> = MutableLiveData()

    val gameTypeData: MutableLiveData<ResultState<ArrayList<GameType>>> = MutableLiveData()

    val gameData: MutableLiveData<ResultState<ArrayList<GameListBean>>> = MutableLiveData()

    val recentData = MutableLiveData<ResultState<ArrayList<GameListBean>>>()


    fun getHomeData(wap: Int) {
        request({ HttpRequestManger.instance.getHomeData(wap) }, homeData)
    }

    fun getGameTypeData() {
        request({ HttpRequestManger.instance.getGameTypeData() }, gameTypeData)
    }

    fun getRecentData() {
        request({ HttpRequestManger.instance.getRecentData() }, recentData)
    }

    fun getGameData(r: String, n: Int, offset: Int, gametypeid: Int?) {
        request({
            HttpRequestManger.instance.getGameListData(
                r = r,
                n = n,
                offset = offset,
                gametypeid = gametypeid
            )
        }, gameData)
    }
}