package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.GameListBean
import com.example.sougame.data.model.bean.GamePackBean
import com.example.sougame.data.model.bean.IntegralRecordBean
import com.example.sougame.data.model.bean.RechargeRecordBean
import com.example.sougame.data.repository.request.HttpRequestManger
import com.example.sougame.ui.adapter.GamePackAdapter

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/30 15:53
 * Description: com.example.sougame.viewmodel
 */
class ListViewModel(application: Application) : BaseViewModel(application) {

    val recentData = MutableLiveData<ResultState<ArrayList<GameListBean>>>()

    val rechargeRecord = MutableLiveData<ResultState<ArrayList<RechargeRecordBean>>>()

    val integralRecord = MutableLiveData<ResultState<ArrayList<IntegralRecordBean>>>()

    fun getRecentData() {
        request({ HttpRequestManger.instance.getRecentData() }, recentData)
    }

    fun getRechargeRecord() {
        request({ HttpRequestManger.instance.getRechargeRecord() }, rechargeRecord)
    }

    fun getIntegralRecord() {
        request({ HttpRequestManger.instance.getIntegralRecord() }, integralRecord)
    }


    val giftBagHistoryList = MutableLiveData<ResultState<ArrayList<GamePackBean>>>()

    fun getGiftBagHistory() {
        request({ HttpRequestManger.instance.getGiftBagHistory() }, giftBagHistoryList)
    }


    fun amendData(list: ArrayList<GamePackBean>): MutableList<GamePackBean> {
        val newList = mutableListOf<GamePackBean>()
        var oldGameId = 0

        var position = 0
        for (bean in list) {
            if (oldGameId != bean.gameid) {
                newList.add(
                    GamePackBean(
                        icon = bean.icon,
                        subject = bean.subject,
                        gametypename = bean.gametypename,
                        playnum = bean.playnum,
                        gameid = bean.gameid,
                        itemType = GamePackAdapter.TYPE_TITLE
                    )
                )
                position = newList.size
                oldGameId = bean.gameid
            }
            bean.itemType = GamePackAdapter.TYPE_PACK
            if (bean.type == 2) {
                newList.add(position, bean)
            } else
                newList.add(bean)
        }
        return newList
    }


}