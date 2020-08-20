package com.example.sougame.wxapi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sougame.app.util.WechatHelper
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import android.widget.Toast
import com.blankj.utilcode.util.LogUtils
import com.example.common.ext.getAppViewModel
import com.example.sougame.app.event.EventViewModel
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelmsg.SendAuth

/**
 *   Created by YX on 2020-07-22 18:12.
 */
class WXEntryActivity : AppCompatActivity(), IWXAPIEventHandler {

    //Application全局的ViewModel，用于发送全局通知操作
    val eventViewModel: EventViewModel by lazy { getAppViewModel<EventViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WechatHelper.get().wx_api.handleIntent(intent, this)
    }

    override fun onResp(p0: BaseResp) {
        when (p0.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                // 成功
                val type = p0.type

//                if (mWXHandler != null) {
//                    mWXHandler.onSuccess(type)
//                }

                if (type == ConstantsAPI.COMMAND_SENDAUTH) {
                    // 授权
                    eventViewModel.wxCode.value =  (p0 as SendAuth.Resp).code
                }

                if (type == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
                    // 分享
                    Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show()

                }
            }
            BaseResp.ErrCode.ERR_USER_CANCEL -> {
            }   // 用户取消
            BaseResp.ErrCode.ERR_AUTH_DENIED -> {
            }  //发送被拒绝
        }
        finish()
    }

    override fun onReq(p0: BaseReq?) {
        LogUtils.e(p0)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        WechatHelper.get().wx_api.handleIntent(intent, this)
        finish()
    }

}