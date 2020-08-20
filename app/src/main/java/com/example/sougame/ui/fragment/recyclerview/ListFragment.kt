package com.example.sougame.ui.fragment.recyclerview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.ext.goFragment
import com.example.common.ext.nav
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.config.Constant
import com.example.sougame.app.config.Constant.INTEGRAL_RECORD
import com.example.sougame.app.config.Constant.PERSONAL_MY_PACK
import com.example.sougame.app.config.Constant.RECENTLY_PLAY
import com.example.sougame.app.config.Constant.RECHARGE_RECORD
import com.example.sougame.app.ext.init
import com.example.sougame.app.ext.setNbOnItemChildClickListener
import com.example.sougame.app.ext.setNbOnItemClickListener
import com.example.sougame.databinding.FragmentListBinding
import com.example.sougame.ui.adapter.GameCommonAdapter
import com.example.sougame.ui.adapter.GamePackAdapter
import com.example.sougame.ui.adapter.IntegralRecordAdapter
import com.example.sougame.ui.adapter.RechargeRecordAdapter
import com.example.sougame.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.layout_title.*

class ListFragment : BaseFragment<ListViewModel, FragmentListBinding>() {
    private var playedAdapter = GameCommonAdapter().apply {
        setNbOnItemClickListener { adapter, view, position ->
            val gameid = this.data[position].gameid
            goFragment(R.id.action_listFragment_to_gameDetailsFragment,
                Bundle().apply {
                    putInt("gameid", gameid)
                })
        }
        addChildClickViewIds(R.id.tv_start)
        setNbOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.tv_start) {
                val gameid = this.data[position].gameid
                goFragment(R.id.action_listFragment_to_webFragment,
                    Bundle().apply {
                        putInt("gameId", gameid)
                    })
            }
        }
    }

    private val rechargeRecordAdapter = RechargeRecordAdapter()
    private val integralRecordAdapter = IntegralRecordAdapter()
    private val gamePackAdapter = GamePackAdapter(
        arrayListOf(),
        R.id.action_listFragment_to_webFragment,
        R.id.action_listFragment_to_gameDetailsFragment
    ){_,post->

    }

    private val type by lazy {
        arguments?.getInt("type")
    }

    override fun layoutId() = R.layout.fragment_list

    override fun initView(savedInstanceState: Bundle?) {
        iv_back.setOnClickListener {
            nav().navigateUp()
        }

        tv_title_name.text = arguments?.getString("title")



        when (type) {
            RECHARGE_RECORD -> mRecyclerView.init(  //充值记录
                rechargeRecordAdapter,
                isDecoration = true
            )
            INTEGRAL_RECORD -> mRecyclerView.init(   //积分记录
                integralRecordAdapter,
                isDecoration = true
            )
            RECENTLY_PLAY -> mRecyclerView.init(playedAdapter, isDecoration = true)     //最近玩过
            PERSONAL_MY_PACK -> mRecyclerView.init(gamePackAdapter)  //我的礼包


        }
    }

    override fun lazyLoadData() {
        when (type) {
            RECHARGE_RECORD -> mViewModel.getRechargeRecord()
            INTEGRAL_RECORD -> mViewModel.getIntegralRecord()
            RECENTLY_PLAY -> mViewModel.getRecentData()
            PERSONAL_MY_PACK -> mViewModel.getGiftBagHistory()
        }

    }

    override fun createObserver() {
        mViewModel.run {
            recentData.observe(viewLifecycleOwner, Observer {
                parseState(it, { list ->
                    playedAdapter.setList(list)
                })
            })
            rechargeRecord.observe(viewLifecycleOwner, Observer {
                parseState(it, { list ->
                    rechargeRecordAdapter.setList(list)
                })
            })
            integralRecord.observe(viewLifecycleOwner, Observer {
                parseState(it, { list ->
                    integralRecordAdapter.setList(list)
                })
            })
            giftBagHistoryList.observe(viewLifecycleOwner, Observer {
                parseState(it, { list ->
                    gamePackAdapter.data.clear()
                    gamePackAdapter.data.addAll(mViewModel.amendData(list))
                    gamePackAdapter.notifyDataSetChanged()
                })
            })



        }
    }

}