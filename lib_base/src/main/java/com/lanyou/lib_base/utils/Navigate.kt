package com.lanyou.lib_base.utils

import android.app.Activity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.lanyou.lib_base.ARouterConstant
import com.lanyou.lib_base.KeyConstant
import com.lanyou.lib_base.StatusConstant

/**
 * 通用方法
 */
fun routerNavigate(path: String) {
    ARouter.getInstance()
        .build(path)
        .navigation()
}
fun routerNavUpdate(context: Activity, path: String, isForce:Boolean, downUrl: String,requestCode:Int) {
    ARouter.getInstance()
        .build(path)
        .withBoolean("IS_FORCE",isForce)
        .withString("DOWN_URL",downUrl)
        .navigation(context,requestCode)
}

fun logout() {
    mmkvUtil.removeFromKey(KeyConstant.REQ_TOKEN)
    mmkvUtil.removeFromKey(KeyConstant.DEVICE_ID)
    mmkvUtil.removeFromKey(KeyConstant.BUSINESS_TYPE)
    mmkvUtil.put(StatusConstant.IS_LOGIN, false)
    ActivityController.finishAll()

    routerNavigate(ARouterConstant.LOGIN)

}