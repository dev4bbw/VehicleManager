package com.lanyou.lib_base

object KeyConstant {
    const val BASE_APP_URL = "baseAPPurl"
    const val BASE_H5_URL = "baseH5url"
    const val REQ_TOKEN = "req_token"
    const val DEVICE_ID = "device_id"

    const val USER_INFO = "user_info"
    const val LOGIN_ACCOUNT = "login_account"
    const val LOGIN_PSW = "login_psw"

    const val BUSINESS_TYPE = "business_type"   //1-尊享车 2-联友租车

    const val DOWNLOAD_ID = "download_id"

}

object StatusConstant{
    const val IS_LOGIN = "isLogin"
}

object ConfigConstant{
    const val NET_TIME_OUT = 15
}

object ARouterConstant{
    //公共部分
    //更新
    const val UPDATE = "/base/UpdateDialogActivity"
    //设置
    const val LOGIN ="/common/LoginActivity"

    const val SETTING ="/common/SettingActivity"

    //尊享车
    const val ZXC_MAIN = "/module_zxc/MainZXCActivity"

    //联友租车
    const val LYZC_MAIN = "/module_lyzc/MainLYZCActivity"
}
