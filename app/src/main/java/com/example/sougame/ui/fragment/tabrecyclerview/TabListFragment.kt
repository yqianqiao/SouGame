package com.example.sougame.ui.fragment.tabrecyclerview

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.init
import com.example.sougame.app.ext.tabBindRecyclerView
import com.example.sougame.data.model.bean.Coupon
import com.example.sougame.databinding.FragmentTabListBinding
import com.example.sougame.ui.adapter.CouponAdapter
import com.example.sougame.viewmodel.TabListViewModel
import kotlinx.android.synthetic.main.fragment_tab_list.*


class TabListFragment : BaseFragment<TabListViewModel, FragmentTabListBinding>() {


    private var unusedList = arrayListOf<Coupon>()
    private var loseList = arrayListOf<Coupon>()
    private val couponAdapter = CouponAdapter()

    private val tabs = arrayOf("未使用", "已失效")

    override fun layoutId() = R.layout.fragment_tab_list

    override fun initView(savedInstanceState: Bundle?) {

        tab_recyclerView.init(couponAdapter)

        tabBindRecyclerView(tab_Layout, tabs) {
            couponAdapter.setList(if (it == 0) unusedList else loseList)
        }

    }

    override fun lazyLoadData() {
        mViewModel.getCouponList()
    }

    override fun createObserver() {
        mViewModel.couponList.observe(viewLifecycleOwner, Observer {
            parseState(it, { bean ->
                unusedList = bean.unused
                loseList = bean.lose
                couponAdapter.setList(unusedList)
            })
        })
    }
}