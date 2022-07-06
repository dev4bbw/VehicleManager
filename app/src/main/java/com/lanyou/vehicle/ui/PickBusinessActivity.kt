package com.lanyou.vehicle.ui

import android.view.View
import com.lanyou.lib_base.ARouterConstant
import com.lanyou.lib_base.KeyConstant
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.lib_base.base.BaseViewModel
import com.lanyou.lib_base.net.beans.common.UserInfoBean
import com.lanyou.lib_base.utils.mmkvUtil
import com.lanyou.lib_base.utils.routerNavigate
import com.lanyou.vehicle.databinding.ActivityPickBusinessBinding

class PickBusinessActivity : BaseActivity<ActivityPickBusinessBinding, BaseViewModel>() {
    override fun getViewBinding(): ActivityPickBusinessBinding =
        ActivityPickBusinessBinding.inflate(layoutInflater)

    override fun initView() {
    }

    override fun initData() {
        val userInfo =
            mmkvUtil.getParcelable(KeyConstant.USER_INFO, UserInfoBean::class.java) as UserInfoBean
        val roleList = userInfo.carUserRole
        if (roleList.isNullOrEmpty()) {
            binding.clBusiness.visibility = View.GONE
            binding.tvSet.text = "暂无业务权限"
        } else if (roleList.size > 1) {
            binding.llZxc.visibility = View.VISIBLE
            binding.llLyzc.visibility = View.VISIBLE
        } else {
            if ("CDZ" == roleList[0]){
                   //尊享车
                routerNavigate(ARouterConstant.ZXC_MAIN)
            }else if ("ZYD" == roleList[0]){
                routerNavigate(ARouterConstant.LYZC_MAIN)
            }
        }
    }

    override fun initListener() {
        binding.llZxc.setOnClickListener {
            routerNavigate(ARouterConstant.ZXC_MAIN)
        }
        binding.llLyzc.setOnClickListener {
            routerNavigate(ARouterConstant.LYZC_MAIN)
        }
    }
}