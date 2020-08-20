package com.example.sougame.ui.fragment.gamedetails

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.common.ext.goFragment
import com.example.common.ext.nav
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.init
import com.example.sougame.app.ext.initImage
import com.example.sougame.app.ext.jumpByWebView
import com.example.sougame.app.ext.loadFilletImage
import com.example.sougame.data.model.bean.GameDetailsBean
import com.example.sougame.databinding.FragmentGameDetailsBinding
import com.example.sougame.ui.adapter.GameGiftBagAdapter
import com.example.sougame.viewmodel.GameDetailsViewModel
import kotlinx.android.synthetic.main.fragment_game_details.*
import kotlinx.android.synthetic.main.layout_title.*

class GameDetailsFragment : BaseFragment<GameDetailsViewModel, FragmentGameDetailsBinding>() {
    private var gameId = 0
    private lateinit var gameImgAdapter: BaseQuickAdapter<String, BaseViewHolder>
    private val gameGiftBagAdapter = GameGiftBagAdapter(arrayListOf())

    private var beans: GameDetailsBean? = null
    override fun layoutId() = R.layout.fragment_game_details

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            gameId = it.getInt("gameid")
        }

        tv_title_name.text = "游戏详情"
        iv_back.setOnClickListener {
            nav().navigateUp()
        }

        gameImgAdapter = rv_game_image.initImage(arrayListOf())
        rv_game_gift_bag.init(gameGiftBagAdapter, isDecoration = true)

        tv_start.setOnClickListener {
            beans?.let {
                jumpByWebView(R.id.action_gameDetailsFragment_to_webFragment, Bundle().apply {
                    putInt("gameid", it.gameid)
                }) {
                    goFragment(R.id.action_gameDetailsFragment_to_loginFragment, Bundle().apply {
                        putString("img", it.cover)
                        putInt("gameid", it.gameid)
                        putInt("type", 1)
                    })
                }
            }


        }

    }

    override fun lazyLoadData() {
        mViewModel.getGameDetailsData(gameId)
        //TODO 用户信息VIP等级
        mViewModel.gameGiftBagData(gameId, 1)

    }

    override fun createObserver() {
        mViewModel.gameDetailsData.observe(viewLifecycleOwner, Observer {
            parseState(it, { bean ->
                beans = bean
                iv_icon.loadFilletImage(bean.icon)
                tv_title.text = bean.subject
                tv_description.text = bean.description
                tv_info.text = bean.info
                gameImgAdapter.setList(bean.images_app.split(","))
                tv_game_info.text =
                    "${bean.guangchushen}\n${bean.isbn}\n${bean.publisher}\n${bean.copyright}"

            })
        })
        mViewModel.gameGiftBagData.observe(viewLifecycleOwner, Observer {
            parseState(it, { list ->
                gameGiftBagAdapter.setList(list)
            })
        })
    }

}