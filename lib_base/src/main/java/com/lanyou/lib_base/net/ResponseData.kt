package com.lanyou.lib_base.net

/**
 * 数据请求的基础模型
 * {
 * "code": "ok", //失败则 fail
 * "message": "查询成功",
 * "data": {}
 * }
 */
open class BaseResponse<T> {
    var message: String? = null
    var code: String? = null
    var data: T? = null
}


class StartResponse<T> : BaseResponse<T>()

data class SuccessResponse<T>(var data1: T) : BaseResponse<T>()

class EmptyResponse<T> : BaseResponse<T>()

data class FailureResponse<T>(val exception: AppException) : BaseResponse<T>()

data class CustomerResponse<T>(val response: BaseResponse<T>) : BaseResponse<T>()