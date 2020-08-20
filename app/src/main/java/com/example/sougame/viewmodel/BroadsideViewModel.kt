package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.*
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 *   Created by YX on 2020-07-16 17:01.
 */
class BroadsideViewModel(application: Application) : BaseViewModel(application) {
    var signResult = MutableLiveData<ResultState<SignBean>>()

    val newGameData = MutableLiveData<ResultState<ArrayList<BroadsideGameBean>>>()

    val shakyData = MutableLiveData<ResultState<ArrayList<ShakyBean>>>()

    val gameGiftBagData = MutableLiveData<ResultState<ArrayList<GameGiftBag>>>()

    var bindPhoneCodeResult = MutableLiveData<ResultState<Any>>()
    var bindPhoneResult = MutableLiveData<ResultState<Any>>()

    fun sign() {
        request({
            HttpRequestManger.instance.sign()
        }, signResult)
    }

    fun bindPhoneCode(phone: String) {
        request({
            HttpRequestManger.instance.bindPhoneCode(phone)
        }, bindPhoneCodeResult)
    }

    fun bindPhone(phone: String, code: String) {
        request({
            HttpRequestManger.instance.bindPhone(phone, code)
        }, bindPhoneResult)
    }

    //更多游戏
    fun getGameList(r: String) {
        request({ HttpRequestManger.instance.getBroadsideGameListData(r = r, n = 10) }, newGameData)
    }

    /**
     * 资讯
     */
    fun getShakyData(informationtypeid: Int, gameid: Int?) {
        request(
            { HttpRequestManger.instance.getShakyData(informationtypeid, gameid, 4) },
            shakyData
        )
    }

    //礼包
    fun gameGiftBagData(gameId: Int, vip: Int) {
        request({ HttpRequestManger.instance.gameGiftBagData(gameId, vip) }, gameGiftBagData)
    }

    val entityGoodsData = MutableLiveData<ResultState<ArrayList<EntityGoodsBean>>>()

    /**
     * 实物商品
     */
    fun getEntityGoods() {
        request({ HttpRequestManger.instance.getEntityGoods() }, entityGoodsData)
    }


    fun machiningGameList(list: ArrayList<BroadsideGameBean>): ArrayList<BroadsideGameBean> {
        for (i in list.indices) {
            if (i == 0) {
                list.add(0, BroadsideGameBean(title = "必玩游戏"))
            } else if (i == 4) {
                list.add(4, BroadsideGameBean(title = "热门游戏"))
            }
        }
        return list
    }


    fun amendData(list: ArrayList<GameGiftBag>): MutableList<GameGiftBag> {

        val newList = mutableListOf<GameGiftBag>()

        var vipTitle = false
        var plainTitle = false
        list.forEach {
            if (it.type == 2) {
                newList.add(0, it)
                if (!vipTitle) {
                    newList.add(0, GameGiftBag(type = 2, title = "VIP礼包"))
                    vipTitle = true
                }


            } else {
                if (!plainTitle) {
                    newList.add(GameGiftBag(type = 1, title = "普通礼包"))
                    plainTitle = true
                }
                newList.add(it)

            }
        }

        return newList
    }
}