package com.example.common.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.common.base.activity.BaseVmDbActivity
import com.example.common.base.fragment.BaseVmDbFragment
import com.example.common.base.viewmodel.BaseViewModel
import com.example.common.ext.util.loge
import com.example.common.network.AppException
import com.example.common.network.BaseResponse
import com.example.common.state.ResultState
import com.example.common.state.paresException
import com.example.common.state.paresResult
import com.example.common.util.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/16 11:16
 * Description: com.example.common.ext
 */
fun <T> BaseVmDbFragment<*, *>.parseState(
    resultState: ResultState<T>,
    onSuccess: (T) -> Unit,
    onError: ((AppException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null
) {
    when (resultState) {
        is ResultState.Loading -> {
            showLoading(resultState.loadingMessage)
            onLoading?.run { this }
        }
        is ResultState.Success -> {
            dismissLoading()
            onSuccess(resultState.data)
        }
        is ResultState.Error -> {
            dismissLoading()
            ToastUtils.showShort(resultState.error.errorMsg)
            LogUtils.debugInfo(resultState.error.errorMsg)
            onError?.run { this(resultState.error) }
        }
    }
}

fun <T> BaseVmDbActivity<*, *>.parseState(
    resultState: ResultState<T>,
    onSuccess: (T) -> Unit,
    onError: ((AppException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null
) {
    when (resultState) {
        is ResultState.Loading -> {
            showLoading(resultState.loadingMessage)
            onLoading?.run { this }
        }
        is ResultState.Success -> {
            dismissLoading()
            onSuccess(resultState.data)
        }
        is ResultState.Error -> {
            dismissLoading()
            ToastUtils.showShort(resultState.error.errorMsg)
            LogUtils.debugInfo(resultState.error.errorMsg)
            onError?.run { this(resultState.error) }
        }
    }
}



fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    resultState: MutableLiveData<ResultState<T>>,
    isShowDialog: Boolean = false,
    loadingMessage: String = "请求网络中..."
) {
    viewModelScope.launch {
        runCatching {
            if (isShowDialog) resultState.value = ResultState.onAppLoading(loadingMessage)
            withContext(Dispatchers.IO) { block() }
        }.onSuccess {
            resultState.paresResult(it)
        }.onFailure {
            it.message?.loge("YX_MvvM")
            resultState.paresException(it)
        }
    }
}