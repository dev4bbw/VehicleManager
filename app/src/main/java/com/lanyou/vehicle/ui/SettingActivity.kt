package com.lanyou.vehicle.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.lanyou.lib_base.ARouterConstant
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.lib_base.utils.logout
import com.lanyou.vehicle.databinding.ActivitySettingBinding
import com.lanyou.vehicle.viewmodel.SettingViewModel

@Route(path = ARouterConstant.SETTING)
class SettingActivity:BaseActivity<ActivitySettingBinding,SettingViewModel>() {
    override fun getViewBinding(): ActivitySettingBinding = ActivitySettingBinding.inflate(layoutInflater)

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {
        binding.apply {
            rlUpdate.setOnClickListener {  }
            rlAbout.setOnClickListener {  }
            rlCancellation.setOnClickListener {  }
            tvUse?.setOnClickListener {  }
            tvPrivate?.setOnClickListener {  }

            btLogout.setOnClickListener {
                logout()
            }
        }
    }
}