package com.lanyou.lib_base.net.services

import com.lanyou.lib_base.net.BaseResponse
import com.lanyou.lib_base.net.beans.common.LoginBean
import com.lanyou.lib_base.net.beans.common.UpdateInfoBean
import com.lanyou.lib_base.net.beans.common.UserInfoBean
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * 公用接口
 */
interface CommonApiServices {
    /**
     * 检查更新
     */
    @Headers("urlname:appinfologin")
    @POST("lycx-app-service/app/version/checkUpdate")
    suspend fun updateApp(@Body map: HashMap<String, String>): BaseResponse<UpdateInfoBean>

    /**
     * 登录
     */
    @Headers("urlname:appinfologin")
    @POST("lycxadmin/app/sysUser/login")
    suspend fun login(@Body body: RequestBody): BaseResponse<LoginBean>


    /**
     * 获取用户信息
     */
    @Headers("urlname:appinfologin")
    @POST("lycxadmin/app/sysUser/info")
    suspend fun getUserInfo(): BaseResponse<UserInfoBean>
}