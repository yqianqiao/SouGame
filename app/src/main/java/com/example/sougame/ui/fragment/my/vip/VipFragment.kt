package com.example.sougame.ui.fragment.my.vip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.loadRoundImage
import com.example.sougame.databinding.FragmentVipBinding
import com.example.sougame.viewmodel.VipViewModel
import kotlinx.android.synthetic.main.fragment_vip.*


class VipFragment : BaseFragment<VipViewModel, FragmentVipBinding>() {


    override fun layoutId() = R.layout.fragment_vip

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun lazyLoadData() {

    }

    override fun createObserver() {
        shareViewModel.userInfo.value?.let {
            iv_avatar.loadRoundImage(it.icon)
            tv_name.text = it.nickname
            tv_integral.text  = "积分：${it.accgrade}"
        }
    }
}