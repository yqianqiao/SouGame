package com.example.sougame.ui.fragment.invitefriends

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.copy
import com.example.sougame.app.ext.init
import com.example.sougame.app.weight.GridItemDecoration
import com.example.sougame.databinding.FragmentInviteFriendsBinding
import com.example.sougame.ui.adapter.InviteFriendsAdapter
import com.example.sougame.ui.adapter.NumAdapter
import com.example.sougame.viewmodel.InviteFriendsViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_invite_friends.*
import kotlinx.android.synthetic.main.fragment_invite_friends.tv_out
import kotlinx.android.synthetic.main.layout_invite_friends.*
import kotlinx.android.synthetic.main.layout_withdraw.*

class InviteFriendsFragment : BaseFragment<InviteFriendsViewModel, FragmentInviteFriendsBinding>() {

    private val friendsAdapter = InviteFriendsAdapter(arrayListOf())
    private val numAdapter = NumAdapter()

    override fun layoutId() = R.layout.fragment_invite_friends


    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        tv_url.text =
            "http://www.sooyooj.com/index.html?userid=${shareViewModel.userInfo.value?.uid}&sharetype=friendDistribution&activityid=0"

        tv_copy.setOnClickListener {
            ToastUtils.showShort("复制成功")
            copy(tv_url.text.toString())
        }

        rv_withdraw.init(numAdapter, 3, layoutMangers = GridLayoutManager(requireContext(), 3))

        rv_friends.init(friendsAdapter)
        tab_Layout.addTab(tab_Layout.newTab().setText("提现"))
        tab_Layout.addTab(tab_Layout.newTab().setText("好友列表"))
        tab_Layout.addTab(tab_Layout.newTab().setText("赚钱攻略(规则)"))
        tab_Layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                showLayout(tab.position)
            }
        })

        tv_withdraw.setOnClickListener {
            ToastUtils.showShort("请进入微信公众号，进行提现！")
        }
        if (arguments?.getInt("type") == 1)
            tab_Layout.getTabAt(2)?.select()

    }

    private var oldPosition = -1
    private fun showLayout(position: Int) {
        if (oldPosition == position) return
        layout_withdraw.visibility = if (position == 0) View.VISIBLE else View.GONE
        layout_invite_friends.visibility = if (position == 1) View.VISIBLE else View.GONE
        layout_invite_rule.visibility = if (position == 2) View.VISIBLE else View.GONE

        oldPosition = position
    }

    override fun lazyLoadData() {
        mViewModel.getInviteNum()
        numAdapter.setList(mViewModel.getNumData())
    }

    override fun createObserver() {
        eventViewModel.inviteData.observe(viewLifecycleOwner, Observer {
            tv_out.text = it.out.toString()
            tv_invite.text = it.countnum.toString()
            tv_reward.text = it.amountReward.toString()
        })

        mViewModel.inviteNumData.observe(viewLifecycleOwner, Observer {
            parseState(it, { list ->
                friendsAdapter.setList(list)
            })
        })
    }

}