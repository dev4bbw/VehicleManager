package com.lanyou.lib_base.net.beans.lyzc

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ZCAuthBean(
    val pagination: PaginationBean?,
    val records: List<AuthRecordsBean>?
):Parcelable {
    @Parcelize
    data class PaginationBean(
        val total: Int?,
        val current: Int?,
        val pageSize: Int?
    ):Parcelable

    @Parcelize
    data class AuthRecordsBean(
        val id: String?,
        val account: String?,
        val mobile: String?,
        val spareMobile: String?,
        val email: String?,
        val imgPath: String?,
        val userName: String?,
        val realName: String?,
        val gender: String?,
        val hometown: String?,
        val industry: String?,
        val signature: String?,
        val hobby: String?,
        val age: String?,
        val status: String?,
        val memberType: String?,
        val userType: String?,
        val identityStatus: String?,//身份认证状态（0-未认证、1-已上传、2-已认证）
        val driverStatus: String?, //驾照认证（0-未认证、1-已上传、2-已认证）
        val isCustomerProtocol: String?,
        val isSecretProtocol: String?,
        val isAllowOrder: String?,//0-允许、1-不允许
        val balance: String?,
        val depositType: String?,//押金状态 0未缴纳 1已缴纳 2芝麻免押 3退压中 4.免押一半 5取消退芝麻免押中
        val deposit: String?,
        val couponsNum: String?,
        val isProtection: String?,
        val isStaff: String?,
        val lastLoginTime: String?,
        val createTime: String?,
        val driverPageUrl: String?,
        val driverSubPageUrl: String?,
        val driverNo: String?,//驾照证号
        val identityNo: String?,//身份证号
        val identityUrl: String?,//身份证正面url
        val identityBackUrl: String?,// 身份证背面url
        val formatTime: String?
    ):Parcelable
}