package com.example.sougame.app.base

import android.os.Bundle
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.common.base.activity.BaseVmDbActivity
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.getAppViewModel
import com.example.sougame.R
import com.example.sougame.app.event.AppViewModel
import com.example.sougame.app.event.EventViewModel
import com.example.sougame.app.util.CacheUtil

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/10 23:33
 * Description: com.example.sougame.app.base
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbActivity<VM, DB>() {
    private var dialog: MaterialDialog? = null

    //Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
    val shareViewModel: AppViewModel by lazy { getAppViewModel<AppViewModel>() }

    //Application全局的ViewModel，用于发送全局通知操作
    val eventViewModel: EventViewModel by lazy { getAppViewModel<EventViewModel>() }

    abstract override fun layoutId(): Int

    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 创建liveData观察者
     */
    override fun createObserver() {
        shareViewModel.run {
            isLogin.observe(this@BaseActivity, Observer {
                CacheUtil.setIsLogin(it)
            })
            uid.observe(this@BaseActivity, Observer {
                it?.let {
                    CacheUtil.setUid(it)
                }

            })
            token.observe(this@BaseActivity, Observer {
                it?.let {
                    CacheUtil.setToken(it)
                }
            })
            userInfo.observe(this@BaseActivity, Observer {
                CacheUtil.setUser(it)
            })


        }
    }

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        if (dialog == null) {
            dialog = this.let {
                MaterialDialog(it)
                    .cancelable(true)
                    .cancelOnTouchOutside(false)
                    .customView(R.layout.layout_custom_progress_dialog_view)
                    .lifecycleOwner(this)
            }
            dialog?.getCustomView()?.run {
                this.findViewById<TextView>(R.id.loading_tips).text = message
//                shareViewModel.appColor.value.let {
//                    this.findViewById<ProgressBar>(R.id.progressBar).indeterminateTintList =
//                        SettingUtil.getOneColorStateList(it)
//                }
            }
        }
        dialog?.show()
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dialog?.dismiss()
    }

//    /**
//     * 在任何情况下本来适配正常的布局突然出现适配失效，适配异常等问题，只要重写 Activity 的 getResources() 方法
//     */
//    override fun getResources(): Resources {
//        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
//        return super.getResources()
//    }
}