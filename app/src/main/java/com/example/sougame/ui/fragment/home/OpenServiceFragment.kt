package com.example.sougame.ui.fragment.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.example.common.ext.goFragment
import com.example.common.ext.nav
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.init
import com.example.sougame.app.ext.jumpByWebView
import com.example.sougame.app.ext.setNbOnItemChildClickListener
import com.example.sougame.app.ext.setNbOnItemClickListener
import com.example.sougame.databinding.FragmentOpenServiceBinding
import com.example.sougame.ui.adapter.OpenServiceAdapter
import com.example.sougame.viewmodel.OpenServiceViewModel
import kotlinx.android.synthetic.main.fragment_open_service.*
import kotlinx.android.synthetic.main.fragment_shaky.rg_type

class OpenServiceFragment : BaseFragment<OpenServiceViewModel, FragmentOpenServiceBinding>() {
    private val openServiceAdapter = OpenServiceAdapter(arrayListOf()).apply {
        setNbOnItemClickListener { adapter, view, position ->
            goFragment(R.id.action_main_fragment_to_gameDetailsFragment, Bundle().apply {
                putInt("gameid", data[position].gameid)
            })
        }
        addChildClickViewIds(R.id.tv_start)
        setNbOnItemChildClickListener { adapter, view, position ->

            if (view.id == R.id.tv_start) {
                if (type==1){
                    jumpByWebView(R.id.action_main_fragment_to_webFragment, Bundle().apply {
                        putInt("gameid", data[position].gameid)
                    }){
                        goFragment(R.id.action_main_fragment_to_loginFragment, Bundle().apply {
//                            putString("img", data[position].cover)
                            putInt("gameid", data[position].gameid)
                            putInt("type", 1)
                        })
                    }
                }else{
                    // TODO: 2020/6/23 判断用户登录
                    ToastUtils.showShort("开服提醒")
                }
            }

        }
    }

    override fun layoutId() = R.layout.fragment_open_service

    override fun initView(savedInstanceState: Bundle?) {
        rv_open_service.init(openServiceAdapter, isDecoration = true)
        rg_type.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_1 -> {
                    openServiceAdapter.type = 1
                    mViewModel.getOpenServiceData(1)
                }
                R.id.rb_2 -> {
                    openServiceAdapter.type = 2
                    mViewModel.getOpenServiceData(2)
                }

            }
        }
    }

    override fun lazyLoadData() {
        mViewModel.getOpenServiceData(1)
    }

    override fun createObserver() {
        mViewModel.openServiceData.observe(viewLifecycleOwner, Observer {
            parseState(it, { list ->
                openServiceAdapter.setList(list)
            })
        })
    }

}