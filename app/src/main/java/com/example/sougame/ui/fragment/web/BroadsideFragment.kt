package com.example.sougame.ui.fragment.web


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.blankj.utilcode.util.ToastUtils
import com.example.common.ext.goFragment
import com.example.common.ext.nav

import com.example.sougame.R
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.ext.*
import com.example.sougame.data.model.bean.BroadsideGameBean
import com.example.sougame.data.model.bean.ShakyBean
import com.example.sougame.databinding.FragmentBroadsideBinding
import com.example.sougame.ui.adapter.*
import com.example.sougame.viewmodel.BroadsideViewModel
import kotlinx.android.synthetic.main.dialog_back_page.*
import kotlinx.android.synthetic.main.fragment_broadside.*
import kotlinx.android.synthetic.main.layout_broadside.*
import kotlinx.coroutines.*
import java.net.Socket

class BroadsideFragment : BaseFragment<BroadsideViewModel, FragmentBroadsideBinding>() {
    private var dialog: MaterialDialog? = null
    private var gameAdapter = BroadsideGameAdapter().apply {
        addChildClickViewIds(R.id.tv_start)
        setNbOnItemChildClickListener { adapter, view, position ->
            eventViewModel.loadGame.value = data[position].gameid!!
            nav().navigateUp()
        }
    }

    private val searchAdapter = BackPageAdapter(arrayListOf()).apply {
        setNbOnItemClickListener { adapter, view, position ->
            if (position==3){
                goFragment(R.id.action_broadsideFragment_to_main_fragment)
                dialog?.dismiss()
            }else{
                eventViewModel.loadGame.value = data[position].gameid!!
                dialog?.dismiss()
                nav().navigateUp()
            }

        }
    }
    private var job: Job? = null
    //资讯
    private val informationAdapter = InformationAdapter()
    //礼包
    private val gameGiftBagAdapter = BroadsideBagAdapter()

    private val integralAdapter = IntegralAdapter()

    private var informationtypeid = 0
    private var refreshNum = 0


    private var gameid: Int? = null

    private lateinit var recyclerView: RecyclerView

    override fun layoutId() = R.layout.fragment_broadside

