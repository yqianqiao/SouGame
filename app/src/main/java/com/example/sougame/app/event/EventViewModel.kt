package com.example.sougame.app.event

import android.app.Application
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.callback.livedata.UnPeekLiveData
import com.example.common.callback.livedata.UnPeekNotNullLiveData
import com.example.sougame.data.model.bean.ProgressBean
import com.example.sougame.data.model.bean.UserInfo

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/11 14:58
 * Description: APP全局的Viewmodel，可以在这里发送全局通知替代Eventbus，LiveDataBus等
 */
class EventViewModel (app: Application) : BaseViewModel(app){

    var inviteData =
        UnPeekLiveData<ProgressBean>()


    //WebView

    var isRefresh =
        UnPeekNotNullLiveData<Int>()

    var isBroadside =
        UnPeekNotNullLiveData<Boolean>()


    var loadGame = UnPeekNotNullLiveData<Int>()

    var wxCode = UnPeekNotNullLiveData<String>()
    var qqCode = UnPeekNotNullLiveData<String>()


}