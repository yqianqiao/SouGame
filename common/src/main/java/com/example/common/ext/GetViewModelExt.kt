package com.example.common.ext

import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.common.base.BaseApp
import com.example.common.base.activity.BaseVmDbActivity
import com.example.common.base.fragment.BaseVmDbFragment
import com.example.common.base.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/10 23:20
 * Description: com.example.common.ext
 */

/**
 * 获取当前类绑定的泛型ViewModel-clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}

/**
 * 在Activity中得到Application上下文的ViewModel
 */
inline fun <reified VM : BaseViewModel> AppCompatActivity.getAppViewModel(): VM {
    (this.application as? BaseApp).let {
        if (it == null) {
            throw NullPointerException("你的Application没有继承框架自带的BaseApp类，暂时无法使用getAppViewModel该方法")
        } else {
            return it.getAppViewModelProvider().get(VM::class.java)
        }
    }
}

/**
 * 在Fragment中得到Application上下文的ViewModel
 * 提示，在fragment中调用该方法时，请在该Fragment onCreate以后调用或者请用by lazy方式懒加载初始化调用，不然会提示requireActivity没有导致错误
 */
inline fun <reified VM : BaseViewModel> Fragment.getAppViewModel(): VM {
    (this.requireActivity().application as? BaseApp).let {
        if (it == null) {
            throw NullPointerException("你的Application没有继承框架自带的BaseApp类，暂时无法使用getAppViewModel该方法")
        } else {
            return it.getAppViewModelProvider().get(VM::class.java)
        }
    }
}

/**
 * 得到当前Activity上下文的ViewModel
 */
inline fun <reified VM : BaseViewModel> AppCompatActivity.getViewModel(): VM {
    return ViewModelProvider(
        this,
        ViewModelProvider.AndroidViewModelFactory(application)
    ).get(VM::class.java)
}

/**
 * 得到当前Fragment上下文的ViewModel
 * 提示，在fragment中调用该方法时，请在该Fragment onCreate以后调用或者请用by lazy方式懒加载初始化调用，不然会提示requireActivity没有导致错误
 */
inline fun <reified VM : BaseViewModel> Fragment.getViewModel(): VM {
    return ViewModelProvider(
        this,
        ViewModelProvider.AndroidViewModelFactory(this.requireActivity().application)
    ).get(VM::class.java)
}

/**
 * 得到当前Fragment上下文的ViewModel
 * 提示，在fragment中调用该方法时，请在该Fragment onCreate以后调用或者请用by lazy方式懒加载初始化调用，不然会提示requireActivity没有导致错误
 */
inline fun <reified VM : BaseViewModel> FrameLayout.getViewModel(): VM {
    (context as? BaseVmDbActivity<*, *>).let {
        if (it == null)
            throw ClassCastException("类型转换错误")
        else
           return ViewModelProvider(
                it,
                ViewModelProvider.AndroidViewModelFactory(it.application)
            ).get(VM::class.java)

    }
}

/**
 * 在Fragment中得到父类Activity的共享ViewModel
 * 提示，在fragment中调用该方法时，请在该Fragment onCreate以后调用或者请用by lazy方式懒加载初始化调用，不然会提示requireActivity没有导致错误
 */
inline fun <reified VM : BaseViewModel> Fragment.getActivityViewModel(): VM {
    return ViewModelProvider(
        requireActivity(),
        ViewModelProvider.AndroidViewModelFactory(this.requireActivity().application)
    ).get(VM::class.java)
}