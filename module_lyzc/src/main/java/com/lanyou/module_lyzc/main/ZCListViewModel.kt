package com.lanyou.module_lyzc.main

import androidx.lifecycle.MutableLiveData
import com.lanyou.lib_base.base.BaseViewModel
import com.lanyou.lib_base.base.request
import com.lanyou.lib_base.net.ExceptionHandle
import com.lanyou.lib_base.net.beans.zxc.ZXCMainBubbleBean
import com.lanyou.lib_base.net.beans.zxc.ZXCOrderBean
import com.lanyou.lib_base.utils.ToastUtil

class ZCListViewModel : BaseViewModel() {
    private val repository by lazy {
        ZCMainRepository()
    }

    var isMainRefreshing = MutableLiveData<Boolean>(false)
    var isChildFinishRefresh = MutableLiveData<Boolean>(false)
    var listData  = MutableLiveData<ZXCOrderBean>()
    var bubbleBean = MutableLiveData<ZXCMainBubbleBean>()
    fun loadBubble() {
        request({ repository.getMainBubble() }) {
            onRequestSuccess {
                if (it != null) {
                    bubbleBean.value = it
                } else {
                    ToastUtil.toastCustomer(ExceptionHandle.handleException(Throwable("无数据")).err)
                }

            }
            onRequestFail {
                ToastUtil.toastCustomer(it.message)
            }
        }
    }

    fun getListData(current: String, type: String, condition: String="") {
        request({
            repository.getLastTwoDayOrderList(
                current = current, type = type, screeningConditions = condition
            )
        }) {
            onRequestSuccess {
                isMainRefreshing.value = false
                isChildFinishRefresh.value = true
                if (it != null) {
                    listData.value = it
                } else {
                    ToastUtil.toastCustomer(ExceptionHandle.handleException(Throwable("无数据")).err)
                }
            }
            onRequestFail {
                isMainRefreshing.value = false
                isChildFinishRefresh.value = true
                ToastUtil.toastCustomer(it.message)
            }

        }
    }
}