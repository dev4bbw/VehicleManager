package com.lanyou.lib_base.net.services

import com.lanyou.lib_base.net.BaseResponse
import com.lanyou.lib_base.net.beans.lyzc.ZCAuthBean
import com.lanyou.lib_base.net.beans.zxc.ZXCMainBubbleBean
import com.lanyou.lib_base.net.beans.zxc.ZXCOrderBean
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * 公用接口
 */
interface LYZCApiServices {

    /**
     * 首页气泡
     */
    @Headers("urlname:appinforent")
    @POST("lycxzcs/web/zcsOrder/zcWaitToHandleNum")
    suspend fun getZCBubble(): BaseResponse<ZXCMainBubbleBean>

    /**
     * 近两日订单
     */
    @Headers("urlname:appinforent")
    @POST("lycxzcs/web/zcsOrder/zcWaitToGetOrReturnVehicle")
    suspend fun getZCLastTwoDayOrder(@Body map:HashMap<String,String>): BaseResponse<ZXCOrderBean>

    /**
     * 首页认证任务列表
     */
    @Headers("urlname:appinforent")
    @POST("lycxcrm/customer/getZcDriverAuditList")
//    @POST("lycxcrm/customer/getDriverAuditList") //尊享车接口，调试用
    suspend fun getZCAuthList(@Body map:HashMap<String,String>): BaseResponse<ZCAuthBean>

}