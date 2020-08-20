package com.example.common.ext

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment


/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/12 16:07
 * Description: com.example.common.ext
 */
fun Fragment.nav(): NavController {
    return NavHostFragment.findNavController(this)
}

fun nav(view: View): NavController {
    return Navigation.findNavController(view)
}


var toLastClickTime = 0L
fun Fragment.goFragment(id: Int, bundle: Bundle? = null) {
    val currentTime = System.currentTimeMillis()
    if (toLastClickTime != 0L && currentTime - toLastClickTime < 1000) {
        return
    }
    toLastClickTime = currentTime
    nav().navigate(id, bundle)
}