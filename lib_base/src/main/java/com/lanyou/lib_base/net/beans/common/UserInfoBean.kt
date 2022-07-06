package com.lanyou.lib_base.net.beans.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserInfoBean(
    val userInfo: UserInfo,
    val CDZ: List<CDZItem>?,
    val carUserRole: List<String>?
): Parcelable

@Parcelize
data class UserInfo(
    val gender: String?,
    val loginApp: String?,
    val telephone: String?,
    val avatar: String?,
    val userName: String?,
    val lastLoginIp: String?,
    val realName: String?,
    val lastLoginTime: String?,
    val createTime: String?,
    val parkIds: List<String>?,
    val groupIds: List<String>?,
    val id: String?,
    val branchIds: List<String>?,
    val email: String?,
    val status: String?
):Parcelable
@Parcelize
data class CDZItem(
    val permissionId: String?,
    val permissionPath: String?,
    val plate: String?,
    val permissionParent: String?,
    val permissionName: String?
):Parcelable