package com.lanyou.vehicle.viewmodel

import androidx.lifecycle.*
import com.lanyou.lib_base.BuildConfig
import com.lanyou.lib_base.KeyConstant
import com.lanyou.lib_base.StatusConstant
import com.lanyou.lib_base.base.BaseViewModel
import com.lanyou.lib_base.base.request
import com.lanyou.lib_base.net.ApiConfig
import com.lanyou.lib_base.net.ExceptionHandle
import com.lanyou.lib_base.net.beans.common.UserInfoBean
import com.lanyou.lib_base.utils.mmkvUtil
import com.lanyou.lib_base.utils.ToastUtil
import com.lanyou.vehicle.repository.LoginRepository

class LoginViewModel : BaseViewModel() {
    private val repository by lazy { LoginRepository() }

    var userInfo = MutableLiveData<UserInfoBean>()

    fun login(username: String, password: String) {
        request({repository.login(username, password)}){
            onRequestFail { ToastUtil.toastCustomer(it.message) }
            onRequestSuccess {
                if (it != null) {
                    mmkvUtil.put(KeyConstant.REQ_TOKEN, it.`Access-Token`)
                    mmkvUtil.put(KeyConstant.DEVICE_ID, it.deviceId)
                    mmkvUtil.put(StatusConstant.IS_LOGIN,true)
                    if (it.h5Url?.isNotBlank() == true) {
                        ApiConfig.baseH5Url = it.h5Url!!
                    }
                    getUserInfo(username, password)
                }else{
                    ToastUtil.toastCustomer(ExceptionHandle.handleException(Throwable("无数据")).err)
                }
            }
            onCustomResponse {
                when (it.code) {
                    "updatePwd" -> {
                        ToastUtil.toastCustomer("需要设置密码")
                    }
                    else -> {
                        ToastUtil.toastCustomer(
                            if (it.message.isNullOrBlank()) it.message
                            else ExceptionHandle.handleException(Throwable("请求错误")).err
                        )
                    }
                }
            }
        }
    }

    private fun getUserInfo(username: String, password: String) {
        request({repository.getUserInfo()}) {
            onRequestSuccess {
                mmkvUtil.put(KeyConstant.USER_INFO, it)
                mmkvUtil.put(KeyConstant.LOGIN_ACCOUNT, username)
                if (BuildConfig.DEBUG) {
                    mmkvUtil.put(KeyConstant.LOGIN_PSW, password)
                }
                userInfo.value = it
            }
            onRequestFail {
                ToastUtil.toastCustomer(it.message)
            }
        }
    }


}