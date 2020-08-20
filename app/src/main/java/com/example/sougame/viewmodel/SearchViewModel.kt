package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.GameListBean
import com.example.sougame.data.model.bean.GameType
import com.example.sougame.data.repository.request.HttpRequestManger

class SearchViewModel(application: Application) : BaseViewModel(application) {

    var searchData: MutableLiveData<ResultState<ArrayList<GameListBean>>> = MutableLiveData()

    var searchGameData: MutableLiveData<ResultState<ArrayList<GameListBean>>> = MutableLiveData()

    val gameTypeData = MutableLiveData<ResultState<ArrayList<GameType>>>()

    //筛选
    val gameData = MutableLiveData<ResultState<ArrayList<GameListBean>>>()

    val letterList = arrayListOf("ABC", "DEFG", "HIJKL", "MNOP", "QRST", "UVWX", "YZ")

    /**
     * 获取不限/最新 游戏
     */
    fun getSearch(r: String) {
        request({ HttpRequestManger.instance.getGameListData(r = r) }, searchData)
    }

    fun getSearchGame(v: String) {
        request({ HttpRequestManger.instance.getGameListData(v = v) }, searchGameData)
    }

    fun getGameTypeData() {
        request({ HttpRequestManger.instance.getGameTypeData() }, gameTypeData)
    }


    fun getGameData(gametypeid: Int?) {
        request({
            HttpRequestManger.instance.getGameListData(gametypeid = gametypeid)
        }, gameData,true)
    }
}