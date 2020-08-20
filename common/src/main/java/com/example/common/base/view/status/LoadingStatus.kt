package com.example.common.base.view.status

import android.content.Context
import android.view.View
import com.example.common.R
import com.kingja.loadsir.callback.Callback

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/5/13 10:57
 * Description: com.example.lib_common.mvvm.view.status
 */
class LoadingStatus :Callback() {
    override fun onCreateView(): Int {
        return R.layout.common_layout_loading
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        //不响应reload事件
        return true
    }

    override fun getSuccessVisible(): Boolean {
        //背景可见
        return true
    }
}