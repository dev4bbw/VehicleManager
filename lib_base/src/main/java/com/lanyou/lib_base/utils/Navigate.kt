package com.lanyou.lib_base.utils

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.lanyou.lib_base.ARouterConstant
import com.lanyou.lib_base.KeyConstant
import com.lanyou.lib_base.StatusConstant

fun routerNavigate(path: String, bundle: Bundle = Bundle()) {
    ARouter.getInstance()
        .build(path)
        .with(bundle)
        .navigation()
}

fun logout() {
    mmkvUtil.removeFromKey(KeyConstant.REQ_TOKEN)
    mmkvUtil.removeFromKey(KeyConstant.DEVICE_ID)
    mmkvUtil.removeFromKey(KeyConstant.BUSINESS_TYPE)
    mmkvUtil.put(StatusConstant.IS_LOGIN, false)
    ActivityController.finishAll()

    routerNavigate(ARouterConstant.LOGIN)

}