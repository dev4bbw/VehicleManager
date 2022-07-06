package com.lanyou.lib_base.net.beans.common


/**
 * Created by EZ on 2017/8/5.
 */
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
     val appletsId: Any?,
     val downloadUrl: String?,
     val creater: String?,
     val updater: String?
)
