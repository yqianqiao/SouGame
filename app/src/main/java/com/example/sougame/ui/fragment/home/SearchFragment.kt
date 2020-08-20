package com.example.sougame.ui.fragment.home

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.common.ext.goFragment
import com.example.common.ext.nav
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.init
import com.example.sougame.app.ext.setNbOnItemChildClickListener
import com.example.sougame.app.ext.setNbOnItemClickListener
import com.example.sougame.data.model.bean.SearchTypeBean
import com.example.sougame.databinding.FragmentSearchBinding
import com.example.sougame.ui.adapter.*
import com.example.sougame.viewmodel.SearchViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.dialog_select.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_search_default.*

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {


    private val searchTypeBean = SearchTypeBean()
    private val searchAdapter = SearchAdapter(arrayListOf()).apply {
        loadWeb(this)
    }
    private val historyAdapter = SearchHistoryAdapter(arrayListOf())
    private val hotAdapter = HotAdapter(arrayListOf()).apply {
        loadWeb(this)
    }
    private val gameCommonAdapter = GameCommonAdapter().apply {

        setNbOnItemClickListener { adapter, view, position ->
            val gameid = this.data[position].gameid
            goFragment(R.id.action_searchFragment_to_gameDetailsFragment,
                Bundle().apply {
                    putInt("gameid", gameid)
                })
        }
        addChildClickViewIds(R.id.tv_start)
        setNbOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.tv_start) {
                val gameid = this.data[position].gameid
                goFragment(R.id.action_searchFragment_to_webFragment,
                    Bundle().apply {
                        putInt("gameid", gameid)
                    })
            }
        }

    }
    private val gameTypeAdapter = GameTypeTextAdapter(arrayListOf()).apply {
        setNbOnItemClickListener { adapter, view, position ->
            mViewModel.getGameData(this.data[position].gametypeid)
            searchTypeBean.isScreen.set(!searchTypeBean.isScreen.get())
        }
    }
    private val letterAdapter = GameTypeStringAdapter().apply {
        setNbOnItemClickListener { adapter, view, position ->
            // TODO: 2020/6/23 赛选接口
//            ToastUtils.showShort(data[position])

        }
    }


    private var searchType = "hot"
    override fun layoutId() = R.layout.fragment_search

    override fun initView(savedInstanceState: Bundle?) {
        mDataBind.searchTypeBean = searchTypeBean
        mDataBind.click = ProxyClick()
        initRecyclerView()
    }


    private fun initRecyclerView() {
        rv_search.init(searchAdapter,1)

        rv_history.init(historyAdapter, 2)
        historyAdapter.run {
            //TODO 搜索历史
        }


        rv_game_type.init(gameTypeAdapter, 2)
        letterAdapter.setList(mViewModel.letterList)
        rv_letter.init(letterAdapter, 2)

        //热门推荐
        rv_hot.init(hotAdapter)

        //搜索内容
        rv_search_details.init(gameCommonAdapter)


    }

    override fun lazyLoadData() {
        mViewModel.getSearch(searchType)

    }

    override fun createObserver() {
        mViewModel.run {
            searchData.observe(viewLifecycleOwner, Observer {
                parseState(it, { list ->
                    searchAdapter.setList(list)
                    if (TextUtils.equals(searchType, "hot")) {
                        hotAdapter.setList(list.subList(0, 9))
                    }
                })
            })
            searchGameData.observe(viewLifecycleOwner, Observer {
                parseState(it, { list ->
                    gameCommonAdapter.setList(list)
                })
            })
            gameTypeData.observe(viewLifecycleOwner, Observer {
                parseState(it, { list ->
                    gameTypeAdapter.setList(list)
                })
            })

            gameData.observe(viewLifecycleOwner, Observer {
                parseState(it, { list ->
                    setViewVisibility(1)
                    gameCommonAdapter.setList(list)
                })
            })
        }
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (TextUtils.isEmpty(s.toString())) {
                    setViewVisibility(0)
                } else {
                    setViewVisibility(1)
                    mViewModel.getSearchGame(s.toString())
                }
            }
        })


    }

    private fun loadWeb(adapter: BaseQuickAdapter<*, *>) {
        adapter.setNbOnItemClickListener { adapter, view, position ->
            goFragment(R.id.action_searchFragment_to_webFragment,
                Bundle().apply {
                    putInt("gameid", searchAdapter.data[position].gameid)
                })

        }
    }

    private fun setViewVisibility(type: Int) {
        layout_search_default.visibility = if (type == 0) View.VISIBLE else View.GONE
        rv_search_details.visibility = if (type == 1) View.VISIBLE else View.GONE
        layout_select.visibility = if (type == 2) View.VISIBLE else View.GONE


    }


    inner class ProxyClick {
        fun back() {
            nav().navigateUp()
        }

        fun unlimited() {
            if (!tv_unlimited.isChecked) {
                searchTypeBean.isChecked.set(!searchTypeBean.isChecked.get())
                searchType = "hot"
                mViewModel.getSearch("hot")
                setViewVisibility(0)
            }

        }

        fun newest() {
            if (!tv_newest.isChecked) {
                searchTypeBean.isChecked.set(!searchTypeBean.isChecked.get())
                searchType = "time"
                mViewModel.getSearch("time")
                setViewVisibility(0)
            }
        }

        fun searchSelect() {
            searchTypeBean.isScreen.set(!searchTypeBean.isScreen.get())
            setViewVisibility(if (searchTypeBean.isScreen.get()) 2 else 0)
            if (mViewModel.gameTypeData.value == null)
                mViewModel.getGameTypeData()
        }
    }

}