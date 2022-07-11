package com.lanyou.vehicle.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.lifecycle.coroutineScope
import com.lanyou.lib_base.ARouterConstant
import com.lanyou.lib_base.KeyConstant
import com.lanyou.lib_base.StatusConstant
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.lib_base.dialog.CommonDialog
import com.lanyou.lib_base.net.ExceptionHandle
import com.lanyou.lib_base.net.observeState
import com.lanyou.lib_base.utils.ToastUtil
import com.lanyou.lib_base.utils.mmkvUtil
import com.lanyou.lib_base.utils.routerNavigate
import com.lanyou.vehicle.MainActivity
import com.lanyou.vehicle.R
import com.lanyou.vehicle.databinding.ActivitySplashBinding
import com.lanyou.vehicle.viewmodel.SplashViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)
    override fun initView() {
    }

    override fun initData() {
        viewModel.updateData()
        viewModel.data.observe(this){
            if (it == null){
                toLogin()
            }else{
                routerNavigate(ARouterConstant.UPDATE)
//                CommonDialog.showConfirmDialog(
//                    context = this@SplashActivity,
//                    titleTx = it.title ?: "",
//                    contentTx = it.content ?: "",
//                    onlyConfirm = it.forceUpdate?:false,
//                    onCancelListener = object :CommonDialog.OnCancelListener{
//                        override fun onClick(dialog: AlertDialog) {
//                            toLogin()
//                        }
//                    },
//                    onConfirmListener = object : CommonDialog.OnConfirmListener {
//                        override fun onClick(dialog:AlertDialog) {
//                            toDownLoad()
//                        }
//                    }
//                )
            }
        }
    }

    private fun toDownLoad() {
    }

    override fun initListener() {
    }


    /**
     * 登录
     */
    private fun toLogin() {
        val isLogin = mmkvUtil.getBoolean(StatusConstant.IS_LOGIN,false)
        val  token = mmkvUtil.getString(KeyConstant.REQ_TOKEN)
        if (isLogin && token.isNotEmpty()){
            this@SplashActivity.finish()
            when (mmkvUtil.getInt(KeyConstant.BUSINESS_TYPE,0)){
                0-> toActivity(PickBusinessActivity::class.java)
                1-> routerNavigate(ARouterConstant.ZXC_MAIN)
                else -> routerNavigate(ARouterConstant.LYZC_MAIN)
            }
        }else{
            lifecycle.coroutineScope.launch {
                delay(50)
                this@SplashActivity.finish()
                toActivity(LoginActivity::class.java)
            }
        }
    }

}