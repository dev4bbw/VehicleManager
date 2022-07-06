package com.lanyou.vehicle.repository

import com.lanyou.lib_base.base.APP
import com.lanyou.lib_base.base.BaseRepository
import com.lanyou.lib_base.net.BaseResponse
import com.lanyou.lib_base.net.beans.common.UpdateInfoBean
import com.lanyou.lib_base.utils.AppUtil
import kotlin.collections.HashMap

class SplashRepository : BaseRepository() {

    suspend fun checkUpdate():BaseResponse<UpdateInfoBean> {
        val map = HashMap<String,String>()
        map["appType"] = "CAR"
        map["platform"] = "android"
        map["versionNo"] = AppUtil.getVersionCode(APP.getInstance().applicationContext).toString()
        return commonApiServices.updateApp(map)
    }

}