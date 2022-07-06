package com.lanyou.lib_base.net.beans.zxc

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ZXCMainBubbleBean(
    val waitToGetNum: String = "",
    val authenticationNum: String = "",
    val violationNum: String = "",
    val orderNum: String = "",
    val waitToReturnNum: String = ""
) : Parcelable