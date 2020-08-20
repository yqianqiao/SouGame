package com.example.sougame.ui.fragment.gamepack

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.bindObserve
import com.example.sougame.app.ext.init
import com.example.sougame.app.ext.loadRoundImage
import com.example.sougame.app.ext.showGift
import com.example.sougame.data.model.bean.GamePackBean
import com.example.sougame.databinding.FragmentGamePackBinding
import com.example.sougame.ui.adapter.GamePackAdapter
import com.example.sougame.viewmodel.GamePackViewModel
import kotlinx.android.synthetic.main.fragment_game_pack.*
import kotlinx.android.synthetic.main.layout_game_integral.*

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/11 15:39
 * Description: com.example.sougame.ui.fragment.gamepack
 */
class GamePackFragment : BaseFragment<GamePackViewModel, FragmentGamePackBinding>() {

    private val gamePackAdapter =
        GamePackAdapter(
            arrayListOf(),
            R.id.action_main_fragment_to_webFragment,
            R.id.action_main_fragment_to_gameDetailsFragment
        ) { _, position ->
            gameid = giftbags[position].gameid
            mViewModel.giftbag(giftbags[position].giftbagtypeid!!)
        }

    private var giftbags = arrayListOf<GamePackBean>()

    private var vip = 0
    private var gameid = 0

    override fun layoutId() = R.layout.fragment_game_pack

    override fun initView(savedInstanceState: Bundle?) {

        rv_game_gift_bag.init(gamePackAdapter)


    }

    override fun lazyLoadData() {
        mViewModel.getGamePackList(vip)
    }

    override fun createObserver() {
        mViewModel.run {
            bindObserve(gamePackList) {
                gamePackAdapter.data.clear()
                gamePackAdapter.data.addAll(mViewModel.amendData(it))

                gamePackAdapter.notifyDataSetChanged()
                giftbags = gamePackAdapter.data
            }

            bindObserve(getGiftBag) { bean ->
                showGift(bean, gameid)
            }
        }
        shareViewModel.isLogin.observe(viewLifecycleOwner, Observer {
            if (it) {
                mViewModel.getGamePackList(vip)
            }
        })


        shareViewModel.userInfo.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                iv_avatar.loadRoundImage(it.icon)
                tv_name.text = it.nickname
                tv_integral.text = it.accgrade.toString()
                vip = it.viplevel
            }
        })


    }

}