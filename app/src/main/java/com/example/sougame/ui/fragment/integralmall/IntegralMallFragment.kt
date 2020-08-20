package com.example.sougame.ui.fragment.integralmall

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.example.common.base.viewmodel.BaseViewModel
import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.bindObserve
import com.example.sougame.app.ext.loadRoundImage
import com.example.sougame.app.ext.radioBindFragment
import com.example.sougame.app.util.CacheUtil
import com.example.sougame.databinding.FragmentIntegralMallBinding
import com.example.sougame.ui.fragment.home.OpenServiceFragment
import com.example.sougame.viewmodel.IntegralMallViewModel
import kotlinx.android.synthetic.main.fragment_integral_mall.*
import kotlinx.android.synthetic.main.layout_game_integral.*

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/11 15:43
 * Description: com.example.sougame.ui.fragment.integralmall
 */
class IntegralMallFragment : BaseFragment<IntegralMallViewModel, FragmentIntegralMallBinding>() {

    private var fragments: ArrayList<Fragment> = arrayListOf()
    var rbView_id = intArrayOf(
        R.id.rb_integral_mall,
        R.id.rb_integral_task,
        R.id.rb_integral_record,
        R.id.rb_integral_rule
    )

    init {
        fragments.add(IntegralShoppingFragment())
        fragments.add(IntegralTaskFragment())
        fragments.add(IntegralListFragment())
        fragments.add(IntegralRuleFragment())
    }

    override fun layoutId() = R.layout.fragment_integral_mall

    override fun initView(savedInstanceState: Bundle?) {

        radioBindFragment(fragments, rbView_id, rg_type)


        tv_sign.setOnClickListener {
            mViewModel.sign()
        }

    }

    override fun lazyLoadData() {
    }

    override fun createObserver() {
        mViewModel.run {
            bindObserve(signRuquest) {
                ToastUtils.showShort(if (it.accgrade != 0) {
                    shareViewModel.userInfo.value?.let { userinfo ->
                        userinfo.accgrade = userinfo.accgrade + it.accgrade
                    }
                    "签到成功"
                } else
                    "您今天已经签到过了")
            }
        }

        shareViewModel.userInfo.observe(viewLifecycleOwner, Observer {
          if (it!=null){
              iv_avatar.loadRoundImage(it.icon)
              tv_name.text = it.nickname
              tv_integral.text = it.accgrade.toString()
              tv_integral.setTextColor(ContextCompat.getColor(requireContext(),R.color.color_easy_red))
          }
        })

    }
}