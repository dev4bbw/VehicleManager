package com.lanyou.module_lyzc.vehiclemanage

import com.lanyou.lib_base.base.BaseViewModel
import com.lanyou.lib_base.base.request

class VehicleManageViewModel:BaseViewModel() {
    private val repository by lazy { VehicleManageRepository() }

    fun loadZCManageList(page:Int,type:String){
        request({repository.loadZCManageList(page.toString(),type)}){
            onRequestSuccess {  }
            onRequestFail {  }
        }
    }
}