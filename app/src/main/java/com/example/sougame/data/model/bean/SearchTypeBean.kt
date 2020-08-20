package com.example.sougame.data.model.bean

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableBoolean

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/15 14:58
 * Description: com.example.sougame.data.model.bean
 */
class SearchTypeBean : BaseObservable() {
     val isChecked = ObservableBoolean(true)
     //筛选
     val isScreen = ObservableBoolean(false)
}