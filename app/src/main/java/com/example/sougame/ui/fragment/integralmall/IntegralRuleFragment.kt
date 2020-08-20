package com.example.sougame.ui.fragment.integralmall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.databinding.FragmentIntegralRuleBinding
import com.example.sougame.viewmodel.IntegralRuleViewModel

class IntegralRuleFragment : BaseFragment<IntegralRuleViewModel, FragmentIntegralRuleBinding>() {


    override fun layoutId() = R.layout.fragment_integral_rule

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun lazyLoadData() {
    }


}