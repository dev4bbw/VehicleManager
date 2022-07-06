package com.lanyou.lib_base.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.lanyou.lib_base.R
import com.lanyou.lib_base.dialog.ProgressDialog
import com.lanyou.lib_base.utils.StatusBarUtil
import com.lanyou.lib_base.utils.ToastUtil
import java.lang.reflect.ParameterizedType
import kotlin.math.abs

abstract class BaseActivity<T : ViewBinding, VM : BaseViewModel> :
    AppCompatActivity() {
    lateinit var binding: T
    protected lateinit var viewModel: VM

    protected var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //根据状态栏颜色来决定状态栏文字用黑色还是白色
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)

        binding = getViewBinding()
        viewModel = ViewModelProvider(this)[getViewModelClass()]
        setContentView(binding.root)
        onViewCreated()
        progressDialog = ProgressDialog.Builder(this).noClose().get()
    }

    //    abstract fun getViewModelClass():Class<VM>
    private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1]
        return type as Class<VM>
    }

    abstract fun getViewBinding(): T

    fun onViewCreated() {

        initView()

        initData()

        initListener()

        viewModel.uiChangeLiveData.showDialogEvent.observe(this) {
            showProgress()
        }

        viewModel.uiChangeLiveData.dismissDialogEvent.observe(this) {
            dismissProgress()
        }
        viewModel.uiChangeLiveData.toastEvent.observe(this) {
            ToastUtil.toastCustomer(it)
        }
        viewModel.uiChangeLiveData.uiMessageEvent.observe(this) {
        }

    }

    abstract fun initView()
    abstract fun initData()
    abstract fun initListener()

    override fun onDestroy() {
        super.onDestroy()
        progressDialog?.cancel()
    }

    fun showProgress() {
        if (progressDialog != null) {
            progressDialog?.showDialog()
        } else {
            progressDialog = ProgressDialog.newBuilder(this)
                .noClose()
                .get()
            progressDialog?.showDialog()
        }
    }

    fun dismissProgress() {
        progressDialog?.dismiss()
    }

    fun <T : BaseActivity<*, *>> toActivity(clazz: Class<T>) {
        val intent = Intent(this, clazz)
        this.startActivity(intent)
    }

    /**
     * 防止连点的方法
     */
    private var mClickTemp: Long = 0
    fun isClickBuffering(): Boolean {
        val duration = System.currentTimeMillis() - mClickTemp
        mClickTemp =System.currentTimeMillis()
        return abs(duration) <= 1000
    }

}

