package com.example.sougame.ui.fragment.integralmall

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.common.ext.goFragment
import com.example.common.ext.nav
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.*
import com.example.sougame.app.util.CacheUtil
import com.example.sougame.databinding.FragmentVirtualGoodsBinding
import com.example.sougame.ui.adapter.LetterAdapter
import com.example.sougame.ui.adapter.SearchAdapter
import com.example.sougame.ui.adapter.VirtualGiftAdapter
import com.example.sougame.viewmodel.VirtualGoodsViewModel
import kotlinx.android.synthetic.main.fragment_virtual_goods.*


class VirtualGoodsFragment : BaseFragment<VirtualGoodsViewModel, FragmentVirtualGoodsBinding>() {
    private val recentAdapter = SearchAdapter(arrayListOf()).apply {
        setNbOnItemClickListener { adapter, view, position ->
            goFragment(R.id.action_main_fragment_to_webFragment, Bundle().apply {
                putInt("gameid", data[position].gameid)
            })
        }
    }
    private val virtualGiftAdapter = VirtualGiftAdapter().apply {
        addChildClickViewIds(R.id.tv_accgrade)
        setNbOnItemChildClickListener { adapter, view, position ->
            accgrade = data[position].accgrade
            showMessage("是否兑换此礼包", negativeButtonText = "取消", positiveAction = {
                mViewModel.virtualGiftbag(data[position].id!!)
            })

        }
    }
    private val giftBagTypeAdapter = VirtualGiftAdapter().apply {
        addChildClickViewIds(R.id.tv_accgrade)
        setNbOnItemChildClickListener { adapter, view, position ->
            val bean = data[position]
            if (bean.isget == 0) {
                accgrade = bean.accgrade
                showMessage("是否兑换此礼包", negativeButtonText = "取消", positiveAction = {
                    mViewModel.virtualGiftbag(bean.giftbagtypeid!!)
                })

            } else {
                gameid = bean.gameid
                mViewModel.giftbag(bean.giftbagtypeid!!)
            }
        }
    }


    private var letterAdapter = LetterAdapter()

    private var accgrade = 0
    private var gameid = 0
    override fun layoutId() = R.layout.fragment_virtual_goods

    override fun initView(savedInstanceState: Bundle?) {
        rv_lately.init(recentAdapter, 1)

        rv_letter.init(letterAdapter, 1)

        rv_game_gift.init(virtualGiftAdapter)

        rv_game_gift_bag.init(giftBagTypeAdapter)


    }

    override fun lazyLoadData() {
        mViewModel.getRecentData()
        letterAdapter.setList(mViewModel.getLetterData())
        mViewModel.getVirtualGift()
//        val svip = shareViewModel.userInfo.value?.svip

        mViewModel.getGamePackList(1, 4)
    }

    override fun createObserver() {
        mViewModel.run {
            recentData.observe(viewLifecycleOwner, Observer {
                parseState(it, { list ->
                    recentAdapter.setList(list)
                })
            })
            virtualGiftList.observe(viewLifecycleOwner, Observer {
                parseState(it, { list ->
                    virtualGiftAdapter.setList(list)
                })
            })
            gamePackList.observe(viewLifecycleOwner, Observer {
                parseState(it, { list ->
                    giftBagTypeAdapter.setList(mViewModel.amendData(list))
                })
            })

            bindObserve(virtualGiftbagData) {
                ToastUtils.showShort("兑换成功")
                shareViewModel.userInfo.value?.let {
                    it.accgrade -= accgrade
                }
            }


            bindObserve(getGiftBag) { bean ->
                showGift(bean, gameid)
            }
        }
    }


}