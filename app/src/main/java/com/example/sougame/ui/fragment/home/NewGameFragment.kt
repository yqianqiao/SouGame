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
import com.example.sougame.app.ext.jumpByWebView
import com.example.sougame.app.ext.setNbOnItemChildClickListener
import com.example.sougame.databinding.FragmentNewGameBinding
import com.example.sougame.ui.adapter.NewGameAdapter
import com.example.sougame.viewmodel.NewGameViewModel
import kotlinx.android.synthetic.main.fragment_new_game.*

class NewGameFragment : BaseFragment<NewGameViewModel, FragmentNewGameBinding>() {
    private val newGameAdapter = NewGameAdapter(arrayListOf()).apply {
        addChildClickViewIds(R.id.item_game_common, R.id.tv_start)
        setNbOnItemChildClickListener { adapter, view, position ->
            val bean = this.data[position]
            when (view.id) {
                R.id.item_game_common -> {
                    goFragment(
                        R.id.action_main_fragment_to_gameDetailsFragment,
                        Bundle().apply { putInt("gameid", bean.gameid) })
                }
                R.id.tv_start -> {
                    jumpByWebView(R.id.action_main_fragment_to_webFragment, Bundle().apply {
                        putInt("gameid", bean.gameid)
                    }){
                        goFragment(R.id.action_main_fragment_to_loginFragment, Bundle().apply {
                            putString("img", bean.cover)
                            putInt("gameid", bean.gameid)
                            putInt("type", 1)
                        })
                    }

                }
            }
        }
    }

    override fun layoutId() = R.layout.fragment_new_game

    override fun initView(savedInstanceState: Bundle?) {
        rv_new_game.init(newGameAdapter)

    }

    override fun lazyLoadData() {
        mViewModel.getNewGameData(6)
    }

    override fun createObserver() {
        mViewModel.newGameData.observe(viewLifecycleOwner, Observer {
            parseState(it, { list ->
                newGameAdapter.setList(list)
            })
        })
    }
}