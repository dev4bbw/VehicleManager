package com.lanyou.lib_base.net

private typealias OnCommonCallBack = () -> Unit
private typealias OnSuccessCallBack<T> = (data: T?) -> Unit
private typealias OnCustomCallBack<T> = (data: BaseResponse<T>) -> Unit
private typealias OnFailCallBack = (e: AppException) -> Unit

class CustomCallBack<T> : RequestCallBack<T> {
    private var onCommonCallBack: OnCommonCallBack? = null
    private var onSuccessCallBack: OnSuccessCallBack<T?>? = null
    private var onCustomCallBack: OnCustomCallBack<T>? = null
    private var onFailCallBack: OnFailCallBack? = null

    /**
     * 开始
     */
    override fun onRequestStart() = onCommonCallBack?.invoke() ?: Unit

    /**
     * 成功
     */
    override fun onRequestSuccess(response: T?) = onSuccessCallBack?.invoke(response)  ?: Unit

    /**
     * 失败
     */
    override fun onRequestFail(e: AppException) = onFailCallBack?.invoke(e) ?: Unit

    /**
     * 请求成功，但 code非常见的值
     */
    override fun onCustomResponse(response: BaseResponse<T>) =
        onCustomCallBack?.invoke(response) ?: Unit

    /**
     * 结束
     */
    override fun onRequestFinish() = onCommonCallBack?.invoke() ?: Unit

    fun onRequestStart(callBack: OnCommonCallBack) {
        onCommonCallBack = callBack
    }

    fun onRequestSuccess(callback: OnSuccessCallBack<T?>) {
        onSuccessCallBack = callback
    }

    fun onRequestFail(callback: OnFailCallBack) {
        onFailCallBack = callback
    }

    fun onCustomResponse(callback: OnCustomCallBack<T>) {
        onCustomCallBack = callback
    }


    fun onRequestFinish(callBack: OnCommonCallBack) {
        onCommonCallBack = callBack
    }
}

fun <T> registerCustomCallBack(function: CustomCallBack<T>.() -> Unit) =
    CustomCallBack<T>().also(function)