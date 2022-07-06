package com.lanyou.lib_base.utils

import android.util.Log
import okhttp3.RequestBody
import com.lanyou.lib_base.utils.RequestJsonUtil
import com.lanyou.lib_base.utils.AESUtil
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.Exception

/**
 * Created by wang on 2018/6/2.
 */
object RequestJsonUtil {
    private const val TAG = "RequestJsonUtil"
    private val JSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()
    fun getRequestBody_AES(map: Map<String, Any>): RequestBody? {
        try {
            val contentJsonstr: String = MoshiUtil.toJsonStr(map)
            Log.e(TAG, "----明文: $contentJsonstr")
            //String encryJson = AESUtils.encryptByAES(contentJsonstr);
            val encryJson: String? = AESUtil.instance?.encrypt(contentJsonstr)
            return encryJson?.toRequestBody(JSON)
        } catch (e: Exception) {
            Log.e(TAG, "getRequestBody_AES: " + e.message)
            e.printStackTrace()
        }
        return null
    }

    var isEncrypt = false
    fun getRequestBody(map: HashMap<String, Any>): RequestBody {
        var encryJson = ""
        Log.e(TAG, "getRequestBody: -----RequestBody是否要加密：" + isEncrypt)
        encryJson = if (isEncrypt) {
            getRequestJson(map)
        } else {
            MoshiUtil.toJsonStr(map)
        }
        return encryJson.toRequestBody(JSON)
    }

    fun getRequestBody(jsonParam: String): RequestBody {
        var encryJson: String? = ""
        Log.e(TAG, "getRequestBody: -----RequestBody是否要加密：" + isEncrypt)
        encryJson = if (isEncrypt) {
            getRequestJson(jsonParam)
        } else {
            jsonParam
        }
        return encryJson!!.toRequestBody(JSON)
    }

    fun getRequestJson(map: HashMap<String, Any>): String {
        val trim = getEncryData(map)
        //LUtil.e("===加密后的 请求json数据：",encryJson);
        return "{\"privDecrypts\":\"" + trim?.replace("\\s*".toRegex(), "") + "\"}"
    }

    fun getRequestJson(jsonParam: String): String? {
        val trim = getEncryData(jsonParam)
        //LUtil.e("===加密后的 请求json数据：",encryJson);
        return "{\"privDecrypts\":\"" + trim?.replace("\\s*".toRegex(), "") + "\"}"
    }

    fun getEncryData(map: Map<String, Any>): String? {
        val encryJson: String = MoshiUtil.toJsonStr(map)
        return AESUtil.instance?.encrypt(encryJson)
    }

    fun getEncryData(jsonParam: String): String? {
        return AESUtil.instance?.encrypt(jsonParam)
    }
}