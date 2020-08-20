package com.example.sougame.ui.fragment.my

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.common.ext.goFragment
import com.example.common.ext.nav
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.config.Constant.PAGE_PERSONAL
import com.example.sougame.app.config.Constant.PAGE_RECHARGE_RECORD
import com.example.sougame.app.config.Constant.PAGE_SECURITY
import com.example.sougame.app.ext.*
import com.example.sougame.app.network.NetWorkApi
import com.example.sougame.data.model.bean.UserInfo
import com.example.sougame.databinding.FragmentMyBinding
import com.example.sougame.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.android.synthetic.main.layout_user_info.*

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/11 15:44
 * Description: com.example.sougame.ui.fragment.my
 */
class MyFragment : BaseFragment<MyViewModel, FragmentMyBinding>() {


    override fun layoutId() = R.layout.fragment_my

    override fun initView(savedInstanceState: Bundle?) {
        mDataBind.click = ProxyClick()

    }

    override fun lazyLoadData() {

    }

    override fun createObserver() {
        shareViewModel.isLogin.observe(viewLifecycleOwner, Observer {
            tv_login.text = if (it) {
                mViewModel.getUserInfo()
                mViewModel.getProgressData()
                "退出登录"
            } else {
                layout_not_login.visibility = View.VISIBLE
                layout_user_info.visibility = View.GONE
                "登录"
            }
        })
        mViewModel.run {
            bindObserve(getUserInfo) {
                shareViewModel.userInfo.postValue(it)

                LogUtils.json(it)
                layout_not_login.visibility = View.GONE
                layout_user_info.visibility = View.VISIBLE
                initUserInfo(it)
                it.token = shareViewModel.token.value!!
                it.uid = shareViewModel.uid.value!!
                if (!shareViewModel.userList.contains(it))
                    shareViewModel.userList.add(it)
            }
            bindObserve(progressData) {
                eventViewModel.inviteData.value = it
                tv_invite.text = it.countnum.toString()
                tv_reward.text = it.amountReward.toString()
            }
            bindObserve(signRuquest) {
                ToastUtils.showShort(if (it.accgrade != 0) {
                    shareViewModel.userInfo.value?.let { userinfo ->
                        userinfo.accgrade = userinfo.accgrade + it.accgrade
                    }
                    "签到成功"
                } else
                    "您今天已经签到过了")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initUserInfo(userInfo: UserInfo) {
        mDataBind.layoutUserInfo.ivAvatar.loadRoundImage(userInfo.icon)
        tv_user_name.text = userInfo.nickname
        tv_vip_info.text = "VIP${userInfo.viplevel}了解更多"
        tv_phone.text = "账号：${userInfo.username}"
        tv_uid.text = "UID：${shareViewModel.uid.value}"
        pb_progress.progress = 100 - userInfo.percentage.progress()
        tv_nextLevel_pay.text = "您还需要充值${userInfo.minus}RMB"
        tv_integral.text = "${userInfo.accgrade}积分"
    }

    private fun toPersonalCenter(type: Int) {
        goFragment(R.id.action_main_fragment_to_personalFragment, Bundle().apply {
            putInt("type", type)
        })

    }


    private fun copytext(text: String) {
        copy(text)
    }


    inner class ProxyClick {
        fun titleLogin() {
            nav().jumpByLogin {}
        }

        fun vipInfo() {
            goFragment(R.id.action_main_fragment_to_vipFragment)
        }

        fun inviteFriends() {
           goFragment(R.id.action_main_fragment_to_inviteFriendsFragment)

//            showShare("http://www.sooyooj.com/user.html?u=&c=&s=&share,%E3%80%8A%E6%90%9C%E6%B8%B8%E8%AE%B0%E3%80%8B%E6%B8%B8%E6%88%8F%E5%B9%B3%E5%8F%B0,%E6%88%91%E6%8B%BF%E5%88%B0%E7%8E%B0%E9%87%91%E5%95%A6%EF%BC%81%E5%9C%A8%E3%80%8A%E6%90%9C%E6%B8%B8%E8%AE%B0%E3%80%8B%E7%8E%A9%E6%B8%B8%E6%88%8F%EF%BC%8C%E7%A6%8F%E5%88%A9%E5%A4%9A%E5%A4%9A%EF%BC%8C%E8%BF%98%E8%83%BD%E5%92%8C%E6%88%91%E4%B8%80%E8%B5%B7%E6%8B%BF%E5%A5%96%E5%8A%B1%EF%BC%81,http://www.sooyooj.com/user.html?userid=${shareViewModel.userInfo.value?.uid}&sharetype=friendDistribution&activityid=0,https://img158.sooyooj.com/20190304/utboccqp4S3STgiA.ico")
        }

        fun moder() {
            goFragment(R.id.action_main_fragment_to_inviteFriendsFragment,Bundle().apply {
                putInt("type",1)
            })
        }

        fun withdraw() {
            goFragment(R.id.action_main_fragment_to_inviteFriendsFragment)
        }

        fun sign() {
            nav().jumpByLogin {
                mViewModel.sign()
            }

//            LogUtils.e(shareViewModel.isLogin.value)
//            LogUtils.e(shareViewModel.token.value)
//            LogUtils.e(shareViewModel.uid.value)
//            LogUtils.json(shareViewModel.userInfo.value)
//            LogUtils.json(CacheUtil.getUser())
//            LogUtils.json(CacheUtil.getToken())
//            LogUtils.json(CacheUtil.getUid())
        }

        fun personalCenter() {
            nav().jumpByLogin {
                toPersonalCenter(PAGE_PERSONAL)
            }

        }

        fun security() {
            nav().jumpByLogin {
                toPersonalCenter(PAGE_SECURITY)
            }
        }

        fun rechargeRecord() {
            nav().jumpByLogin {
                toPersonalCenter(PAGE_RECHARGE_RECORD)
            }
        }

        fun customerService() {
            showCustomView(R.layout.dialog_customer_service) { _, view ->
                view.findViewById<TextView>(R.id.tv_copy1).setOnClickListener {
                    copytext("0755-23223286")
                    ToastUtils.showShort("复制成功")
                }
                view.findViewById<TextView>(R.id.tv_copy2).setOnClickListener {
                    copytext("3001188720")
                    ToastUtils.showShort("复制成功")
                }
            }
        }

        fun checkUpdate() {
            mViewModel.checkUpdate()

        }

        fun switchAccount() {
            nav().jumpByLogin {
                showSwitchAccount(shareViewModel.getUserNameList()) {
                    goFragment(R.id.action_main_fragment_to_loginFragment)
                }
            }
        }

        fun login() {
            nav().jumpByLogin {
                showMessage(message = "是否确认退出账号？", negativeButtonText = "取消", positiveAction = {
                    NetWorkApi().cookieJar.clear()
                    shareViewModel.userInfo.postValue(null)
                    shareViewModel.isLogin.postValue(false)
                    shareViewModel.uid.value = null
                    shareViewModel.token.value = null

                    tv_integral.text = ""
                })
            }
        }

    }


}