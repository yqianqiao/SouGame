package com.example.sougame.data.model.bean

import kotlinx.android.parcel.Parcelize

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/19 10:52
 * Description: com.example.sougame.data.model.bean
 */
data class HomeBean(
    val handpick: ArrayList<GameListBean>,
    val hot:ArrayList<GameListBean>,
    val recommend: ArrayList<GameListBean>
)


