package com.lanyou.lib_base.net

typealias OnSuccessCallback<T> = (data: T) -> Unit
typealias OnCustomCallback<T> = (data: T) -> Unit
typealias OnFailureCallback = (e: AppException) -> Unit
typealias OnUnitCallback = () -> Unit

class HttpRequestCallback<T> {

    var startCallback: OnUnitCallback? = null
    var successCallback: OnSuccessCallback<T>? = null
    var emptyCallback: OnUnitCallback? = null
    var customerCallback: OnCustomCallback<BaseResponse<T>>? = null
    var failureCallback: OnFailureCallback? = null
    var finishCallback: OnUnitCallback? = null

    fun onStart(block: OnUnitCallback) {
        startCallback = block
    }

    fun onSuccess(block: OnSuccessCallback<T>) {
        successCallback = block
    }

    fun onEmpty(block: OnUnitCallback) {
        emptyCallback = block
    }

    fun onCustom(block: OnCustomCallback<BaseResponse<T>>){
        customerCallback = block
    }

    fun onFailure(block: OnFailureCallback) {
        failureCallback = block
    }

    fun onFinish(block: OnUnitCallback) {
        finishCallback = block
    }
}