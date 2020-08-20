package com.example.sougame.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.common.ext.nav
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.init
import com.example.sougame.app.ext.jumpByLogin
import com.example.sougame.app.util.WechatHelper
import com.example.sougame.databinding.FragmentMainBinding
import com.example.sougame.ui.fragment.gamepack.GamePackFragment
import com.example.sougame.ui.fragment.home.HomeFragment
import com.example.sougame.ui.fragment.integralmall.IntegralMallFragment
import com.example.sougame.ui.fragment.my.MyFragment
import com.example.sougame.viewmodel.state.MainViewModel
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotlinx.android.synthetic.main.fragment_main.*
import java.net.HttpURLConnection

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/11 11:55
 * Description: com.example.sougame.ui.fragment
 */
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    var fragments = arrayListOf<Fragment>()
    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val gamePackFragment: GamePackFragment by lazy { GamePackFragment() }
    private val integralMallFragment: IntegralMallFragment by lazy { IntegralMallFragment() }
    private val myFragment: MyFragment by lazy { MyFragment() }

    init {
        fragments.apply {
            add(homeFragment)
            add(gamePackFragment)
            add(integralMallFragment)
            add(myFragment)
        }
    }

    override fun layoutId() = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {
        WechatHelper.get().initWxApi(requireContext())
        main_viewpager.init(this, fragments, false)
            .offscreenPageLimit = fragments.size
        main_bottom.run {
            enableAnimation(false)
            labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
//            enableShiftingMode(false)
            isItemHorizontalTranslationEnabled = false
//            enableItemShiftingMode(false)
            setTextSize(12F)
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_home -> main_viewpager.setCurrentItem(0, false)
                    R.id.menu_game_pack -> nav().jumpByLogin {
                        main_viewpager.setCurrentItem(
                            1,
                            false
                        )
                    }
                    R.id.menu_integral_mall -> nav().jumpByLogin {
                        main_viewpager.setCurrentItem(
                            2,
                            false
                        )
                    }
                    R.id.menu_my -> main_viewpager.setCurrentItem(3, false)
                }
                true
            }
        }
    }

    override fun lazyLoadData() {
    }


}