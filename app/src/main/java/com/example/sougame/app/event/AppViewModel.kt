package com.example.sougame.app.event

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.callback.livedata.StringLiveData
import com.example.common.callback.livedata.UnPeekLiveData
import com.example.common.callback.livedata.UnPeekNotNullLiveData
import com.example.sougame.app.util.CacheUtil
import com.example.sougame.data.model.bean.UserInfo

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/11 14:51
 * Description: com.example.sougame.app.event
 */
class AppViewModel(app: Application) : BaseViewModel(app) {

    //是否是第一次启动
    var isFirst =
        UnPeekNotNullLiveData<Boolean>()

    //是否已经登录过
    var isLogin =
        UnPeekNotNullLiveData<Boolean>()

    //App的账户信息 使用UnPeekLiveData 因为账户信息有可能为空
    var userInfo =
        UnPeekLiveData<UserInfo>()

    var uid = MutableLiveData<String?>()
    var token = MutableLiveData<String?>()

    var userList = CacheUtil.getUserList()


    init {
        isFirst.value = CacheUtil.isFirst()
        isLogin.value = CacheUtil.isLogin()
        userInfo.value = CacheUtil.getUser()
        uid.value = CacheUtil.getUid()
        token.value = CacheUtil.getToken()
    }

    fun getUserNameList(): ArrayList<String> {
        val userName = arrayListOf<String>()
        userList.forEach { userName.add(it.username) }
        return userName
    }


}