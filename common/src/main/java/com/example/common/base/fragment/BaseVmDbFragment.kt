package com.example.common.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.getVmClazz
import com.example.common.network.manager.NetState
import com.example.common.network.manager.NetworkStateManager

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/10 21:08
 * Description: com.example.common.base.fragment
 */
abstract class BaseVmDbFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    //是否第一次加载
    private var isFirst: Boolean = true
    //该类负责绑定视图数据的Viewmodel
    lateinit var mViewModel: VM
    //该类绑定的ViewDataBinding
    lateinit var mDataBind: DB

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract fun layoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataBind = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        mDataBind.lifecycleOwner = this
        return mDataBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = createViewModel()
        initView(savedInstanceState)
        onVisible()
        registerDefUIChange()
        initData()
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(this.requireActivity().application)
        ).get(getVmClazz(this))
    }

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()

    /**
     * 创建观察者
     */
    abstract fun createObserver()


    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            isFirst = false
            createObserver()
            NetworkStateManager.instance.mNetworkStateCallback.observe(this, Observer {
                onNetworkStateChanged(it)
            })
        }
    }

    /**
     * Fragment执行onCreate后触发的方法
     */
    open fun initData() {}

    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()


    /**
     * 注册 UI 事件
     */
    private fun registerDefUIChange() {
        mViewModel.loadingChange.showDialog.observe(viewLifecycleOwner, Observer {
            showLoading()
        })
        mViewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner, Observer {
            dismissLoading()
        })
    }
}