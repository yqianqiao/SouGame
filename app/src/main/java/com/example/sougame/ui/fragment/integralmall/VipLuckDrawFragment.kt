package com.example.sougame.ui.fragment.integralmall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.databinding.FragmentVipLuckDrawBinding
import com.example.sougame.viewmodel.VipLuckDrawViewModel


class VipLuckDrawFragment : BaseFragment<VipLuckDrawViewModel,FragmentVipLuckDrawBinding>() {


    override fun layoutId()=R.layout.fragment_vip_luck_draw

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun lazyLoadData() {
    }

}