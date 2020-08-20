package com.example.sougame.app.weight

import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.common.ext.getViewModel
import com.example.common.ext.getVmClazz
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.App
import com.example.sougame.app.base.BaseActivity
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.event.AppViewModel
import com.example.sougame.app.util.CacheUtil
import com.example.sougame.data.repository.request.HttpRequestManger
import com.example.sougame.viewmodel.LoginViewModel
import com.lxj.xpopup.core.CenterPopupView
import kotlinx.android.synthetic.main.dialog_login.view.*

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/24 15:25
 * Description: com.example.sougame.app.weight
 */
class LoginDialog(context: Context) : CenterPopupView(context), View.OnClickListener {
    private val activity = context as BaseActivity<*, *>
    private val loginViewModel: LoginViewModel by lazy {
        getViewModel<LoginViewModel>()
    }

    private val baseViewModel: AppViewModel by lazy {
        App.instance.getAppViewModelProvider().get(AppViewModel::class.java)
    }


    override fun getImplLayoutId() = R.layout.dialog_login

    override fun onCreate() {
        super.onCreate()
        tv_login.setOnClickListener(this)
        tv_phone_login.setOnClickListener(this)
        createObserver()
    }

    private fun createObserver() {
        loginViewModel.loginResult.observe(activity, Observer {
            activity.parseState(it, { bean ->
                baseViewModel.isLogin.postValue(true)
                baseViewModel.token.postValue(bean.token)
                baseViewModel.uid.postValue(bean.uid)
            })
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_close -> dismiss()
            R.id.tv_login -> {
                login()
            }
            R.id.tv_register -> {

            }
            R.id.tv_forget_password -> {

            }
            R.id.tv_wx_login -> {

            }
            R.id.tv_qq_login -> {

            }
            R.id.tv_phone_login -> {

                tv_phone_login.text = "账户登录"
                tv_phone_login.setCompoundDrawables(
                    null,
                    ContextCompat.getDrawable(context, R.drawable.ic_account),
                    null,
                    null
                )
            }

        }
    }

    private fun login() {
        when {
            et_input_account.text.trim().isEmpty() -> {
                ToastUtils.showShort("请输入账号")
            }
            et_input_password.text.trim().isEmpty() -> {
                ToastUtils.showShort("请输入密码")
            }
            else -> {
                loginViewModel.loginReq(
                    et_input_account.text.toString(),
                    et_input_password.text.toString(),
                    "common"
                )
    //            ToastUtils.showShort("正在登录")
            }
        }
    }

}