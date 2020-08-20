package com.example.sougame.app.ext

import android.app.Dialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.common.ext.goFragment
import com.example.common.ext.nav
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.event.AppViewModel
import com.example.sougame.app.util.CacheUtil
import com.example.sougame.app.util.WechatHelper
import com.example.sougame.data.model.bean.GamePackBean
import com.example.sougame.data.model.bean.GiftBagBean
import com.tencent.connect.share.QQShare
import com.tencent.connect.share.QzoneShare
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import kotlinx.android.synthetic.main.dialog_giftbag.*
import kotlinx.android.synthetic.main.dialog_switch_account.*

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/1 14:38
 * Description: com.example.sougame.app.ext
 */

/**
 * @param message 显示对话框的内容 必填项
 * @param title 显示对话框的标题 默认 温馨提示
 * @param positiveButtonText 确定按钮文字 默认确定
 * @param positiveAction 点击确定按钮触发的方法 默认空方法
 * @param negativeButtonText 取消按钮文字 默认空 不为空时显示该按钮
 * @param negativeAction 点击取消按钮触发的方法 默认空方法
 */
fun Fragment.showMessage(
    message: String,
    title: String = "温馨提示",
    positiveButtonText: String = "确定",
    positiveAction: () -> Unit = {},
    negativeButtonText: String = "",
    negativeAction: () -> Unit = {}
) {
    activity?.let {
        MaterialDialog(it)
            .cancelable(true)
            .lifecycleOwner(viewLifecycleOwner)
            .show {
                title(text = title)
                message(text = message)
                positiveButton(text = positiveButtonText) {
                    positiveAction.invoke()
                }
                if (negativeButtonText.isNotEmpty()) {
                    negativeButton(text = negativeButtonText) {
                        negativeAction.invoke()
                    }
                }
//                getActionButton(WhichButton.POSITIVE).updateTextColor(SettingUtil.getColor(it))
//                getActionButton(WhichButton.NEGATIVE).updateTextColor(SettingUtil.getColor(it))
            }
    }
}

fun Fragment.showShare(url: String) {
    val bottomDialog = Dialog(requireContext(), R.style.DialogTheme)
    val contentView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_share, null)
    contentView.findViewById<View>(R.id.tv_wechat).setOnClickListener {
        share(this,url, 0)
        bottomDialog.dismiss()
    }
    contentView.findViewById<View>(R.id.tv_moments).setOnClickListener {
        share(this,url, 1)
        bottomDialog.dismiss()
    }

    contentView.findViewById<View>(R.id.tv_qq).setOnClickListener {
        shareQQ(this,url, 0)
        bottomDialog.dismiss()
    }
    contentView.findViewById<View>(R.id.tv_qq_moments).setOnClickListener {
        shareQQ(this,url, 1)
        bottomDialog.dismiss()
    }

    contentView.findViewById<View>(R.id.tv_cancel).setOnClickListener { bottomDialog.dismiss() }

    bottomDialog.setContentView(contentView)
    bottomDialog.window!!.setGravity(Gravity.BOTTOM)
    bottomDialog.window!!.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    bottomDialog.show()
}

private fun shareQQ(fragment: Fragment, url: String, type: Int) {
    val urlEncoded = Uri.decode(url)

    val split = urlEncoded.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val params = Bundle()
    if (type == 0) {

        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
        params.putString(QQShare.SHARE_TO_QQ_TITLE, split[1])
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, split[2])
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, split[3])

        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, split[4])
        //        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
        WechatHelper.get().shareToQQ(fragment.activity!!, params)
//        mTencent.shareToQQ(this@DaBaoActivity, params, BaseUiListener)
    } else {
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT)
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, split[1])//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, split[2])//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, split[3])//必填
        val imgUrlList = java.util.ArrayList<String>()
        imgUrlList.add(split[3])
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList)// 图片地址
        //            params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        WechatHelper.get().shareToQzone(fragment.activity!!, params)
    }

}

private fun share(fragment: Fragment, url: String, type: Int) {
    val urlEncoded = Uri.decode(url)
    val split = urlEncoded.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    //初始化一个WXWebpageObject，填写url
    val webpage = WXWebpageObject()
    webpage.webpageUrl = split[3]

    //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
    val msg = WXMediaMessage(webpage)
    msg.title = split[1]
    msg.description = split[2]
    Glide.with(fragment).asBitmap().load(split[4]).into(object :
        SimpleTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            msg.thumbData = resource.compressImage
            val req = SendMessageToWX.Req()
            req.transaction = System.currentTimeMillis().toString()
            req.message = msg
            req.scene =
                if (type == 0) SendMessageToWX.Req.WXSceneSession else SendMessageToWX.Req.WXSceneTimeline
            WechatHelper.get().sendReq(req)
        }
    })
}


fun Fragment.showGift(bean: GiftBagBean, gameid: Int) {
    MaterialDialog(requireContext())
        .show {
            customView(R.layout.dialog_giftbag, noVerticalPadding = true)
            tv_code.text = bean.code
            tv_copy.setOnClickListener {
                ToastUtils.showShort("复制成功")
                copy(bean.code)

            }
            tv_info.text = bean.info
            tv_dialog_name.text = bean.name
            tv_content.text = bean.content
            tv_start.setOnClickListener {
                goFragment(
                    R.id.action_main_fragment_to_webFragment,
                    Bundle().apply {
                        putInt("gameid", gameid)
                    })

                dismiss()
            }
        }
}

fun Fragment.showCustomView(layoutId: Int, action: (MaterialDialog, View) -> Unit) {
    MaterialDialog(requireContext())
        .show {
            val view = LayoutInflater.from(requireContext()).inflate(layoutId, null, false)
            customView(view = view, noVerticalPadding = true)
            action(this, view)
        }
}

fun BaseFragment<*, *>.showSwitchAccount(data: ArrayList<String>, action: () -> Unit): Int {
    var mposition = -1
    MaterialDialog(requireContext())
        .show {
            customView(R.layout.dialog_switch_account, noVerticalPadding = true)
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, data)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_account.adapter = adapter

            spinner_account.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    mposition = position
                }
            }
            tv_login.setOnClickListener {
                if (mposition == -1) return@setOnClickListener
                shareViewModel.userList[mposition].let {
                    shareViewModel.isLogin.postValue(true)
                    shareViewModel.token.postValue(it.token)
                    shareViewModel.uid.postValue(it.uid)
                }
                dismiss()
            }

            tv_other_login.setOnClickListener {
                action()
//                goFragment(R.id.action_main_fragment_to_loginFragment)
                dismiss()
            }

        }
    return mposition
}


/**
 * 拦截登录操作，如果没有登录跳转登录，登录过了贼执行你的方法
 */
fun NavController.jumpByLogin(action: (NavController) -> Unit) {
    if (CacheUtil.isLogin()) {
        action(this)
    } else {
        //注意一下，这里我是确定我所有的拦截登录都是在MainFragment中的，所以我可以写死，但是如果不在MainFragment中时跳转，你会报错,
        //当然你也可以执行下面那个方法 自己写跳转
        this.navigate(R.id.action_main_fragment_to_loginFragment)
    }
}

/**
 * 拦截登录操作，如果没有登录执行方法 actionLogin 登录过了执行 action
 */
fun NavController.jumpByLogin(
    actionLogin: (NavController) -> Unit,
    action: (NavController) -> Unit
) {
    if (CacheUtil.isLogin()) {
        action(this)
    } else {
        actionLogin(this)
    }
}




