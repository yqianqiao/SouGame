package com.example.sougame.app.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.LogUtils
import com.example.sougame.app.App
import com.example.sougame.app.event.EventViewModel
import com.example.sougame.data.model.bean.QqCodeBean
import com.google.gson.Gson
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError

/**
 *   Created by YX on 2020-07-28 16:39.
 */
class WechatHelper {

    companion object {
        const val WX_APP_ID: String = "wx4df863a2c1e83fc0"
        const val QQ_APP_ID: String = "101521879"
        private var instance: WechatHelper? = null
            get() {
                if (field == null) {
                    field = WechatHelper()
                }
                return field
            }

        fun get(): WechatHelper {
            return instance!!
        }
    }

    lateinit var wx_api: IWXAPI
    private lateinit var mTencent: Tencent
    var code = ""

    fun initWxApi(context: Context) {
        wx_api = WXAPIFactory.createWXAPI(context, WX_APP_ID, true)
        wx_api.registerApp(WX_APP_ID)

        mTencent = Tencent.createInstance(QQ_APP_ID, App.instance)
    }


    fun sendWxLogin() {
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "wechat_sdk_demo_test"
        wx_api.sendReq(req)
    }

    fun sendReq(req: BaseReq) {
        wx_api.sendReq(req)
    }


    fun sendQQLogin(activity: Activity, action: () -> Unit) {
        mTencent.login(activity, "all", baseUiListener)
    }

    fun shareToQQ(activity: Activity, params: Bundle) {
        mTencent.shareToQQ(activity, params, baseUiListener)
    }

    fun shareToQzone(activity: Activity, params: Bundle) {
        mTencent.shareToQzone(activity, params, baseUiListener)
    }


    val baseUiListener = object : IUiListener {
        override fun onComplete(p0: Any?) {
            val bean = Gson().fromJson(p0.toString(), QqCodeBean::class.java)
            qqCode?.getQqCode(bean.openid,bean.access_token)
            LogUtils.e("onComplete $p0")

        }

        override fun onCancel() {
            LogUtils.e("aaa")
        }

        override fun onError(p0: UiError?) {
            LogUtils.e(p0)
        }
    }

     var qqCode: QqCode? = null

    interface QqCode {
        fun getQqCode(qqopenid : String,qqaccesstoken  : String)
    }

}