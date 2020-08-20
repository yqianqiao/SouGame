package com.example.sougame.ui.fragment.login

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.common.ext.goFragment
import com.example.common.ext.nav
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.event.AppViewModel
import com.example.sougame.app.ext.bindObserve
import com.example.sougame.app.ext.getRandomCode
import com.example.sougame.app.ext.loadImage
import com.example.sougame.app.ext.showCustomView
import com.example.sougame.app.util.CacheUtil
import com.example.sougame.app.util.WechatHelper
import com.example.sougame.databinding.FragmentLoginBinding
import com.example.sougame.viewmodel.LoginViewModel
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.*
import kotlin.concurrent.thread


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    // 0，账号登录，1，注册，2，手机登录
    private var loginType = 0
    private var type = 0
    private var gameid = 0
    private var account = ""
    private var password = ""

    private var job: Job? = null

    override fun layoutId() = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            type = it.getInt("type")
            it.getString("img")?.let {url->
                iv_img.loadImage(url)
            }
            gameid = it.getInt("gameid")
            iv_img.visibility = View.VISIBLE

            tv_fast.visibility = View.VISIBLE

        } ?: initWindowBackGround()
        mDataBind.click = ProxyClick()

    }

    private fun initWindowBackGround() {

        val dm = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(dm)
        requireActivity().window.setLayout(
            dm.widthPixels,
            requireActivity().window.attributes.height
        )
        requireActivity().window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun lazyLoadData() {

    }


    override fun createObserver() {
        mViewModel.run {

            bindObserve(loginResult) {
                shareViewModel.isLogin.postValue(true)
                shareViewModel.token.postValue(it.token)
                shareViewModel.uid.postValue(it.uid)
                if (type == 1) {
                    goFragment(R.id.action_loginFragment_to_webFragment, Bundle().apply {
                        putInt("gameid", gameid)
                    })

                } else
                    nav().navigateUp()
            }

            bindObserve(registerResult) {
                mViewModel.loginReq(account, password, "common")
            }

            bindObserve(phoneLoginResult) {
                var time = 60
//                thread {
//                    while (time != 0) {
//                        Thread.sleep(1000)
//                        time--
//                        activity?.runOnUiThread {
//                            tv_get_code.text = "${time}s"
//                        }
//                    }
//                    activity?.runOnUiThread {
//                        tv_get_code.text = "重新发送"
//                    }
//                }
                job = GlobalScope.launch {
                    while (time != 0) {
                        doSomething()
                        time--
                        withContext(Dispatchers.Main) {
                            tv_get_code.text = "${time}s"
                        }
                    }

                    withContext(Dispatchers.Main) {
                        tv_get_code.text = "重新发送"
                    }
                }
            }


            eventViewModel.wxCode.observe(viewLifecycleOwner, Observer {
                if (it.isNotEmpty()) {
                    mViewModel.loginReq(
                        type = "qq",
                        qqopenid = password,
                        qqaccesstoken = account
                    )
                }
            })

        }

        WechatHelper.get().qqCode = object : WechatHelper.QqCode {
            override fun getQqCode(qqopenid: String, qqaccesstoken: String) {
                mViewModel.loginReq(
                    type = "qq",
                    qqopenid = qqopenid,
                    qqaccesstoken = qqaccesstoken
                )
            }
        }
    }

    private fun doSomething() {
        Thread.sleep(1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        requireActivity().window.setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }

    fun showBindPhone() {
        showCustomView(R.layout.dialog_bind_phone) { dialog, view ->
            var time = 60
            val code = view.findViewById<TextView>(R.id.tv_code)
            val phone = view.findViewById<EditText>(R.id.et_phone)
            code.setOnClickListener {

                mViewModel.bindPhoneCode(phone.text.toString())
            }

            view.findViewById<TextView>(R.id.tv_submit).setOnClickListener {
                mViewModel.bindPhone(
                    phone.text.toString(),
                    view.findViewById<EditText>(R.id.et_code).text.toString()
                )
                dialog.dismiss()
            }

            view.findViewById<TextView>(R.id.tv_close).setOnClickListener {
                dialog.dismiss()
            }

            mViewModel.run {
                bindObserve(bindPhoneCodeResult) {
                    ToastUtils.showShort("发送成功")
                    job = GlobalScope.launch {
                        while (time != 0) {
                            Thread.sleep(1000)
                            time--
                            withContext(Dispatchers.Main) {
                                code.text = "${time}s"
                            }
                        }
                        withContext(Dispatchers.Main) {
                            code.text = "重新发送"
                        }
                    }
                }

            }

        }


    }


    inner class ProxyClick {
        fun close() {
            nav().navigateUp()
        }

        fun register() {
            loginType = 1
            tv_text.text = "搜游记注册"
            tv_login.text = "注册"
            et_confirm_password.visibility = View.VISIBLE
            tv_register.visibility = View.GONE
            tv_forget_password.visibility = View.GONE
        }

        fun forgetPassword() {

        }

        fun getCode() {
            account = et_input_account.text.toString().trim()
            if (tv_get_code.text.toString().contains("s")) {
                ToastUtils.showShort("请勿重复获取")
                return
            }
            if (loginType == 2) {
                mViewModel.phoneLogin(account)
            }
        }

        fun login() {
            account = et_input_account.text.toString().trim()
            password = et_input_password.text.toString().trim()
            when {
                account.isEmpty() -> {
                    ToastUtils.showShort("请输入账号")
                }
                password.isEmpty() -> {
                    ToastUtils.showShort("请输入密码")
                }
                else -> {
                    when (loginType) {
                        0 -> mViewModel.loginReq(
                            account,
                            password,
                            "common"
                        )
                        1 -> {
                            if (password != et_confirm_password.text.toString().trim()) {
                                ToastUtils.showShort("密码不一致")
                                return
                            }
                            mViewModel.registerReq(
                                account,
                                password,
                                "fast"
                            )
                        }
                        2 -> {
                            mViewModel.loginReq(
                                type = "phone",
                                code = password,
                                phone = account
                            )
                        }
                    }

                }
            }
        }

        fun fastLogin() {
            showCustomView(R.layout.dialog_tourist_login) { dialog, view ->
                val code = getRandomCode()
                view.findViewById<TextView>(R.id.tv_account).text = "您的账号：$code"
                view.findViewById<TextView>(R.id.tv_pass).text = "您的密码：$code"

                account = code
                password = code
                view.findViewById<TextView>(R.id.tv_toGame).setOnClickListener {
                    mViewModel.registerReq(
                        account,
                        password,
                        "fast"
                    )

                    dialog.dismiss()
                }

                view.findViewById<TextView>(R.id.tv_upData).setOnClickListener {
                    showBindPhone()
                }

            }
        }

        fun wxLogin() {
            WechatHelper.get().sendWxLogin()
        }

        fun qqLogin() {
            WechatHelper.get().sendQQLogin(activity!!) {

            }
        }

        fun phoneLogin() {
            et_confirm_password.visibility = View.GONE
            if (loginType != 2) {
                tv_text.text = "搜游记登录"
                tv_login.text = "登录"
                tv_phone_login.text = "账号登录"
                et_input_account.hint = "输入手机号码"
                et_input_password.hint = "输入验证码"
                tv_get_code.visibility = View.VISIBLE
                tv_register.visibility = View.GONE
                tv_forget_password.visibility = View.GONE
                loginType = 2
            } else {
                tv_phone_login.text = "手机登录"
                et_input_account.hint = "输入账号"
                et_input_password.hint = "输入密码"
                tv_get_code.visibility = View.GONE
                tv_register.visibility = View.VISIBLE
                tv_forget_password.visibility = View.VISIBLE
                loginType = 0
            }
        }

    }


}