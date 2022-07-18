package com.lanyou.module_lyzc.main

import com.lanyou.lib_base.base.BaseRepository
import com.lanyou.lib_base.net.BaseResponse
import com.lanyou.lib_base.net.beans.lyzc.ZCAuthBean
import com.lanyou.lib_base.net.beans.zxc.ZXCOrderBean
import okhttp3.MultipartBody

class ZCMainRepository : BaseRepository() {


    suspend fun getMainBubble() =
        lyzcApiServices.getZCBubble()

    suspend fun getLastTwoDayOrderList(
        current: String,
        pageSize: String = "20",
        type: String,
        screeningConditions: String = ""
    ): BaseResponse<ZXCOrderBean> {
        val map = HashMap<String, String>()
        map["current"] = current
        map["pageSize"] = pageSize
        map["type"] = type
        map["screeningConditions"] = screeningConditions
        return lyzcApiServices.getZCLastTwoDayOrder(map)
    }
    suspend fun getAuthList(
        current: String,
        pageSize: String = "20",
        driverStatus: String,
        phone: String = ""
    ): BaseResponse<ZCAuthBean> {
        val map = HashMap<String, String>()
        map["current"] = current
        map["pageSize"] = pageSize
        if (driverStatus.isNotBlank()) {
            map["driverStatus"] = driverStatus
        }
        if (phone.isNotBlank()) {
            map["mobile"] = phone
        }
        return lyzcApiServices.getZCAuthList(map)
    }
}