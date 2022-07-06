package com.lanyou.lib_base.base

import androidx.lifecycle.viewModelScope
import com.elvishew.xlog.XLog
import com.lanyou.lib_base.net.*
import kotlinx.coroutines.*

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param isShowDialog 是否显示加载框
 * @param callback 结果回调
 */
fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    isShowDialog: Boolean = true,
    callback: CustomCallBack<T>.() -> Unit
) {
    val requestCallback = registerCustomCallBack<T>(callback)
    viewModelScope.launch {
        runCatching {
            if (isShowDialog) uiChangeLiveData.showDialogEvent.postValue("loading")
            block()
        }.onSuccess {
            uiChangeLiveData.dismissDialogEvent.postValue(null)
            executeResponse(it,
                { t ->
                    requestCallback.onRequestSuccess(t)
                }, { ex ->
                    requestCallback.onRequestFail(ex)
                }, { custom ->
                    requestCallback.onCustomResponse(custom)
                })
        }.onFailure {
            uiChangeLiveData.dismissDialogEvent.postValue(null)
            requestCallback.onRequestFail(ExceptionHandle.handleException(it))
        }
    }
}

/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> executeResponse(
    response: BaseResponse<T>,
    success: suspend CoroutineScope.(T?) -> Unit,
    fail: suspend CoroutineScope.(AppException) -> Unit,
    custom: suspend CoroutineScope.(BaseResponse<T>) -> Unit,
) {
    coroutineScope {
        when (response.code) {
            "ok" -> {
                success(response.data)
            }
            "fail" -> {
                fail(
                    AppException(
                        status = Error.RESPONSE_ERROR.getKey(),
                        message = response.message
                    )
                )
            }
            "logout" -> {

            }
            else -> {
                custom(response)
            }
        }
    }
}

