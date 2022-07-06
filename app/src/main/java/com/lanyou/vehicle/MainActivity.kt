package com.lanyou.vehicle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.vehicle.databinding.ActivityMainBinding
import com.lanyou.vehicle.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {
    }

}