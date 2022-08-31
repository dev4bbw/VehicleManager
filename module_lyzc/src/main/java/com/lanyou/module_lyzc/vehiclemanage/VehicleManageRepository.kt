package com.lanyou.module_lyzc.vehiclemanage

import com.lanyou.lib_base.base.BaseRepository
import com.lanyou.lib_base.net.BaseResponse

class VehicleManageRepository:BaseRepository() {

    suspend fun loadZCManageList(current:String,type:String):BaseResponse<String>{
        val map = HashMap<String, String>()
        map["current"] = current
        map["pageSize"] = "20"
        if ("-1" != type) {
            map["operationStatus"] = type
        }
        return lyzcApiServices.loadZCManageList(map)
    }
}