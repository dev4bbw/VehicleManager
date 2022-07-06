package com.lanyou.lib_base.net

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.lanyou.lib_base.base.BaseViewModel


/**
 * 监听 LiveData 的值的变化，回调为 DSL 的形式
 */
inline fun <T> LiveData<BaseResponse<T>>.observeState(
    owner: LifecycleOwner,
    model: BaseViewModel,
    showDialog:Boolean = true,
    callback:HttpRequestCallback<T>.() -> Unit
) {
    val requestCallback = HttpRequestCallback<T>().apply(callback)
    observe(owner, object : IStateObserver<T> {
        override fun onStart() {
            if (showDialog) model.uiChangeLiveData.showDialogEvent.post()
            requestCallback.startCallback?.invoke()
        }

        override fun onSuccess(data: T) {
            requestCallback.successCallback?.invoke(data)
        }

        override fun onEmpty() {
            requestCallback.emptyCallback?.invoke()
        }

        override fun onCustomResponse(response: BaseResponse<T>) {
            requestCallback.customerCallback?.invoke(response)
        }

        override fun onFailure(e: AppException) {
            requestCallback.failureCallback?.invoke(e)
        }

        override fun onFinish() {
            model.uiChangeLiveData.dismissDialogEvent.post()
            requestCallback.finishCallback?.invoke()
        }
    })
}



