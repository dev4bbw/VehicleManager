package com.lanyou.lib_base.net.beans.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * Created by EZ on 2017/8/5.
 */
@Parcelize
data class UpdateInfoBean(
     val createTime: String?,
     val updateTime: String?,
     val id: String?,
     val appType: String?,
     val versionNo: Int?,
     val versionName: String?,
     val title: String?,
     val content: String?,
     val forceUpdate: Boolean?,
     val enable: Int?,
     val platform: String?,
     val appletsId: String?,
     val downloadUrl: String?,
     val creater: String?,
     val updater: String?
):Parcelable
