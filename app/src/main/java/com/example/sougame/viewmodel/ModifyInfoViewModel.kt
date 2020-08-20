package com.example.sougame.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.request
import com.example.common.state.ResultState
import com.example.sougame.data.model.bean.GameListBean
import com.example.sougame.data.repository.request.HttpRequestManger

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/30 17:32
 * Description: com.example.sougame.viewmodel
 */
class ModifyInfoViewModel(application: Application) : BaseViewModel(application) {
    var certificationData = MutableLiveData<ResultState<ArrayList<String>>>()
    var resetPasswordData = MutableLiveData<ResultState<ArrayList<String>>>()

    fun certification(realname: String, card: String) {
        request({ HttpRequestManger.instance.certification(realname, card) }, certificationData)
    }
    fun getResetPassword(oldpassword: String,password: String){
        request({ HttpRequestManger.instance.getResetPassword(oldpassword, password) }, resetPasswordData)
    }
}