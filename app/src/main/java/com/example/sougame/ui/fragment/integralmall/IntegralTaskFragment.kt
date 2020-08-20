package com.example.sougame.ui.fragment.integralmall

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.databinding.FragmentIntegralTaskBinding
import com.example.sougame.viewmodel.IntegralTaskViewModel
import kotlinx.android.synthetic.main.fragment_integral_task.*


class IntegralTaskFragment : BaseFragment<IntegralTaskViewModel, FragmentIntegralTaskBinding>() {


    override fun layoutId() = R.layout.fragment_integral_task

    override fun initView(savedInstanceState: Bundle?) {
        tv_invite_friends.setOnClickListener {

        }
        tv_withdraw.setOnClickListener {

        }


    }

    override fun lazyLoadData() {

    }

    @SuppressLint("SetTextI18n")
    override fun createObserver() {
        eventViewModel.inviteData.observe(viewLifecycleOwner, Observer {
            tv_amountReward.text = "已邀请${it.countnum}人，已奖励${it.amountReward}元"
        })
    }

}