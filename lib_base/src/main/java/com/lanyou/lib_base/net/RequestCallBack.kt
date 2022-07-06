package com.lanyou.lib_base.net

interface RequestCallBack<T> {
    /**
     * 开始
     */
    fun onRequestStart()

    /**
     * 成功
     */
    fun onRequestSuccess(response:  T?)

    /**
     * 失败
     */
    fun onRequestFail(e:AppException)

    /**
     * 请求成功，但 code非常见的值
     */
    fun onCustomResponse(response: BaseResponse<T>)

    /**
     * 结束
     */
    fun onRequestFinish()
}