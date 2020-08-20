package com.example.sougame.ui.fragment.integralmall

import android.os.Bundle
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.init
import com.example.sougame.app.ext.tabBindRecyclerView
import com.example.sougame.databinding.FragmentIntegralListBinding
import com.example.sougame.ui.adapter.IntegralRecordAdapter
import com.example.sougame.viewmodel.IntegralListViewModel
import kotlinx.android.synthetic.main.fragment_tab_list.*


class IntegralListFragment : BaseFragment<IntegralListViewModel, FragmentIntegralListBinding>() {
    private val integralRecordAdapter = IntegralRecordAdapter()

    private val tabs = arrayOf("获取记录", "兑换记录")

    override fun layoutId() = R.layout.fragment_integral_list

    override fun initView(savedInstanceState: Bundle?) {
        tab_recyclerView.init(integralRecordAdapter)

        tabBindRecyclerView(tab_Layout, tabs) {
            LogUtils.e(it)
            mViewModel.getIntegralRecord(
                if (it == 0) {
                    "incr"
                } else {
                    "decr"
                }
            )

        }
    }

    override fun lazyLoadData() {
        mViewModel.getIntegralRecord("incr")
    }

    override fun createObserver() {
        mViewModel.integralRecord.observe(viewLifecycleOwner, Observer {
            parseState(it, { list ->
                integralRecordAdapter.setList(list)
            })
        })

    }
}