package com.lanyou.lib_base.net

import com.elvishew.xlog.XLog
import com.lanyou.lib_base.BuildConfig
import com.lanyou.lib_base.ConfigConstant
import com.lanyou.lib_base.net.services.CommonApiServices
import com.lanyou.lib_base.net.services.LYZCApiServices
import com.lanyou.lib_base.net.services.ZXCApiServices
import com.lanyou.lib_base.utils.HashMapJsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object RetrofitClient {
    val commonService:CommonApiServices by lazy {
        getRetrofit().create(CommonApiServices::class.java)
    }

    val zxcService:ZXCApiServices by lazy {
        getRetrofit().create(ZXCApiServices::class.java)
    }

    val lyzcService:LYZCApiServices by lazy {
        getRetrofit().create(LYZCApiServices::class.java)
    }

    fun getRetrofit():Retrofit{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(HashMapJsonAdapter.FACTORY)
            .build()
        val builder = OkHttpClient.Builder()
            .connectTimeout(ConfigConstant.NET_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(ConfigConstant.NET_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
            .addInterceptor(MoreUrlInterceptor(ApiConfig.baseUrl))
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(builder.build())
            .baseUrl(ApiConfig.baseUrl)
            .build()
        XLog.tag("Retrofit").d("初始化完成")
        return retrofit
    }

}