package com.lanyou.lib_base.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.elvishew.xlog.XLog
import com.lanyou.lib_base.net.*
import com.lanyou.lib_base.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseRepository {
    val commonApiServices = RetrofitClient.commonService
    val zxcApiServices = RetrofitClient.zxcService
    val lyzcApiServices = RetrofitClient.lyzcService

//
//    /**
//     *  过滤请求结果
//     * @param block 请求体 必须要用suspend关键字修饰
//     */
//    protected fun <T> http(
//        context: CoroutineContext = Dispatchers.IO,
//        block: suspend () -> BaseResponse<T>
//    ): LiveData<BaseResponse<T>> = liveData(context) {
//        this.runCatching {
//            emit(StartResponse())
//            block()
//        }.onSuccess {
//            when (it.code) {
//                "ok" -> {
//                    if (it.data == null) {
//                        emit(EmptyResponse())
//                    } else {
//                        emit(SuccessResponse(it.data!!))
//                    }
//                }
//                "logout" -> {
//
//                }
//                "fail" -> {
//                    if (it.message?.isNotBlank() == true) {
//                        emit(FailureResponse(
//                                AppException(
//                                    status = Error.RESPONSE_ERROR.getKey(),
//                                    message = it.message
//                                )
//                            ))
//                    }else{
//                        emit(FailureResponse(ExceptionHandle.handleException(Throwable(""))))
//                    }
//                }
//                else -> {
//                    emit(CustomerResponse(it))
//                }
//            }
//        }.onFailure {
//            //失败回调
//            ExceptionHandle.handleException(it).message?.apply {
//                //打印错误消息
//                XLog.e(this)
//            }
//            emit(FailureResponse(ExceptionHandle.handleException(it)))
//        }
//    }

}