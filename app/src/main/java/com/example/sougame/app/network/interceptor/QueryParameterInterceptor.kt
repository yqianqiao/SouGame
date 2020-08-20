package com.example.sougame.app.network.interceptor

import com.example.sougame.app.util.CacheUtil
import okhttp3.*


/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/28 16:43
 * Description: com.example.common.network.interceptor
 */
class QueryParameterInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {


        val originalRequest = chain.request()
        var request: Request? = null

        if ("POST" == originalRequest.method()) {
            if (originalRequest.body() is FormBody) {
                val bodyBuilder = FormBody.Builder()
                var formBody = originalRequest.body() as FormBody
                // 先复制原来的参数
                for (i in 0 until formBody.size()) {
                    bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i))
                }
                // 添加公共参数
                formBody = if (CacheUtil.getToken() != null) {
                    bodyBuilder
                        .addEncoded("uid", CacheUtil.getUid())
                        .addEncoded("token", CacheUtil.getToken())
                        .build()
                } else {
                    bodyBuilder.build()
                }


                request = originalRequest.newBuilder().post(formBody).build()
            } else {

                val modifiedUrl = originalRequest.url().newBuilder()
                    // Provide your custom parameter here
                    .addQueryParameter("uid", CacheUtil.getUid())
                    .addQueryParameter("token", CacheUtil.getToken())
                    .build()

                request = originalRequest.newBuilder().url(modifiedUrl).build()
            }
        } else {
            val modifiedUrl = originalRequest.url().newBuilder()
                // Provide your custom parameter here
                .addQueryParameter("uid", CacheUtil.getUid())
                .addQueryParameter("token", CacheUtil.getToken())
//            .addQueryParameter("deviceid", deviceid)
                .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
        }



        return chain.proceed(request!!)
    }

}