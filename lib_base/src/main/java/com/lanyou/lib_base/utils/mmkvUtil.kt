package com.lanyou.lib_base.utils

import android.os.Parcelable
import android.text.TextUtils
import com.tencent.mmkv.MMKV

/**
 * 数据缓存
 */
object mmkvUtil {

    /**
    * 存储常规数据
    */
    fun put(key: String, content: Any?): Boolean {
        if (key.isEmpty() || content == null) return false
        val mmkv = MMKV.defaultMMKV();
        try {
            if (content is String) {
                mmkv.encode(key, content)
            } else if (content is Double) {
                mmkv.encode(key, content)
            } else if (content is Float) {
                mmkv.encode(key, content)
            } else if (content is Int) {
                mmkv.encode(key, content)
            } else if (content is Parcelable) {
                mmkv.encode(key, content)
            } else if (content is ByteArray) {
                mmkv.encode(key, content)
            } else if (content is Long) {
                mmkv.encode(key, content)
            } else if (content is Boolean) {
                mmkv.encode(key, content)
            } else {
                return false
            }
        } catch (exception: Exception) {
            return false
        }
        return true
    }
    /**
     * 存储set集合
     */
    fun putSet(key: String, content: Set<String>): Boolean {
        if (TextUtils.isEmpty(key) || content.isEmpty()) return false
        val mmkv = MMKV.defaultMMKV();
        mmkv.encode(key, content)
        return true
    }

    fun getString(key: String): String {
        val mmkv = MMKV.defaultMMKV();
        return mmkv.decodeString(key,"")?:""
    }

    fun getString(key: String, defVal: String): String {
        val mmkv = MMKV.defaultMMKV();
        if (TextUtils.isEmpty(mmkv.decodeString(key))) return defVal
        return mmkv.decodeString(key,"")?:""
    }

    fun getDouble(key: String): Double {
        val mmkv = MMKV.defaultMMKV();
        return mmkv.decodeDouble(key)
    }

    fun getDouble(key: String, defVal: Double): Double {
        val mmkv = MMKV.defaultMMKV();
        if (mmkv.decodeDouble(key) == 0.0) return defVal
        return mmkv.decodeDouble(key)
    }

    fun getFloat(key: String): Float {
        val mmkv = MMKV.defaultMMKV();
        return mmkv.decodeFloat(key)
    }

    fun getFloat(key: String, defVal: Float): Float {
        val mmkv = MMKV.defaultMMKV();
        if (mmkv.decodeDouble(key) == 0.0) return defVal
        return mmkv.decodeFloat(key)
    }

    fun getInt(key: String): Int {
        val mmkv = MMKV.defaultMMKV();
        return mmkv.decodeInt(key)
    }

    fun getInt(key: String, defult: Int): Int {
        val mmkv = MMKV.defaultMMKV();
        if (mmkv.decodeInt(key) == 0) return defult
        return mmkv.decodeInt(key)
    }

    fun getByteArray(key: String): ByteArray? {
        val mmkv = MMKV.defaultMMKV()
        return mmkv.decodeBytes(key)
    }

    fun <T:Parcelable>getParcelable(key: String, clazz: Class<T>): Parcelable? {
        val mmkv = MMKV.defaultMMKV()
        return mmkv.decodeParcelable(key, clazz)
    }

    fun getSet(key: String): Set<String>? {
        val mmkv = MMKV.defaultMMKV()
        return mmkv.decodeStringSet(key)
    }

    fun removeFromKey(key: String){
        val mmkv = MMKV.defaultMMKV()
        mmkv.removeValueForKey(key)
    }

    fun removeFromKey(vararg key:String){
        val mmkv = MMKV.defaultMMKV()
        val keys = arrayOfNulls<String>(key.size)
        key.forEachIndexed { index, content ->
            keys.set(index, content)
        }
        mmkv.removeValuesForKeys(keys)
    }

    fun removeAll(){
        val mmkv = MMKV.defaultMMKV()
        mmkv.clearAll()
    }
}