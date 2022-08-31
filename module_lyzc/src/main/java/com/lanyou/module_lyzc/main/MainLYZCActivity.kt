package com.lanyou.module_lyzc.main

import android.os.Process
import android.view.View
import android.widget.TextView
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
import com.lanyou.lib_base.databinding.CustomtabNewTackItemBinding
import com.lanyou.module_lyzc.databinding.ActivityLyzcMainBinding
import com.lanyou.module_lyzc.ordermanage.ZCOrderManageActivity
import com.lanyou.module_lyzc.vehiclemanage.VehicleManageActivity
import java.lang.Exception

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
                    else -> ZCListFragment("102")
                }
            }
        }
        TabLayoutMediator(
            binding.bottom.tab,
            binding.bottom.vpContent,
            false,
            false
        ) { tab, position ->
            val tabBinding = CustomtabNewTackItemBinding.inflate(layoutInflater)
            tab.customView = tabBinding.root
            when (position) {
                0 -> tabBinding.tabItemText.text = "近2日待取车"
                1 -> tabBinding.tabItemText.text = "近2日待还车"
                else -> tabBinding.tabItemText.text = "认证处理"
            }
        }.attach()

        binding.bottom.refresh.apply {
            setEnableLoadMore(false)
            setOnRefreshListener {
                viewModel.loadBubble()
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
        viewModel.bubbleBean.observe(this) { bean ->
            val takeCar = bean.waitToGetNum
            val returnCar = bean.waitToReturnNum
            val auth = bean.authenticationNum
//            binding.bottom.tab.getTabAt(0)?.orCreateBadge?.number = 199
            setBadge(switchBadge(takeCar), switchBadge(returnCar), switchBadge(auth))
        }
        viewModel.listData.observe(this) {

        }

        viewModel.isChildFinishRefresh.observe(this) {
            if (it) {
                binding.bottom.refresh.finishRefresh()
                viewModel.isMainRefreshing.value = false
            }
        }
        initClick()
    }

    private fun switchBadge(src: String): Int {
        return try {
            val result = src.toInt()
            if (result > 99) {
                99
            } else {
                result
            }
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    private fun setBadge(takeNum: Int = 0, returnNum: Int = 0, authNum: Int = 0) {
        val badgeTake =
            binding.bottom.tab.getTabAt(0)?.customView?.findViewById<TextView>(com.lanyou.lib_base.R.id.rv_num)
        badgeTake?.apply {
            visibility = if (takeNum <= 0) View.GONE else View.VISIBLE
            text = takeNum.toString()
        }
        val badgeReturn =
            binding.bottom.tab.getTabAt(1)?.customView?.findViewById<TextView>(com.lanyou.lib_base.R.id.rv_num)
        badgeReturn?.apply {
            visibility = if (returnNum <= 0) View.GONE else View.VISIBLE
            text = returnNum.toString()
        }
        val badgeAuth =
            binding.bottom.tab.getTabAt(2)?.customView?.findViewById<TextView>(com.lanyou.lib_base.R.id.rv_num)
        badgeAuth?.apply {
            visibility = if (authNum <= 0) View.GONE else View.VISIBLE
            text = authNum.toString()
        }
    }

    private fun initClick() {
        binding.top.imgOpen.setOnClickListener {
            binding.baseContainer.open()
        }

        binding.bottom.apply {
            rlOrder.setOnClickListener {
                toActivity(ZCOrderManageActivity::class.java)
            }
            rlCarManage.setOnClickListener {
                toActivity(VehicleManageActivity::class.java)
            }
            rlViolation.setOnClickListener {}
            rlRepair.setOnClickListener {}
            rlReimburse.setOnClickListener { }
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
        if (current - exitTime > 2000) {
            exitTime = current
            ToastUtil.toastCustomer("再按一次退出")
        } else {
            ActivityController.finishAll()
            Process.killProcess(Process.myPid())
        }
    }
}