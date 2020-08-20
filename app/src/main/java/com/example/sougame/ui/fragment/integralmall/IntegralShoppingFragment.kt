package com.example.sougame.ui.fragment.integralmall

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.tabLayoutBindFragment
import com.example.sougame.databinding.FragmentIntegralShoppingBinding
import com.example.sougame.viewmodel.IntegralShoppingViewModel

class IntegralShoppingFragment :
    BaseFragment<IntegralShoppingViewModel, FragmentIntegralShoppingBinding>() {

    var fragments: ArrayList<Fragment> = arrayListOf()

    init {
        fragments.add(VirtualGoodsFragment())
        fragments.add(EntityGoodsFragment())
        fragments.add(VipLuckDrawFragment())
    }


    //标题
    var titleData = arrayListOf("虚拟物品", "实物商品", "VIP抽奖")


    override fun layoutId() = R.layout.fragment_integral_shopping

    override fun initView(savedInstanceState: Bundle?) {
        tabLayoutBindFragment(titleData, fragments)
    }

    override fun lazyLoadData() {
    }


}