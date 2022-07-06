package com.lanyou.lib_base.net

import android.text.TextUtils
import com.elvishew.xlog.XLog
import com.lanyou.lib_base.KeyConstant
import com.lanyou.lib_base.base.APP
import com.lanyou.lib_base.utils.AESUtil
import com.lanyou.lib_base.utils.AppUtil
import com.lanyou.lib_base.utils.mmkvUtil
import com.lanyou.lib_base.utils.MoshiUtil
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

class MoreUrlInterceptor(private val baseStringUrl: String) : Interceptor {
    private var getTokenTime: Long = 0  //获取token的间隔时间
    private var token: String = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        //原始request
        val originReq = chain.request()
        //获取老的Url
        val originUrl = originReq.url
        val builder = originReq.newBuilder()
        val urlNameList = originReq.headers("urlName")
        if (urlNameList.isNotEmpty()) {
            builder.removeHeader("urlName")
            var baseUrl = baseStringUrl.toHttpUrlOrNull()
            builder.apply {
                addHeader("Access-Token", getAESToken())
                addHeader("Content-Type", "application/json")
                addHeader(
                    "client-version",
                    AppUtil.getVersionName(APP.getInstance().applicationContext)
                )
                addHeader(
                    "n-d-version",
                    AppUtil.getNDVersionName()
                )
                addHeader("client-info", getClientInfo())
            }

            val builder1 = originUrl.newBuilder()
                .scheme((baseUrl as HttpUrl).scheme) //http协议如：http或者https
                .host(baseUrl.host) //主机地址
            val newHttpUrl = builder1.build()
            val newReq = builder.url(newHttpUrl).build()
            return chain.proceed(newReq);
        } else {
            return chain.proceed(originReq)
        }
    }

    private fun getAESToken(): String {
        if (System.currentTimeMillis() - 60 < getTokenTime) token
        val tempToken = mmkvUtil.getString(KeyConstant.REQ_TOKEN)
        XLog.i("Token", tempToken)
        val timestamp = System.currentTimeMillis().toString()

        val map = hashMapOf<String,String>(
            "token" to tempToken,
            "timestamp" to timestamp,
            )
        val aesStr = getAESJsonStr(map)
        return aesStr.replace("\\s*", "")
    }

    private fun getAESJsonStr(turnMap: HashMap<String, String>): String {
        try {
            val jsonStr = MoshiUtil.toJsonStr(turnMap)
            return AESUtil.instance?.encrypt(jsonStr) ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun getClientInfo(): String {
        var versionName =
            AppUtil.getVersionName(APP.getInstance().applicationContext)
        var androidId: String = AppUtil.deviceId
        var model: String = AppUtil.systemModel
        var androidVersion: String = AppUtil.systemVersion
        val platform = "android"
        val lon: Double
        val lat: Double
        var location: String? = null
        var districtCode: String? = null
//        val aMapLocation: AMapLocation = Config.getAMapLocation()
        val aMapLocation: String? = null
        val builder = StringBuilder()
        if (TextUtils.isEmpty(versionName)) {
            versionName = ""
        }
        if (TextUtils.isEmpty(androidId)) {
            androidId = ""
        }
        if (TextUtils.isEmpty(model)) {
            model = ""
        }
        if (TextUtils.isEmpty(androidVersion)) {
            androidVersion = ""
        }
        if (aMapLocation == null) {
            location = ""
            districtCode = ""
        } else {
//            lon = aMapLocation.getLongitude()
//            lat = aMapLocation.getLatitude()
//            location = if (0.0 == lon || 0.0 == lat) {
//                ""
//            } else {
//                "$lon-$lat"
//            }
//            districtCode = aMapLocation.getAdCode()
        }
        builder.append(versionName).append(",")
            .append(androidId).append(",")
            .append(model).append(",")
            .append("Android").append(androidVersion).append(",")
            .append(location).append(",")
            .append(platform).append(",")
            .append(districtCode)
        return builder.toString()
    }
}