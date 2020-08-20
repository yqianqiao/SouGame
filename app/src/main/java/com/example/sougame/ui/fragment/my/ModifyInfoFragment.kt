package com.example.sougame.ui.fragment.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.example.common.ext.nav
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.config.Constant.CHANGE_PASSWORD
import com.example.sougame.app.config.Constant.REAL_NAME
import com.example.sougame.databinding.FragmentModifyInfoBinding
import com.example.sougame.viewmodel.ModifyInfoViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_modify_info.*
import kotlinx.android.synthetic.main.fragment_modify_info.tv_text
import kotlinx.android.synthetic.main.layout_title.*


class ModifyInfoFragment : BaseFragment<ModifyInfoViewModel, FragmentModifyInfoBinding>() {
    private val type by lazy {
        arguments?.getInt("type")
    }

    private var text = ""
    private var text1 = ""

    override fun layoutId() = R.layout.fragment_modify_info

    override fun initView(savedInstanceState: Bundle?) {
        iv_back.setOnClickListener {
            nav().navigateUp()
        }

        if (type == CHANGE_PASSWORD) {
            tv_title_name.text = "修改密码"
            et_text.hint = "请输入旧密码"
            et_text1.hint = "请输入新密码"

        } else if (type == REAL_NAME) {
            tv_title_name.text = "实名认证"
            et_text.hint = "请输入姓名"
            et_text1.hint = "请输入身份证号"
            tv_text.text = "用于账号防沉迷认证，是账号申诉的重要凭证！"
        }


        tv_submit.setOnClickListener {
            submit()
        }
    }

    override fun lazyLoadData() {
        mViewModel.run {
            certificationData.observe(viewLifecycleOwner, Observer {
                parseState(it, {
                    ToastUtils.showShort("实名认证成功")
                    shareViewModel.userInfo.value?.realname = text
                    shareViewModel.userInfo.value?.card = text1
                    nav().navigateUp()
                })
            })
            resetPasswordData.observe(viewLifecycleOwner, Observer {
                parseState(it, {
                    ToastUtils.showShort("重置密码成功")
                    nav().navigateUp()
                })
            })
        }
    }


    private fun submit() {
        text = et_text.text.toString().trim()
        text1 = et_text1.text.toString().trim()

        if (type == 0) {
            when {
                text.isEmpty() -> {
                    ToastUtils.showShort("请输入旧密码")
                }
                text1.isEmpty() -> {
                    ToastUtils.showShort("请输入新密码")
                }
                else -> {
                    mViewModel.getResetPassword(text, text1)
                }
            }
        } else {
            when {
                text.isEmpty() -> {
                    ToastUtils.showShort("请输入姓名")
                }
                text1.isEmpty() && text1.length != 18 -> {
                    ToastUtils.showShort("请输入正确身份证号")
                }
                else -> {
                    mViewModel.certification(text, text1)
                }
            }
        }
    }


}