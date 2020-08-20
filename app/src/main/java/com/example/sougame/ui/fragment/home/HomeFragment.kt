package com.example.sougame.ui.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.common.ext.goFragment
import com.example.common.ext.nav
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.jumpByWebView
import com.example.sougame.app.ext.tabLayoutBindFragment
import com.example.sougame.databinding.FragmentHomeBinding
import com.example.sougame.ui.adapter.BannerImageAdapter
import com.example.sougame.viewmodel.HomeViewModel
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    private val bannerAdapter = BannerImageAdapter(arrayListOf())

    //fragment集合
    var fragments: ArrayList<Fragment> = arrayListOf()

    init {
        fragments.add(HotFragment())
        fragments.add(NewGameFragment())
        fragments.add(ShakyFragment())
        fragments.add(OpenServiceFragment())
    }


    //标题
    var titleData = arrayListOf("热门", "新游", "活动", "开服")
    override fun layoutId() = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        tv_search.setOnClickListener {
            Log.e("TAG", ": -----");
            goFragment(R.id.action_main_fragment_to_searchFragment)
        }
        tabLayoutBindFragment(titleData,fragments)

        tv_notice.isSelected = true

        banner.run {
            addBannerLifecycleObserver(viewLifecycleOwner)//添加生命周期观察者
            adapter = bannerAdapter
            setOnBannerListener { data, position ->
                jumpByWebView(R.id.action_main_fragment_to_webFragment,Bundle().apply {
                    putInt("gameid", bannerAdapter.list[position].gameid)
                }){
                    goFragment(R.id.action_main_fragment_to_loginFragment,Bundle().apply {
                        putString("img", bannerAdapter.list[position].images)
                        putInt("gameid", bannerAdapter.list[position].gameid)
                        putInt("type", 1)
                    })

                }
//                goFragment(R.id.action_main_fragment_to_webFragment, Bundle().apply {
//                    putInt("gameid", bannerAdapter.list[position].gameid)
//                })
            }
            indicator = CircleIndicator(context)
            start()
        }
    }

    override fun lazyLoadData() {
        mViewModel.getBannerData()

    }

    override fun createObserver() {
        mViewModel.bannerData.observe(viewLifecycleOwner, Observer {
            parseState(it, { bean ->
                bannerAdapter.list.clear()
                bannerAdapter.list.add(bean.mobileswipe1)
                bannerAdapter.list.add(bean.mobileswipe2)
                bannerAdapter.list.add(bean.mobileswipe3)
                bannerAdapter.list.add(bean.mobileswipe4)
                bannerAdapter.list.add(bean.mobileswipe5)
                bannerAdapter.notifyDataSetChanged()
            })
        })
    }
}