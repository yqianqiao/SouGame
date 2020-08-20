package com.example.sougame

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.navigation.Navigation
import com.blankj.utilcode.util.LogUtils
import com.example.sougame.app.base.BaseActivity
import com.example.sougame.databinding.ActivityMainBinding
import com.example.sougame.viewmodel.state.MainViewModel
import com.permissionx.guolindev.PermissionX
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private lateinit var mTencent: Tencent

    override fun layoutId() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
//                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
                } else {
//                    Toast.makeText(
//                        this,
//                        "These permissions are denied: $deniedList",
//                        Toast.LENGTH_LONG
//                    ).show()
                }
            }

        mTencent = Tencent.createInstance("101521879", application)

//        WXEntryActivity.wx_api = WXAPIFactory.createWXAPI(this, "wx4df863a2c1e83fc0", false)
//        WXEntryActivity.wx_api.registerApp("wx4df863a2c1e83fc0")
//
//        but.setOnClickListener {
////                        mTencent.login(this, "all", baseUiListener)
//            val req = SendAuth.Req()
//            req.scope = "snsapi_userinfo"
//            req.state = "wechat_sdk_demo_test"
//            val b = WXEntryActivity.wx_api.sendReq(req)
//
//            LogUtils.e("onComplete  $b")
//        }
    }

    val baseUiListener = object : IUiListener {
        override fun onComplete(p0: Any?) {
            LogUtils.e("onComplete")
        }

        override fun onCancel() {
            LogUtils.e("aaa")
        }

        override fun onError(p0: UiError?) {
            LogUtils.e(p0)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.main_navigation).navigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 11101)
            Tencent.onActivityResultData(requestCode, resultCode, data, baseUiListener)
        super.onActivityResult(requestCode, resultCode, data)
    }
}