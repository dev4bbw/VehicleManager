package com.lanyou.vehicle.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.lifecycle.coroutineScope
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.lib_base.dialog.CommonDialog
import com.lanyou.lib_base.net.ExceptionHandle
import com.lanyou.lib_base.net.observeState
import com.lanyou.lib_base.utils.ToastUtil
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
                CommonDialog.showConfirmDialog(
                    context = this@SplashActivity,
                    titleTx = it.title ?: "",
                    contentTx = it.content ?: "",
                    onlyConfirm = it.forceUpdate?:false,
                    onCancelListener = object :CommonDialog.OnCancelListener{
                        override fun onClick(dialog: AlertDialog) {
                            dialog.dismiss()
                        }
                    },
                    onConfirmListener = object : CommonDialog.OnConfirmListener {
                        override fun onClick(dialog:AlertDialog) {
                            dialog.dismiss()
                            toLogin()
                        }
                    }
                )
            }
        }
    }

    override fun initListener() {
    }

    /**
     * 首页
     */
    private fun toMain() {
        lifecycle.coroutineScope.launch {
            delay(50)
            this@SplashActivity.finish()
            toActivity(MainActivity::class.java)
        }
    }

    /**
     * 登录
     */
    private fun toLogin() {
        lifecycle.coroutineScope.launch {
            delay(50)
            this@SplashActivity.finish()
            toActivity(LoginActivity::class.java)
        }
    }

}