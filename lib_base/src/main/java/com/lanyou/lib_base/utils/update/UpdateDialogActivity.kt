package com.lanyou.lib_base.utils.update

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Process
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lanyou.lib_base.ARouterConstant
import com.lanyou.lib_base.R
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.lib_base.base.BaseViewModel
import com.lanyou.lib_base.databinding.ActivityUpdateDialogBinding
import com.lanyou.lib_base.net.beans.common.UpdateInfoBean
import com.lanyou.lib_base.utils.ActivityController
import com.lanyou.lib_base.utils.AppUtil
import com.lanyou.lib_base.utils.ToastUtil
import java.io.File

@Route(path = ARouterConstant.UPDATE)
class UpdateDialogActivity : BaseActivity<ActivityUpdateDialogBinding, BaseViewModel>() {

    private var exitTime: Long = 0
    private var srcPath = ""
    @JvmField
    @Autowired(name = "DOWN_BEAN")
    var bean: UpdateInfoBean? = null


    override fun getViewBinding(): ActivityUpdateDialogBinding =
        ActivityUpdateDialogBinding.inflate(layoutInflater)

    override fun initView() {
        ARouter.getInstance().inject(this)
        if (bean == null) {
            finish()
        }
        if (bean?.forceUpdate == true) {
            binding.apply {
                cancel.visibility = View.GONE
                vView.visibility = View.GONE
                confirm.background = getDrawable(R.drawable.bg_bottom_radius)
            }
        }
        binding.title.text = bean?.title
        binding.version.text = bean?.versionName
        binding.content.text = bean?.content
    }

    override fun initData() {
        startService(Intent(this, UpdateService::class.java))
    }

    override fun initListener() {
        binding.apply {
            cancel.setOnClickListener {
                DownloadUtil.cancelDown(this@UpdateDialogActivity)
                onBackPressed()
            }
            confirm.setOnClickListener {
                checkPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) {
                    when {
                        it -> {
                            val url = bean?.downloadUrl ?: ""
                            DownloadUtil.download(this@UpdateDialogActivity, url) {
                                downloadStart {
                                    binding.llProgress.visibility = View.VISIBLE
                                    binding.content.visibility = View.GONE
                                    binding.confirm.text = getString(R.string.update_downloading)
                                    binding.confirm.isEnabled = false
                                }
                                downloading { progress ->
                                    binding.progress.setProgress(progress)
                                    binding.confirm.text = getString(R.string.update_downloading)
                                }
                                downloadComplete { path ->
                                    binding.llProgress.visibility = View.GONE
                                    binding.content.visibility = View.VISIBLE
                                    binding.confirm.text = getString(R.string.update_confirm)
                                    path?.let {
                                        srcPath = path
                                        if (hasInstallPermission()){
                                            startInstall(path)
                                        }
                                    }
                                    binding.confirm.isEnabled = true
                                }
                                downloadFail {
                                    binding.llProgress.visibility = View.GONE
                                    binding.content.visibility = View.VISIBLE
                                    binding.confirm.text = getString(R.string.update_confirm)
                                    binding.confirm.isEnabled = true
                                    ToastUtil.toastCustomer("下载失败")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (bean?.forceUpdate == true) {
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

    private fun startInstall(path:String){
        val file = File(path)
        if (file.isFile and  file.exists()) {
            AppUtil.installApk(this@UpdateDialogActivity, file)
        } else {
            ToastUtil.toastCustomer("下载失败")
        }
    }

    private fun hasInstallPermission(): Boolean {
        val haveInstallPermission =
            this@UpdateDialogActivity.packageManager.canRequestPackageInstalls()
        return if (!haveInstallPermission) {
            //TODO 待后续完善，这种方式可能存在其他意外情况
            val parse = Uri.parse("package:" + this@UpdateDialogActivity.packageName)
            val intent = Intent(
                Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                parse
            )
            ActivityCompat.startActivityForResult(
                this@UpdateDialogActivity, intent,
                AppUtil.REQUEST_CODE_INSTALL_APK_PERMISSION, null
            )
            false
        } else {
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppUtil.REQUEST_CODE_INSTALL_APK_PERMISSION && resultCode == RESULT_OK) {
            //获取APK安装权限成功后返回，点击安装apk
            startInstall(srcPath)
        }
    }
}