package com.lanyou.lib_base.net

import androidx.lifecycle.Observer

/**
 * @author Lin
 * @date 2021/10/15
 * @function 自定义 Observer，监听 LiveData 的值的变化
 */

interface IStateObserver<T> : Observer<BaseResponse<T>> {

    override fun onChanged(response: BaseResponse<T>?) {
        when (response) {
            is StartResponse -> {
                //onStart()回调后不能直接就调用onFinish()，必须等待请求结束
                onStart()
                return
            }
            is SuccessResponse -> response.data1?.let { onSuccess(it) }
            is EmptyResponse -> onEmpty()
            is FailureResponse -> onFailure(response.exception)
            is CustomerResponse -> onCustomResponse(response)
        }
        onFinish()
    }

    /**
     * 请求开始
     */
    fun onStart()

    /**
     * 请求成功，且 data 不为 null
     */
    fun onSuccess(data: T)

    /**
     * 请求成功，但 data 为 null 或者 data 是集合类型但为空
     */
    fun onEmpty()

    /**
     * 请求成功，但 code非常见的值
     */
    fun onCustomResponse(response: BaseResponse<T>)


    /**
     * 请求失败
     */
    fun onFailure(e: AppException)

    /**
     * 请求结束
     */
    fun onFinish()
}