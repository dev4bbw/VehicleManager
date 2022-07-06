package com.lanyou.vehicle.repository

import com.lanyou.lib_base.base.APP
import com.lanyou.lib_base.base.BaseRepository
import com.lanyou.lib_base.net.BaseResponse
import com.lanyou.lib_base.net.beans.common.LoginBean
import com.lanyou.lib_base.utils.AppUtil
import com.lanyou.lib_base.utils.md5
import okhttp3.MultipartBody

class LoginRepository : BaseRepository() {

    suspend fun login(username: String, password: String): BaseResponse<LoginBean> {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("username", username)
        builder.addFormDataPart("password", md5(password))
        builder.addFormDataPart("platForm", "android")
        builder.addFormDataPart("systemNumber", "android" + AppUtil.systemVersion)
        builder.addFormDataPart("pushId", "")
        builder.addFormDataPart("version", AppUtil.getVersionName(APP.getInstance()))
        builder.addFormDataPart("brand", AppUtil.deviceBrand + " " + AppUtil.systemModel)
        builder.addFormDataPart("source", "2")
        val aMapLocation = ""
        if (aMapLocation.isNotBlank()) {
            builder.addFormDataPart("cityName", "2")
            builder.addFormDataPart("cityCode", "2")
            builder.addFormDataPart("adcode", "2")
            builder.addFormDataPart("lon", "2")
            builder.addFormDataPart("lat", "2")
        }
        return commonApiServices.login(builder.build())
    }

    suspend fun getUserInfo()= commonApiServices.getUserInfo()
}