package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.EntityGoodsBean
import com.example.sougame.data.model.bean.GameDetailsBean
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/9 15:11
 * Description: com.example.sougame.viewmodel
 */
class EntityGoodsViewModer(application: Application) : BaseViewModel(application) {
    val entityGoodsData = MutableLiveData<ResultState<ArrayList<EntityGoodsBean>>>()


    fun getEntityGoods() {
        request({ HttpRequestManger.instance.getEntityGoods() }, entityGoodsData)
    }
}