package com.example.sougame.app

import com.example.common.base.BaseApp
import com.tencent.mmkv.MMKV
import com.umeng.commonsdk.UMConfigure

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/11 14:56
 * Description: com.example.sougame.app
 */
class App :BaseApp() {
    companion object{
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        UMConfigure.setLogEnabled(false)

        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")
        UMConfigure.init(this,"5d47db204ca357d6b5000220"
            ,"umeng", UMConfigure.DEVICE_TYPE_PHONE,"")


    }
}