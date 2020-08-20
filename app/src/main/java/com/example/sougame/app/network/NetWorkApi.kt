package com.example.sougame.app.network

import com.blankj.utilcode.util.Utils
import com.example.common.network.BaseNetworkApi
import com.example.common.network.CoroutineCallAdapterFactory
import com.example.common.network.interceptor.CacheInterceptor
import com.example.sougame.app.App
import com.example.sougame.app.network.converter.CheckGsonConverterFactory
import com.example.sougame.app.network.interceptor.HttpBaseInterceptor
import com.example.sougame.app.util.CacheUtil
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import me.hgj.jetpackmvvm.network.interceptor.HeadInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/11 17:58
 * Description: com.example.sougame.app.network
 */
class NetWorkApi : BaseNetworkApi() {
    companion object {

        val instance: NetWorkApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetWorkApi()
        }

        //双重校验锁式-单例 封装NetApiService 方便直接快速调用
        val service: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            instance.getApi(ApiService::class.java, ApiService.SERVER_URL)
        }
    }

    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
            //设置缓存配置 缓存最大10M
            cache(Cache(File(App.instance.cacheDir, "cxk_cache"), 10 * 1024 * 1024))
            //添加Cookies自动持久化
            cookieJar(cookieJar)
            //添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
            addInterceptor(
                HttpBaseInterceptor.Builder()
//                    .addParam("uid", CacheUtil.getUid())
//                    .addParam("token", CacheUtil.getToken())
                    .build()
            )
            //添加缓存拦截器 可传入缓存天数，不传默认7天
            addInterceptor(CacheInterceptor())
            // 日志拦截器
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            //超时时间 连接、读、写
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(5, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)
        }
        return builder
    }

    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
//            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            addConverterFactory(CheckGsonConverterFactory.create(GsonBuilder().create()))
            addCallAdapterFactory(CoroutineCallAdapterFactory())
        }
    }

    val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(Utils.getApp()))
    }
}