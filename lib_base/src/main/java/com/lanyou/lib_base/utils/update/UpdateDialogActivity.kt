package com.lanyou.lib_base.utils.update

import com.alibaba.android.arouter.facade.annotation.Route
import com.lanyou.lib_base.ARouterConstant
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.lib_base.base.BaseViewModel
import com.lanyou.lib_base.databinding.ActivityUpdateDialogBinding

@Route(path = ARouterConstant.UPDATE)
class UpdateDialogActivity:BaseActivity<ActivityUpdateDialogBinding,BaseViewModel>() {
    override fun getViewBinding(): ActivityUpdateDialogBinding = ActivityUpdateDialogBinding.inflate(layoutInflater)

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun onBackPressed() {
        return
    }
}