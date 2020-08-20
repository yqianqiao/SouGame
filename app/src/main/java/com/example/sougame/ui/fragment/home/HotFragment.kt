package com.example.sougame.ui.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.common.ext.goFragment
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.*
import com.example.sougame.data.model.bean.GameListBean
import com.example.sougame.data.model.bean.GameType
import com.example.sougame.databinding.FragmentHotBinding
import com.example.sougame.ui.adapter.GameCommonAdapter
import com.example.sougame.ui.adapter.HandpickAdapter
import com.example.sougame.ui.adapter.RecommendAdapter
import com.example.sougame.ui.adapter.SearchAdapter
import com.example.sougame.viewmodel.HotViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_hot.*
import kotlinx.android.synthetic.main.item_game_common.*
import kotlinx.android.synthetic.main.item_game_details.*


class HotFragment : BaseFragment<HotViewModel, FragmentHotBinding>(), View.OnClickListener {

    private val handpickAdapter = HandpickAdapter(arrayListOf()).apply {
        setNbOnItemClickListener { adapter, view, position ->
            toGameDetails(this.data[position])
        }
        addChildClickViewIds(R.id.tv_start)
        setNbOnItemChildClickListener { adapter, view, position ->
            toGame(this.data[position])
        }
    }
    private val recommendAdapter = RecommendAdapter(arrayListOf()).apply {
        setNbOnItemClickListener { adapter, view, position ->
            toGame(this.data[position])
        }
    }
    private val gameCommonAdapter = GameCommonAdapter().apply {
        setNbOnItemClickListener { adapter, view, position ->
            toGameDetails(this.data[position])
        }
        addChildClickViewIds(R.id.tv_start)
        setNbOnItemChildClickListener { adapter, view, position ->
            toGame(this.data[position])
        }
    }

    private val recentAdapter = SearchAdapter(arrayListOf()).apply {
        setNbOnItemClickListener { adapter, view, position ->
            jumpByWebView(R.id.action_main_fragment_to_webFragment, Bundle().apply {
                putInt("gameid", data[position].gameid)
            }){
                goFragment(R.id.action_main_fragment_to_loginFragment, Bundle().apply {
                    putString("img", data[position].cover)
                    putInt("gameid", data[position].gameid)
                    putInt("type", 1)
                })
            }
//            goFragment(R.id.action_main_fragment_to_webFragment, Bundle().apply {
//                putInt("gameid", data[position].gameid)
//            })
        }
    }

    private lateinit var gameImgAdapter: BaseQuickAdapter<String, BaseViewHolder>
    private var position = 0
    private var page = 0

    private lateinit var typelist: ArrayList<GameType>
    private var gameIdMap = mutableMapOf<Int, GameListBean>()

    override fun layoutId() = R.layout.fragment_hot

    override fun initView(savedInstanceState: Bundle?) {


        rv_handpick.init(handpickAdapter, 1)
        rv_recommend.init(recommendAdapter, 1)
        rv_game_list.init(gameCommonAdapter)

        gameImgAdapter = rv_game_image.initImage(arrayListOf())

        item_game_common.setOnClickListener(this)
        tv_start.setOnClickListener(this)
        ll_1.setOnClickListener(this)
        ll_2.setOnClickListener(this)
        tv_game_start_1.setOnClickListener(this)
        tv_game_start_2.setOnClickListener(this)

        refreshLayout.setEnableLoadMore(true)
        refreshLayout.setOnLoadMoreListener {
            Log.e("TAG", "=======: ")
            if (position == 0)
                loadGameList(page++ * 10)
            else
                refreshLayout.finishLoadMore(true)
        }

        tab_Layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                position = tab.position
                if (tab.position == 0) {
                    loadGameList()
                } else {
                    loadGameList(gametypeid = typelist[tab.position - 1].gametypeid)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun lazyLoadData() {
        mViewModel.getHomeData(1)
        mViewModel.getGameTypeData()
        loadGameList()
    }

    private fun loadGameList(offset: Int = 0, gametypeid: Int? = null) {
        mViewModel.getGameData("hot", 58, offset, gametypeid)
    }

    override fun createObserver() {
        mViewModel.run {
            bindObserve(recentData) {
                recentAdapter.setList(it)
            }
            bindObserve(homeData) {
                mDataBind.home = it
                initRecommend(it.recommend[0])
                initHot(it.hot)
                initHandpick(it.handpick)
            }
            bindObserve(gameTypeData) {
                typelist = it
                tab_Layout.addTab(tab_Layout.newTab().setText("全部"))
                for (bean in it) {
                    tab_Layout.addTab(tab_Layout.newTab().setText(bean.name))
                }
            }
            bindObserve(gameData) {
                if (position == 0) {
                    recommendAdapter.setList(it.subList(0, 10))
                }
                gameCommonAdapter.setList(it)
                refreshLayout.finishLoadMore()
            }
        }


        shareViewModel.userInfo.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                ll_lately.visibility = View.GONE
            } else {
                ll_lately.visibility = View.VISIBLE
                rv_lately.init(recentAdapter, 1)
                mViewModel.getRecentData()
            }
        })
    }

    private fun initHandpick(handpick: ArrayList<GameListBean>) {
        handpickAdapter.setList(handpick)
    }

    @SuppressLint("SetTextI18n")
    private fun initHot(hot: ArrayList<GameListBean>) {
        gameIdMap[1] = hot[0]
        gameIdMap[2] = hot[1]
        iv_game_1.loadFilletImage(hot[0].hot_img)
        iv_game_2.loadFilletImage(hot[1].hot_img)
        tv_game_info_1.text = "${hot[0].gametypename} | ${hot[0].playnum}人关注"
        tv_game_info_2.text = "${hot[1].gametypename} | ${hot[1].playnum}人关注"

    }

    @SuppressLint("SetTextI18n")
    private fun initRecommend(recommend: GameListBean) {
        gameIdMap[0] = recommend
        mDataBind.gameInfo = recommend
        iv_icon.loadFilletImage(recommend.icon)
        tv_grade.text = "小编评分：${recommend.grade}分"
        val tabs = recommend.tag.split(",")
        val tagView = arrayOf(tv_tag_1, tv_tag_2, tv_tag_3)
        for (i in tabs.indices) {
            val textView: TextView = tagView[i]
            if (!TextUtils.isEmpty(tabs[i])) {
                textView.visibility = View.VISIBLE
                textView.text = tabs[i]
            }
        }
        tv_play_num.text = recommend.playnum.formatNum()
        gameImgAdapter.setList(recommend.images_app.split(","))

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.item_game_common -> {
                gameIdMap[0]?.let { toGameDetails(it) }
            }
            R.id.tv_start -> {
                gameIdMap[0]?.let { toGame(it) }
            }
            R.id.ll_1 -> {
                gameIdMap[1]?.let { toGameDetails(it) }
            }
            R.id.ll_2 -> {
                gameIdMap[2]?.let { toGameDetails(it) }
            }
            R.id.tv_game_start_1 -> {
                gameIdMap[1]?.let { toGame(it) }
            }
            R.id.tv_game_start_2 -> {
                gameIdMap[2]?.let { toGame(it) }
            }

        }
    }

    private fun toGameDetails(bean: GameListBean) {
        goFragment(R.id.action_main_fragment_to_gameDetailsFragment, Bundle().apply {
            putInt("gameid", bean.gameid)
        })
    }

    private fun toGame(bean: GameListBean) {
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

