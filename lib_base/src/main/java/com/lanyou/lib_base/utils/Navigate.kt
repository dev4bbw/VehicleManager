package com.lanyou.lib_base.utils

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter

fun routerNavigate(path:String,bundle: Bundle = Bundle()){
    ARouter.getInstance()
        .build(path)
//        .with(bundle)
        .navigation()
}