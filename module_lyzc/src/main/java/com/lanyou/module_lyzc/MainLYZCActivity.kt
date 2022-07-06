package com.lanyou.module_lyzc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lanyou.lib_base.ARouterConstant
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.lib_base.base.BaseViewModel
import com.lanyou.module_lyzc.databinding.ActivityLyzcMainBinding

@Route(path = ARouterConstant.LYZC_MAIN)
class MainLYZCActivity : BaseActivity<ActivityLyzcMainBinding, BaseViewModel>() {
    override fun getViewBinding(): ActivityLyzcMainBinding =
        ActivityLyzcMainBinding.inflate(layoutInflater)

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {
    }
}