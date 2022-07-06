package com.lanyou.lib_base.net.services

import com.lanyou.lib_base.net.BaseResponse
import com.lanyou.lib_base.net.beans.zxc.ZXCMainBubbleBean
import com.lanyou.lib_base.net.beans.zxc.ZXCOrderBean
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * 尊享车接口
 */
interface ZXCApiServices {

    /**
     * 首页气泡
     */
    @Headers("urlname:appinforent")
    @POST("lycxoms/waitToHandleNum")
    suspend fun getBubble():BaseResponse<ZXCMainBubbleBean>

    /**
     * 近两日订单
     */
    @Headers("urlname:appinforent")
    @POST("lycxoms/waitToGetOrReturnVehicle")
    suspend fun getLastTwoDayOrder(@Body map:HashMap<String,String>):BaseResponse<ZXCOrderBean>
}