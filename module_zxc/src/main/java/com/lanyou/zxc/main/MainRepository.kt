package com.lanyou.zxc.main

import com.lanyou.lib_base.base.BaseRepository
import com.lanyou.lib_base.net.BaseResponse
import com.lanyou.lib_base.net.beans.zxc.ZXCOrderBean
import okhttp3.MultipartBody

class MainRepository : BaseRepository() {


    suspend fun getMainBubble() =
        zxcApiServices.getBubble()

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
        return zxcApiServices.getLastTwoDayOrder(map)
    }

}