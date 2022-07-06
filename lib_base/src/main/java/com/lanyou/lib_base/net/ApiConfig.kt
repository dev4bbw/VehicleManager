package com.lanyou.lib_base.net

import com.lanyou.lib_base.KeyConstant
import com.lanyou.lib_base.utils.mmkvUtil

object ApiConfig {
    //base app============================================================
    private const val BASE_TYPE = 1 // 1-dev 2-uat 3-生产A 4-生产B

    private val _baseUrl = when (BASE_TYPE) {
        1 -> "https://bms-kf.lanyou-mobility.com/lycx/gateway/"
        2 -> "https://dbc-kf.lanyou-mobility.com/lycx/gateway/"
        3 -> "https://bms.lanyou-mobility.com/lycx/gateway/"
        4 -> "https://bms.lanyou-mobility.com/lycxb/gateway/"
        else -> ""
    }

    var baseUrl :String
        get() = _baseUrl
        set(baseUrl){
            mmkvUtil.put(KeyConstant.BASE_APP_URL,baseUrl)
        }

    //h5 ============================================================
     private val _h5Url = when (BASE_TYPE) {
        1 -> "https://bms-kf.lanyou-mobility.com/H5Appdev"
        2 -> "https://bms-kf.lanyou-mobility.com/H5Appdev"
        3 -> "https://marketing.lanyou-mobility.com/b"
        4 -> "https://bms.lanyou-mobility.com/lycxb/gateway/"
        else -> ""
    }
    var baseH5Url :String
        get() = _h5Url
        set(baseH5Url){
           mmkvUtil.put(KeyConstant.BASE_H5_URL,baseH5Url)
        }
}