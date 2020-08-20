package com.example.sougame.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.common.base.fragment.BaseVmDbFragment
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.getAppViewModel
import com.example.common.ext.getVmClazz
import com.example.common.network.manager.NetState
import com.example.common.network.manager.NetworkStateManager
import com.example.sougame.R
import com.example.sougame.app.event.AppViewModel
import com.example.sougame.app.event.EventViewModel
import com.example.sougame.app.ext.hideSoftKeyboard

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/11 15:02
 * Description: com.example.sougame.app.base
 */
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbFragment<VM, DB>() {
    private var dialog: MaterialDialog? = null

    //Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
    val shareViewModel: AppViewModel by lazy { getAppViewModel<AppViewModel>() }
    //Application全局的ViewModel，用于发送全局通知操作
    val eventViewModel: EventViewModel by lazy { getAppViewModel<EventViewModel>()}

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract override fun layoutId(): Int


    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载 只有当前fragment视图显示时才会触发该方法
     */
    abstract override fun lazyLoadData()

    /**
     * 创建LiveData观察者 懒加载之后才会触发
     */
    override fun createObserver() {}


    /**
     * Fragment执行onViewCreated后触发的方法
     */
    override fun initData() {

    }

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        if (dialog == null) {
            dialog = activity?.let {
                MaterialDialog(it)
                    .cancelable(true)
                    .cancelOnTouchOutside(false)
                    .cornerRadius(8f)
                    .customView(R.layout.layout_custom_progress_dialog_view)
                    .lifecycleOwner(this)
            }
            dialog?.getCustomView()?.run {
                this.findViewById<TextView>(R.id.loading_tips).text = message

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

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard(activity)
    }
}