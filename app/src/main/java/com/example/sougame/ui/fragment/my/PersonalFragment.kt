package com.example.sougame.ui.fragment.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.common.ext.goFragment
import com.example.common.ext.nav
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.config.Constant.CHANGE_PASSWORD
import com.example.sougame.app.config.Constant.INTEGRAL_RECORD
import com.example.sougame.app.config.Constant.PAGE_PERSONAL
import com.example.sougame.app.config.Constant.PAGE_RECHARGE_RECORD
import com.example.sougame.app.config.Constant.PAGE_SECURITY
import com.example.sougame.app.config.Constant.PERSONAL_MY_PACK
import com.example.sougame.app.config.Constant.REAL_NAME
import com.example.sougame.app.config.Constant.RECENTLY_PLAY
import com.example.sougame.app.config.Constant.RECHARGE_RECORD
import com.example.sougame.databinding.FragmentPersonalBinding
import com.example.sougame.viewmodel.PersonalViewModel
import kotlinx.android.synthetic.main.fragment_personal.*
import kotlinx.android.synthetic.main.layout_title.*

/**
 * 个人中心，账户安全，充值/积分记录
 */
class PersonalFragment : BaseFragment<PersonalViewModel, FragmentPersonalBinding>() {
    private val type by lazy {
        arguments?.getInt("type")
    }


    override fun layoutId() = R.layout.fragment_personal

    override fun initView(savedInstanceState: Bundle?) {
        iv_back.setOnClickListener {
            nav().navigateUp()
        }
        initTitle()

        mDataBind.click = ProxyClick()
    }

    private fun initTitle() {
        tv_title_name.text = when (type) {
            PAGE_PERSONAL -> {
                tv_text.text = "我的礼包"
                tv_text1.text = "我的卡券"
                tv_text2.text = "我玩过的"
                "个人中心"
            }
            PAGE_SECURITY -> {
                tv_text.text = "修改密码"
                tv_text1.text = "绑定手机"
                tv_text2.text = "实名认证"
                tv_text1_1.text = shareViewModel.userInfo.value?.phone?.replaceRange(
                    3,
                    shareViewModel.userInfo.value?.phone!!.length - 3,
                    "********"
                )
                tv_text2_2.text = shareViewModel.userInfo.value?.card?.replaceRange(
                    3,
                    shareViewModel.userInfo.value?.card!!.length - 3,
                    "********"
                )
                "账户安全"
            }
            else -> {
                tv_text.text = "充值记录"
                tv_text1.text = "积分记录"
                ll_3.visibility = View.GONE
                "充值/积分记录"
            }

        }
    }

    override fun lazyLoadData() {
//        shareViewModel.userInfo.observe(viewLifecycleOwner, Observer {
//            if (type==1){
//                tv_text1_1.text = it.phone.replaceRange(
//                    3,
//                    it.phone.length - 3,
//                    "********"
//                )
//                tv_text2_2.text = it.card.replaceRange(
//                    3,
//                    it.card.length - 3,
//                    "********"
//                )
//            }
//        })

    }


    inner class ProxyClick {
        fun myGiftBag() {
            when (type) {
                PAGE_PERSONAL -> { //我的礼包
                    goFragment(R.id.action_personalFragment_to_listFragment, Bundle().apply {
                        putString("title", "我的礼包")
                        putInt("type", PERSONAL_MY_PACK)
                    })
                }
                PAGE_SECURITY -> { //修改密码
                    goFragment(
                        R.id.action_personalFragment_to_modifyInfoFragment,
                        Bundle().apply {
                            putInt("type", CHANGE_PASSWORD)
                        })
                }
                PAGE_RECHARGE_RECORD -> {
                    goFragment(R.id.action_personalFragment_to_listFragment, Bundle().apply {
                        putString("title", "充值记录")
                        putInt("type", RECHARGE_RECORD)
                    })
                }
            }
        }

        fun myCard() {
            when (type) {
                PAGE_PERSONAL -> { //我的卡券
                    goFragment(R.id.action_personalFragment_to_tabListFragment)
                }
                PAGE_SECURITY -> {
//                    goFragment(
//                        R.id.action_personalFragment_to_modifyInfoFragment,
//                        Bundle().apply {
//                            putInt("type", CHANGE_PASSWORD)
//                        })
                }
                PAGE_RECHARGE_RECORD -> {
                    goFragment(R.id.action_personalFragment_to_listFragment, Bundle().apply {
                        putString("title", "积分记录")
                        putInt("type", INTEGRAL_RECORD)
                    })
                }
            }
        }

        fun myPlayed() {
            if (type == 0)
                goFragment(R.id.action_personalFragment_to_listFragment, Bundle().apply {
                    putString("title", "最近玩过")
                    putInt("type", RECENTLY_PLAY)
                })
            else {
                shareViewModel.userInfo.value?.let {
                    if (it.card.isEmpty()) {
                        goFragment(
                            R.id.action_personalFragment_to_modifyInfoFragment,
                            Bundle().apply {
                                putInt("type", REAL_NAME)
                            })
                    }
                }
            }
        }
    }
}