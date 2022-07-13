package com.lanyou.lib_base.utils.update

import android.Manifest
import android.app.DownloadManager
import android.content.Intent
import android.content.IntentFilter
import android.os.Process
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lanyou.lib_base.ARouterConstant
import com.lanyou.lib_base.R
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.lib_base.base.BaseViewModel
import com.lanyou.lib_base.databinding.ActivityUpdateDialogBinding
import com.lanyou.lib_base.utils.ActivityController
import com.lanyou.lib_base.utils.ToastUtil

@Route(path = ARouterConstant.UPDATE)
class UpdateDialogActivity : BaseActivity<ActivityUpdateDialogBinding, BaseViewModel>() {

    private var exitTime: Long = 0

    @JvmField
    @Autowired(name = "IS_FORCE")
    var isForce: Boolean = false

    @JvmField
    @Autowired(name = "DOWN_URL")
    var downUrl: String = ""


    override fun getViewBinding(): ActivityUpdateDialogBinding =
        ActivityUpdateDialogBinding.inflate(layoutInflater)

    override fun initView() {
        ARouter.getInstance().inject(this)

        if (isForce) {
            binding.apply {
                cancel.visibility = View.GONE
                vView.visibility = View.GONE
                confirm.background = getDrawable(R.drawable.bg_bottom_radius)
            }
        }
    }

    override fun initData() {
        startService(Intent(this,UpdateService::class.java))
    }

    override fun initListener() {
        binding.apply {
            cancel.setOnClickListener {
                onBackPressed()
            }
            confirm.setOnClickListener {
                checkPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) {
                    when {
                        it -> {
                            DownloadUtil.download(this@UpdateDialogActivity,downUrl)
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (isForce) {
            val current = System.currentTimeMillis()
            if (current - exitTime > 2000) {
                exitTime = current
                ToastUtil.toastCustomer("再按一次退出")
            } else {
                ActivityController.finishAll()
                Process.killProcess(Process.myPid())
            }
        } else {
            setResult(1000)
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}