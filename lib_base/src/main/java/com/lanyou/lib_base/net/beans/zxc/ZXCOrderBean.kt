package com.lanyou.lib_base.net.beans.zxc

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ZXCOrderBean(
    var total: Int?,

    val size: Int?,

    val current: Int?,

    val optimizeCountSql: Boolean?,

    val hitCount: Boolean?,

    val searchCount: Boolean?,

    val pages: Int?,

    val records: List<RecordsBean>?,
) : Parcelable {

    @Parcelize
    data class RecordsBean(
        var customerName: String?,

        val id: String?,

        val vehicleID: String?,

        val outTradeNo: String?,

        val orderStatus: String?,

        val planUsingStartDate: String?,

        val planUsingEndDate: String?,

        val orderDays: String?,

        val orderType: String?,

        val phone: String?,

        val plateNumber: String?,

        val branchId: String?,

        val customerId: String?,

        val billingNodeDate: String?,

        val isBeenPerformed: String?,//"车管员是否已取车拍照 0 == 未有， 1 == 已有")

        val createDate: String?,//判断是否新订单

        val isTimeOut: String?,//超时未取车、超时未还车
        //=============================自营网点

        val planGetDate: String?, //自营网点计划取车时间

        val planReturnDate: String?,//自营网点计划还车时间

        val orderNo: String?//自营网点订单号
    ) : Parcelable

}
