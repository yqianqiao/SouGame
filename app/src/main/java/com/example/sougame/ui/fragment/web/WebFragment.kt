package com.example.sougame.ui.fragment.web

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.common.ext.goFragment
import com.example.common.ext.nav
import com.example.common.ext.parseState
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.jumpByLogin
import com.example.sougame.app.util.CacheUtil
import com.example.sougame.databinding.FragmentWebBinding
import com.example.sougame.viewmodel.WebViewModel
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.fragment_web.*

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/15 18:31
 * Description: web
 */
class WebFragment : BaseFragment<WebViewModel, FragmentWebBinding>() {

    private var mAgentWeb: AgentWeb? = null
    private var gameid: Int? = null
    private var url: String? = null

    override fun layoutId() = R.layout.fragment_web

    override fun initView(savedInstanceState: Bundle?) {
        gameid = arguments?.getInt("gameid")
        url = arguments?.getString("url")

    }

    override fun lazyLoadData() {
        but.visibility = if (gameid == 0) {
            View.GONE
        } else {
            mViewModel.getGameDetailsData(gameid!!)
            View.VISIBLE
        }
        but.setOnClickListener {
            goFragment(R.id.action_webFragment_to_broadsideFragment, Bundle().apply {
                putInt("gameid", gameid!!)
            })
        }


        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(web_content, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(url)

        //添加返回键逻辑
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mAgentWeb?.let {
                        if (it.webCreator.webView.canGoBack()) {
                            it.webCreator.webView.goBack()
                        } else {
                            nav().navigateUp()
                        }
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun createObserver() {
        mViewModel.gameDetailsData.observe(viewLifecycleOwner, Observer {
            parseState(it, { bean ->
                mAgentWeb?.urlLoader?.loadUrl(bean.preurl)
            })
        })

        eventViewModel.run {
            isRefresh.observe(viewLifecycleOwner, Observer {
                mAgentWeb?.urlLoader?.reload()
            })
            isBroadside.observe(viewLifecycleOwner, Observer {
                if (it) but.visibility = View.GONE
            })
            loadGame.observe(viewLifecycleOwner, Observer {
                gameid = it
                lazyLoadData()
            })
        }
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        (activity as? AppCompatActivity)?.setSupportActionBar(null)
        eventViewModel.isBroadside.value = false
        super.onDestroy()

    }


}