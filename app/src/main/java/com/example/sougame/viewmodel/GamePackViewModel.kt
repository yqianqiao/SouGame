package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.GamePackBean
import com.example.sougame.data.model.bean.GiftBagBean
import com.example.sougame.data.repository.request.HttpRequestManger
import com.example.sougame.ui.adapter.GamePackAdapter.Companion.TYPE_PACK
import com.example.sougame.ui.adapter.GamePackAdapter.Companion.TYPE_TITLE

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/6 17:19
 * Description: com.example.sougame.viewmodel
 */
class GamePackViewModel(application: Application) : BaseViewModel(application) {

    val gamePackList = MutableLiveData<ResultState<ArrayList<GamePackBean>>>()
    val getGiftBag = MutableLiveData<ResultState<GiftBagBean>>()

    fun getGamePackList(vip: Int) {
        request({ HttpRequestManger.instance.getGamePackList(vip) }, gamePackList)
    }

    fun giftbag(giftbagtypeid: Int) {
        request({ HttpRequestManger.instance.giftbag(giftbagtypeid) }, getGiftBag)
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
                        itemType = TYPE_TITLE
                    )
                )
                position = newList.size
                oldGameId = bean.gameid
            }
            bean.itemType = TYPE_PACK
            if (bean.type == 2) {
                newList.add(position, bean)
            } else
                newList.add(bean)
        }
        return newList
    }

}