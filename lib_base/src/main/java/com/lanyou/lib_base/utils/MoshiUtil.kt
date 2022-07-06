package com.lanyou.lib_base.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type

/**
 * json解析工具类
 */
object MoshiUtil {

    private var sMoshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .add(HashMapJsonAdapter.FACTORY)
        .build()

    @JvmStatic
    fun <T> fromJson(json: String, clazz: Class<T>): T? {
        val adapter = sMoshi.adapter(clazz)
        return adapter.fromJson(json)
    }

    @JvmStatic
    fun <T> fromJson(json: String, type: Type): T? {
        val adapter = sMoshi.adapter<T>(type)
        return adapter.fromJson(json)
    }

    @JvmStatic
    fun toJsonStr(bean: Any?): String {
        if (bean == null) {
            return "{}"
        }
        val adapter = sMoshi.adapter(bean.javaClass)
        return adapter.toJson(bean)
    }

}