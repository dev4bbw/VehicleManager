package com.lanyou.module_lyzc.main

import android.os.Process
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import com.lanyou.lib_base.ARouterConstant
import com.lanyou.lib_base.KeyConstant
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.lib_base.net.beans.common.UserInfoBean
import com.lanyou.lib_base.utils.ActivityController
import com.lanyou.lib_base.utils.ToastUtil
import com.lanyou.lib_base.utils.mmkvUtil
import com.lanyou.lib_base.utils.routerNavigate
import com.lanyou.module_lyzc.databinding.ActivityLyzcMainBinding

@Route(path = ARouterConstant.LYZC_MAIN)
class MainLYZCActivity : BaseActivity<ActivityLyzcMainBinding, ZCListViewModel>() {
    private var exitTime: Long = 0

    override fun getViewBinding(): ActivityLyzcMainBinding =
        ActivityLyzcMainBinding.inflate(layoutInflater)

    override fun initView() {
        binding.bottom.vpContent.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> ZCListFragment("1")
                    1 -> ZCListFragment("2")
                    else -> ZCListFragment("3")
                }
            }
        }
        TabLayoutMediator(
            binding.bottom.tab,
            binding.bottom.vpContent
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "近2日待取车"
                1 -> tab.text = "近2日待还车"
                else -> tab.text = "认证处理"
            }
        }.attach()

        binding.bottom.refresh.apply {
            setEnableLoadMore(false)
            setOnRefreshListener {
                viewModel.isMainRefreshing.value = true
                viewModel.isChildFinishRefresh.value = false
            }
        }
    }

    override fun initData() {
        viewModel.loadBubble()
        val userInfo =
            mmkvUtil.getParcelable(KeyConstant.USER_INFO, UserInfoBean::class.java) as UserInfoBean
        binding.left.tvAccount.text = userInfo.userInfo.userName
        binding.left.tvName.text = userInfo.userInfo.realName
    }

    override fun initListener() {
        viewModel.isChildFinishRefresh.observe(this) {
            if (it) {
                binding.bottom.refresh.finishRefresh()
                viewModel.isMainRefreshing.value = false
            }
        }
        initClick()
    }

    private fun initClick() {
        binding.top.imgOpen.setOnClickListener {
            binding.baseContainer.open()
        }

        binding.bottom.apply {
            rlOrder.setOnClickListener {}
            rlCarManage.setOnClickListener {}
            rlViolation.setOnClickListener {}
            rlRepair.setOnClickListener {}
            rlReimburse.setOnClickListener {  }
            rlAuth.setOnClickListener {}
        }

        binding.left.apply {
            llSet.setOnClickListener {
                routerNavigate(ARouterConstant.SETTING)
            }
            llIntegral.setOnClickListener { }
            llKnowledge.setOnClickListener { }
            llBusinessHour.setOnClickListener { }
            llCheck.setOnClickListener { }
        }
    }

    override fun onBackPressed() {
        val current = System.currentTimeMillis()
        if ( current - exitTime > 2000){
            exitTime = current
            ToastUtil.toastCustomer("再按一次退出")
        }else{
            ActivityController.finishAll()
            Process.killProcess(Process.myPid())
        }
    }
}