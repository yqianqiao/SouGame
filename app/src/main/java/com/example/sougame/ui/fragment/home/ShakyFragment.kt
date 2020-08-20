package com.example.sougame.ui.fragment.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.ext.goFragment
import com.example.common.ext.nav
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.init
import com.example.sougame.app.ext.setNbOnItemClickListener
import com.example.sougame.databinding.FragmentShakyBinding
import com.example.sougame.ui.adapter.NoticeAdapter
import com.example.sougame.ui.adapter.ShakyAdapter
import com.example.sougame.viewmodel.ShakyViewModel
import kotlinx.android.synthetic.main.fragment_shaky.*


class ShakyFragment : BaseFragment<ShakyViewModel, FragmentShakyBinding>() {
    private val shakyAdapter = ShakyAdapter(arrayListOf()).apply {
        setNbOnItemClickListener { adapter, view, position ->
            goFragment(R.id.action_main_fragment_to_webFragment, Bundle().apply {
                putString(
                    "url",
                    "http://www.sooyooj.com/info.html?id=${data[position].informationid}&u=&c="
                )
            })
        }
    }
    private val noticeAdapter = NoticeAdapter(arrayListOf()).apply {
        setNbOnItemClickListener { adapter, view, position ->
            goFragment(R.id.action_main_fragment_to_webFragment, Bundle().apply {
                putString(
                    "url",
                    "http://www.sooyooj.com/info.html?id=${data[position].informationid}&u=&c="
                )
            })
        }
    }
    private val strategyAdapter = NoticeAdapter(arrayListOf()).apply {
        setNbOnItemClickListener { adapter, view, position ->
            goFragment(R.id.action_main_fragment_to_webFragment, Bundle().apply {
                putString(
                    "url",
                    "http://www.sooyooj.com/info.html?id=${data[position].informationid}&u=&c="
                )
            })
        }
    }

    private var informationtypeid = 5
    override fun layoutId() = R.layout.fragment_shaky

    override fun initView(savedInstanceState: Bundle?) {
        rv_shaky.init(shakyAdapter, isDecoration = true)

        rg_type.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_1 -> {
                    if (shakyAdapter.data.size == 0) {
                        informationtypeid = 5
                        mViewModel.getShaky(informationtypeid)
                    }

                    rv_shaky.adapter = shakyAdapter
                    shakyAdapter.notifyDataSetChanged()
                }
                R.id.rb_2 -> {
                    if (noticeAdapter.data.size == 0) {
                        informationtypeid = 7
                        mViewModel.getShaky(informationtypeid)
                    }

                    rv_shaky.adapter = noticeAdapter
                    noticeAdapter.notifyDataSetChanged()
                }
                R.id.rb_3 -> {
                    if (strategyAdapter.data.size == 0) {
                        informationtypeid = 8
                        mViewModel.getShaky(informationtypeid)
                    }
                    rv_shaky.adapter = strategyAdapter
                    strategyAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun lazyLoadData() {
        mViewModel.getShaky(informationtypeid)
    }

    override fun createObserver() {
        mViewModel.shakyData.observe(viewLifecycleOwner, Observer {
            parseState(it, { list ->
                when (informationtypeid) {
                    5 -> shakyAdapter.setList(list)
                    7 -> noticeAdapter.setList(list)
                    else -> strategyAdapter.setList(list)
                }
            })
        })
    }
}