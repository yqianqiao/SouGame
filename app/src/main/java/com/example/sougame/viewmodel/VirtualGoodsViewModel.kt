package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.GameListBean
import com.example.sougame.data.model.bean.GamePackBean
import com.example.sougame.data.model.bean.GiftBagBean
import com.example.sougame.data.model.bean.LetterBean
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/8 10:55
 * Description: com.example.sougame.viewmodel
 */
class VirtualGoodsViewModel(application: Application) : BaseViewModel(application) {
    val recentData = MutableLiveData<ResultState<ArrayList<GameListBean>>>()
    private val letterList = arrayListOf("全部", "ABC", "DEFG", "HIJKL", "MNOP", "QRST", "UVWX", "YZ")

    val virtualGiftList = MutableLiveData<ResultState<ArrayList<GamePackBean>>>()

    val gamePackList = MutableLiveData<ResultState<ArrayList<GamePackBean>>>()
    val virtualGiftbagData = MutableLiveData<ResultState<Any>>()

    val getGiftBag = MutableLiveData<ResultState<GiftBagBean>>()


    fun getRecentData() {
        request({ HttpRequestManger.instance.getRecentData() }, recentData)
    }

    fun getLetterData(): MutableList<LetterBean> {
        val dataList = mutableListOf<LetterBean>()
        for (i in letterList) {
            dataList.add(LetterBean(i, false))
        }
        dataList[0].isClickable = true
        return dataList
    }

    fun getGamePackList(vip: Int? = 0, type: Int) {
        request({ HttpRequestManger.instance.getGamePackList(vip, type) }, gamePackList)
    }


    fun getVirtualGift() {
        request({ HttpRequestManger.instance.getVirtualGift() }, virtualGiftList)
    }


    fun virtualGiftbag(id:Int){
        request({ HttpRequestManger.instance.virtualGiftbag(id) }, virtualGiftbagData)
    }

    fun giftbag(giftbagtypeid: Int) {
        request({ HttpRequestManger.instance.giftbag(giftbagtypeid) }, getGiftBag)
    }


    fun amendData(list: MutableList<GamePackBean>): MutableList<GamePackBean> {
        var oldGameId = 0
        list.forEach {
            if (oldGameId == it.gameid) {
                it.subject = null
            } else
                oldGameId = it.gameid
        }
        return list
    }


}