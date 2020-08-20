package com.example.sougame.app.ext

import android.app.Activity
import android.content.Context
import android.graphics.Outline
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.common.base.fragment.BaseVmDbFragment
import com.example.common.ext.goFragment
import com.example.common.ext.nav
import com.example.common.ext.parseState
import com.example.common.ext.util.toHtml
import com.example.common.network.AppException
import com.example.common.state.ResultState
import com.example.sougame.R
import com.example.sougame.app.App
import com.example.sougame.app.base.BaseFragment
import com.example.sougame.app.util.CacheUtil
import com.example.sougame.data.model.bean.RegisterBean
import com.example.sougame.data.model.bean.TabBean
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_integral_shopping.tab_layout
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView


/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/11 15:03
 * Description: 项目中自定义类的拓展函数
 */
fun ViewPager2.init(
    fragment: Fragment,
    fragments: ArrayList<Fragment>,
    isUserInputEnabled: Boolean = true
): ViewPager2 {
    //是否可以滑动
    this.isUserInputEnabled = isUserInputEnabled
    adapter = object : FragmentStateAdapter(fragment) {
        override fun getItemCount() = fragments.size
        override fun createFragment(position: Int) = fragments[position]
    }
    return this
}

fun MagicIndicator.bindViewPager2(
    viewPager: ViewPager2,
    mDataList: ArrayList<TabBean> = arrayListOf(),
    mStringList: ArrayList<String> = arrayListOf(),
    mMode: Boolean = true,
    action: (index: Int) -> Unit = {}
) {
    val commonNavigator = CommonNavigator(App.instance)
    commonNavigator.adapter = object : CommonNavigatorAdapter() {
        override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
            return ColorTransitionPagerTitleView(App.instance).apply {
                text = if (mDataList.size != 0)
                    mDataList[index].name.toHtml()
                else
                    mStringList[index].toHtml()
                normalColor = R.color.color_title
                selectedColor = R.color.color_yellow
                setOnClickListener {
                    viewPager.currentItem = index
                    Log.e("TAG", "-------:$index ");
                    action.invoke(index)
                }
            }
        }

        override fun getCount() = if (mDataList.size != 0)
            mDataList.size
        else
            mStringList.size


        override fun getIndicator(context: Context?): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_WRAP_CONTENT
//                //线条的宽高度
//                lineHeight = UIUtil.dip2px(App.instance, 3.0).toFloat()
//                lineWidth = UIUtil.dip2px(App.instance, 30.0).toFloat()
//                //线条的圆角
//                roundRadius = UIUtil.dip2px(App.instance, 6.0).toFloat()
//                startInterpolator = AccelerateInterpolator()
//                endInterpolator = DecelerateInterpolator(2.0f)
                //线条的颜色
                setColors(R.color.color_yellow)
            }
        }
    }
    commonNavigator.isAdjustMode = mMode

    this.navigator = commonNavigator
    viewPager.isUserInputEnabled = false
    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            this@bindViewPager2.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            this@bindViewPager2.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            this@bindViewPager2.onPageSelected(position)
            action.invoke(position)
        }
    })
}

//MagicIndicator单独使用
fun MagicIndicator.bindFragment(
    mDataList: ArrayList<TabBean> = arrayListOf(),
    mStringList: ArrayList<String> = arrayListOf(),
    mMode: Boolean = true,
    action: (index: Int) -> Unit = {}
) {
    val commonNavigator = CommonNavigator(App.instance)
    commonNavigator.adapter = object : CommonNavigatorAdapter() {
        override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
            return ColorTransitionPagerTitleView(App.instance).apply {
                text = if (mDataList.size != 0)
                    mDataList[index].name.toHtml()
                else
                    mStringList[index].toHtml()
                normalColor = R.color.color_title
                selectedColor = R.color.color_yellow
                setOnClickListener {
                    Log.e("TAG", "-------:$index ");
                    action.invoke(index)
                }
            }
        }

        override fun getCount() = if (mDataList.size != 0)
            mDataList.size
        else
            mStringList.size


        override fun getIndicator(context: Context?): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_WRAP_CONTENT
//                //线条的宽高度
//                lineHeight = UIUtil.dip2px(App.instance, 3.0).toFloat()
//                lineWidth = UIUtil.dip2px(App.instance, 30.0).toFloat()
//                //线条的圆角
//                roundRadius = UIUtil.dip2px(App.instance, 6.0).toFloat()
//                startInterpolator = AccelerateInterpolator()
//                endInterpolator = DecelerateInterpolator(2.0f)
                //线条的颜色
                setColors(R.color.color_yellow)
            }
        }
    }
    commonNavigator.isAdjustMode = mMode

    this.navigator = commonNavigator
}

