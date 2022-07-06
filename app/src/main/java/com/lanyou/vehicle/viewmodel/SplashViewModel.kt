package com.lanyou.vehicle.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lanyou.lib_base.base.BaseViewModel
import com.lanyou.lib_base.base.request
import com.lanyou.lib_base.net.beans.common.UpdateInfoBean
import com.lanyou.lib_base.utils.ToastUtil
import com.lanyou.vehicle.repository.SplashRepository

class SplashViewModel : BaseViewModel() {
    private val repository by lazy {
        SplashRepository()
    }
    var data = MutableLiveData<UpdateInfoBean?>()

    fun updateData() {
        request({ repository.checkUpdate() }) {
            onRequestSuccess {
                data.value = it
            }
            onRequestFail {
                ToastUtil.toastCustomer(it.message)
            }
        }
    }
}