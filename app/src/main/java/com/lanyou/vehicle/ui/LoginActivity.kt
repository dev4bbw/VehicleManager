package com.lanyou.vehicle.ui

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.lanyou.lib_base.ARouterConstant
import com.lanyou.lib_base.BuildConfig
import com.lanyou.lib_base.KeyConstant
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.lib_base.utils.ToastUtil
import com.lanyou.lib_base.utils.mmkvUtil
import com.lanyou.vehicle.databinding.ActivityLoginBinding
import com.lanyou.vehicle.viewmodel.LoginViewModel

@Route(path = ARouterConstant.LOGIN)
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun initView() {

    }

    override fun initData() {
        val account = mmkvUtil.getString(KeyConstant.LOGIN_ACCOUNT,"")
        var psw = ""
        if (BuildConfig.DEBUG){
            psw = mmkvUtil.getString(KeyConstant.LOGIN_PSW,"")
        }
        binding.loginAccount.setText(account)
        binding.loginPwd.setText(psw)

        viewModel.userInfo.observe(this){
            toActivity(PickBusinessActivity::class.java)
        }
    }

    override fun initListener() {
        binding.cbAgree.setOnCheckedChangeListener { _, isChecked ->
            binding.login.isEnabled = isChecked
        }
        binding.basic.setOnClickListener {
            //切环境
        }
        binding.llVerifyCode.setOnClickListener {
            binding.loginAccount.text.clear()
            binding.loginPwd.setText("")
            showCodeView(true)
        }
        binding.ivBack.setOnClickListener {
            showCodeView(false)
        }
        binding.getCode.setOnClickListener {
            ToastUtil.toastRegisterForbid()
        }

        binding.protocolPrivate.setOnClickListener { }
        binding.protocolUse.setOnClickListener { }

        binding.login.setOnClickListener {
            if(binding.clAccount.visibility == View.VISIBLE){
                if (!isAccountFill()) {
                    return@setOnClickListener
                }
                viewModel.login(binding.loginAccount.text.toString(), binding.loginPwd.pwd)
            }else{
                ToastUtil.toastRegisterForbid()
            }

        }
    }

    override fun onBackPressed() {
        if (binding.ivBack.visibility == View.VISIBLE) {
            showCodeView(false)
        } else {
            super.onBackPressed()
        }
    }

    private fun isAccountFill(): Boolean {
        return if (binding.clAccount.visibility == View.VISIBLE) {
            if (binding.loginAccount.text.toString().isBlank()) {
                ToastUtil.toastCustomer("请输入账号")
                false
            } else if (binding.loginPwd.pwd.isBlank()) {
                ToastUtil.toastCustomer("请输入密码")
                false
            } else
                true
        } else {
            if (binding.loginMobile.text.toString().isBlank()) {
                ToastUtil.toastCustomer("请输入手机号")
                false
            } else if (binding.loginCode.text.toString().isBlank()) {
                ToastUtil.toastCustomer("请输入验证码")
                false
            } else
                true
        }
    }

    private fun showCodeView(showCodeInput: Boolean) {
        binding.ivBack.visibility = if (showCodeInput) View.VISIBLE else View.INVISIBLE
        binding.clCode.visibility = if (showCodeInput) View.VISIBLE else View.GONE
        binding.llVerifyCode.visibility = if (showCodeInput) View.GONE else View.VISIBLE
        binding.clAccount.visibility = if (showCodeInput) View.GONE else View.VISIBLE
    }
}