    override fun initView(savedInstanceState: Bundle?) {
        mDataBind.click = ProxyClick()
        shareViewModel.userInfo.value?.let {
            iv_avatar.loadRoundImage(it.icon)
            tv_name.text = it.nickname
            tv_uid.text = "UID：${shareViewModel.uid.value}"
        }
        gameid = arguments?.getInt("gameid")
        ll_root.setOnClickListener {  }

        recyclerView = rv_broadside.init()

        rg_type.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_1 -> {

                }
                R.id.rb_2 -> {
                    recyclerView.adapter = informationAdapter
                    if (informationAdapter.data.size == 0) {
                        informationtypeid = 5
                        mViewModel.getShakyData(informationtypeid, gameid)
                    } else {
                        informationAdapter.notifyDataSetChanged()
                    }

                }
                R.id.rb_3 -> {
                    recyclerView.adapter = gameGiftBagAdapter
                    if (gameGiftBagAdapter.data.size == 0) {
                        mViewModel.gameGiftBagData(
                            gameid!!, 1
//                            shareViewModel.userInfo.value?.viplevel ?: 1
                        )
                    } else {
                        gameGiftBagAdapter.notifyDataSetChanged()
                    }

                }
                R.id.rb_4 -> {

                }
                R.id.rb_5 -> {
                    recyclerView.adapter = gameAdapter
                    if (gameAdapter.data.size == 0)
                        mViewModel.getGameList("hot")
                    else
                        gameAdapter.notifyDataSetChanged()

                }

            }
        }

    }

    override fun lazyLoadData() {
        mViewModel.run {
            bindObserve(signResult) {
                ToastUtils.showShort(if (it.accgrade != 0) {
                    shareViewModel.userInfo.value?.let { userinfo ->
                        userinfo.accgrade = userinfo.accgrade + it.accgrade
                    }
                    "签到成功"
                } else
                    "您今天已经签到过了")
            }

            bindObserve(newGameData) {
                searchAdapter.setList(it.subList(0, 3))
                searchAdapter.addData(BroadsideGameBean("http://img158.sooyooj.com/20190121/pXt1fQoiRpJJpcJ2.png","更多游戏"))
                gameAdapter.setList(mViewModel.machiningGameList(it))


            }

            bindObserve(shakyData) { list ->
                list.add(0, ShakyBean(type = 0))
                when (informationtypeid) {
                    5 -> {
                        list.forEach { it.title = "活动" }
                        informationtypeid = 6
                        mViewModel.getShakyData(informationtypeid, gameid)
                    }
                    6 -> {
                        list.forEach { it.title = "资讯" }
                        informationtypeid = 7
                        mViewModel.getShakyData(informationtypeid, gameid)
                    }
                    7 -> {
                        list.forEach { it.title = "公告" }
                    }
                }

                informationAdapter.addData(list)
            }


            bindObserve(gameGiftBagData) {
                gameGiftBagAdapter.setList(mViewModel.amendData(it))
            }

            bindObserve(entityGoodsData) {
                integralAdapter.setList(it)
            }
            bindObserve(bindPhoneResult) {
                ToastUtils.showShort("绑定成功成功")
            }

            bindObserve(bindPhoneCodeResult) {
                ToastUtils.showShort("发送成功")
//                job = GlobalScope.launch {
//                    while (time != 0) {
//                        Thread.sleep(1000)
//                        time--
//                        withContext(Dispatchers.Main) {
//                            code.text = "${time}s"
//                        }
//                    }
//
//                    withContext(Dispatchers.Main) {
//                        code.text = "重新发送"
//                    }
//                }
            }
        }

        rb_5.isChecked = true
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    inner class ProxyClick {
        fun backPage() {
            dialog = MaterialDialog(requireContext())
                .show {
                    customView(R.layout.dialog_back_page, noVerticalPadding = true)
                    rv_more.init(searchAdapter, 1)
                    tv_close.setOnClickListener {
                        goFragment(R.id.action_broadsideFragment_to_main_fragment)
                        dismiss()
                    }
                    tv_submit.setOnClickListener {
                        dismiss()
                    }
                }


//            nav().pop(R.id.action_broadsideFragment_to_main_fragment)
        }

        fun share() {
            showShare("http://www.sooyooj.com/user.html?u=&c=&s=&share,%E3%80%8A%E6%90%9C%E6%B8%B8%E8%AE%B0%E3%80%8B%E6%B8%B8%E6%88%8F%E5%B9%B3%E5%8F%B0,%E6%88%91%E6%8B%BF%E5%88%B0%E7%8E%B0%E9%87%91%E5%95%A6%EF%BC%81%E5%9C%A8%E3%80%8A%E6%90%9C%E6%B8%B8%E8%AE%B0%E3%80%8B%E7%8E%A9%E6%B8%B8%E6%88%8F%EF%BC%8C%E7%A6%8F%E5%88%A9%E5%A4%9A%E5%A4%9A%EF%BC%8C%E8%BF%98%E8%83%BD%E5%92%8C%E6%88%91%E4%B8%80%E8%B5%B7%E6%8B%BF%E5%A5%96%E5%8A%B1%EF%BC%81,http://www.sooyooj.com/user.html?userid=${shareViewModel.userInfo.value?.uid}&sharetype=friendDistribution&activityid=0,https://img158.sooyooj.com/20190304/utboccqp4S3STgiA.ico")

        }

        fun bindPhone() {
            showCustomView(R.layout.dialog_bind_phone) { dialog, view ->
                var time = 60
                val code = view.findViewById<TextView>(R.id.tv_code)
                val phone = view.findViewById<EditText>(R.id.et_phone)
                code.setOnClickListener {

                    mViewModel.bindPhoneCode(phone.text.toString())
                }

                view.findViewById<TextView>(R.id.tv_submit).setOnClickListener {
                    mViewModel.bindPhone(
                        phone.text.toString(),
                        view.findViewById<EditText>(R.id.et_code).text.toString()
                    )
                    dialog.dismiss()
                }

                view.findViewById<TextView>(R.id.tv_close).setOnClickListener {
                    dialog.dismiss()
                }

                mViewModel.run {
                    bindObserve(bindPhoneCodeResult) {
                        ToastUtils.showShort("发送成功")
                        job = GlobalScope.launch {
                            while (time != 0) {
                                Thread.sleep(1000)
                                time--
                                withContext(Dispatchers.Main) {
                                    code.text = "${time}s"
                                }
                            }

                            withContext(Dispatchers.Main) {
                                code.text = "重新发送"
                            }
                        }
                    }

                }

            }
        }

        fun sign() {
            mViewModel.sign()
        }

        fun switchAccount() {
            showSwitchAccount(shareViewModel.getUserNameList()) {
                goFragment(R.id.action_broadsideFragment_to_loginFragment)
            }
        }

        fun integral() {
            recyclerView.adapter = integralAdapter
            if (integralAdapter.data.size == 0) {
                mViewModel.getEntityGoods()
            } else {
                integralAdapter.notifyDataSetChanged()
            }
        }

        fun back() {
            nav().navigateUp()
        }

        fun showVipDialog() {
            showCustomView(R.layout.fragment_vip){_,view->

            }
        }

        fun refresh() {
            nav().navigateUp()
            eventViewModel.isRefresh.value = refreshNum++
        }

        fun hide() {
            nav().navigateUp()
            eventViewModel.isBroadside.value = true
        }

    }

}