fun Fragment.tabLayoutBindFragment(
    tabs: ArrayList<String>,
    fragments: ArrayList<Fragment>
) {
    tabs.forEach {
        tab_layout.addTab(tab_layout.newTab().setText(it))
    }
    val transaction = childFragmentManager.beginTransaction()

    fragments.forEach {
        transaction.add(R.id.fl_fragment, it)
        transaction.hide(it)
    }
    transaction.show(fragments[0])
    transaction.commit()

    tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            Log.e("Tag", "onTabSelected: ====" + tab.position)
            val transaction1 = childFragmentManager.beginTransaction()
            fragments.forEach {
                transaction1.hide(it)
            }
            transaction1.show(fragments[tab.position])
            transaction1.commit()
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {}

        override fun onTabReselected(tab: TabLayout.Tab) {}
    })
}

fun Fragment.radioBindFragment(
    fragments: ArrayList<Fragment>,
    views: IntArray,
    radioGroup: RadioGroup
) {
    val transaction = childFragmentManager.beginTransaction()

    fragments.forEach {
        transaction.add(R.id.fl_fragment, it)
        transaction.hide(it)
    }
    transaction.show(fragments[0])
    transaction.commit()

    radioGroup.setOnCheckedChangeListener { group, checkedId ->
        val transaction1 = childFragmentManager.beginTransaction()
        fragments.forEach {
            transaction1.hide(it)
        }
        transaction1.show(fragments[views.indexOf(checkedId)])
        transaction1.commit()
    }
}

fun tabBindRecyclerView(
    tabLayout: TabLayout,
    tabs: Array<String>,
    action: (position: Int) -> Unit
) {
    tabs.forEach {
        tabLayout.addTab(tabLayout.newTab().setText(it))
    }
    tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabSelected(tab: TabLayout.Tab) {
            action(tab.position)
        }
    })
}

fun <T> BaseVmDbFragment<*, *>.bindObserve(
    result: MutableLiveData<ResultState<T>>,
    onError: ((AppException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onSuccess: (T) -> Unit
) {
    result.observe(viewLifecycleOwner, Observer {
        parseState(it, onSuccess, onError, onLoading)
    })
}

/**
 * 隐藏软键盘
 */
fun hideSoftKeyboard(activity: Activity?) {
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
            val inputMethodManager =
                act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}

fun RecyclerView.getFlexboxLayoutManager() {

}

//绑定普通的RecyclerView
// layoutMangerType：0->VERTICAL，1->HORIZONTAL,2->flexBoxLayoutManger
fun RecyclerView.init(
    bindAdapter: RecyclerView.Adapter<*>? = null,
    layoutMangerType: Int = 0,
    isScroll: Boolean = false,
    isDecoration: Boolean = false,
    layoutMangers: RecyclerView.LayoutManager? = null
): RecyclerView {
    layoutManager = when (layoutMangerType) {
        0 -> LinearLayoutManager(context)
        1 -> LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        2 -> {
            val flexBoxLayoutManger = FlexboxLayoutManager(context)
            //方向 主轴为水平方向，起点在左端
            flexBoxLayoutManger.flexDirection = FlexDirection.ROW
            //左对齐
            flexBoxLayoutManger.justifyContent = JustifyContent.FLEX_START
            flexBoxLayoutManger
        }
        else -> {
            layoutMangers
        }
    }
    bindAdapter?.let {
        adapter = it
    }
    isNestedScrollingEnabled = isScroll
    if (isDecoration)
        addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

    return this
}

fun RecyclerView.initImage(
    imageList: MutableList<String>
): BaseQuickAdapter<String, BaseViewHolder> {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    val mAdapter =
        object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_image, imageList) {
            override fun convert(holder: BaseViewHolder, item: String) {
                holder.getView<ImageView>(R.id.iv_img).loadImage(item)
            }
        }
    adapter = mAdapter
    return mAdapter
}


/**
 * 加载圆角图片
 */
fun ImageView.loadFilletImage(url: String) {
//    setBannerRound(this, 5f)
    Glide.with(this.context)
        .load(url.addHttpString())
        .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))//圆角半径
        .into(this)
}

/**
 * 加载圆形图片
 */
fun ImageView.loadRoundImage(url: String?) {
    url?.let {
        Glide.with(this.context)
            .load(it.addHttpString())
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(this)
    }

}

/**
 * 加载普通图片
 */
fun ImageView.loadImage(url: String) {
    Glide.with(this.context).load(url.addHttpString()).into(this)
}

fun String.addHttpString() =
    if (this.contains("http") || this.contains("https"))
        this
    else
        "https:$this"

fun setBannerRound(view: View, radius: Float) {
    view.outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(0, 0, view.width, view.height, radius)
        }
    }
    view.clipToOutline = true
}

fun BaseFragment<*, *>.isLogin(
    action: () -> Unit
) {
    if (shareViewModel.isLogin.value) {
        action()
    } else {
        goFragment(R.id.action_main_fragment_to_loginFragment)
    }
}


fun Fragment.jumpByWebView(
    actionId: Int,
    bundle: Bundle,
    action: () -> Unit
) {
    if (CacheUtil.isLogin()) {
        goFragment(actionId, bundle)
    } else {
        action.invoke()
//        nav().navigate(R.id.action_webFragment_to_loginFragment, Bundle().apply {
//            putInt("type", 1)
//            putString("img", bean.cover)
//        })
    }
}

