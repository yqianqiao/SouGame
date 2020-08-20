package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.GameType
import com.example.sougame.data.model.bean.InviteFriendsBean
import com.example.sougame.data.model.bean.NumBean
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/2 16:36
 * Description: com.example.sougame.viewmodel
 */
class InviteFriendsViewModel(application: Application) : BaseViewModel(application) {
    private val numList = intArrayOf(1, 5, 10, 50, 100, 500)

    val inviteNumData = MutableLiveData<ResultState<ArrayList<InviteFriendsBean>>>()


    fun getInviteNum() {
        request({ HttpRequestManger.instance.getInviteNum() }, inviteNumData)
    }



    fun getNumData(): MutableList<NumBean> {
        val dataList = mutableListOf<NumBean>()
        for (i in numList) {
            dataList.add(NumBean(i, false))
        }
        dataList[0].isClickable = true
        return dataList
    }
}