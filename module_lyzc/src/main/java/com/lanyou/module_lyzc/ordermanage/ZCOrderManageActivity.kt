package com.lanyou.module_lyzc.ordermanage

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.lib_base.databinding.CustomtabNewTackItemBinding
import com.lanyou.lib_base.utils.StatusBarUtil
import com.lanyou.module_lyzc.R
import com.lanyou.module_lyzc.databinding.ActivityZcOrderBinding
import com.lanyou.module_lyzc.main.ZCListFragment
import com.lanyou.module_lyzc.main.ZCListViewModel
import java.lang.Exception

class ZCOrderManageActivity:BaseActivity<ActivityZcOrderBinding, ZCListViewModel>() {
    override fun getViewBinding(): ActivityZcOrderBinding = ActivityZcOrderBinding.inflate(layoutInflater)
    override fun initView() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.order_bar)

        binding.vp.adapter = object :FragmentStateAdapter(this){
            override fun getItemCount(): Int = 4

            override fun createFragment(position: Int)= when(position){
                   0 -> ZCListFragment("1")
                   1 -> ZCListFragment("2")
                   2 -> ZCListFragment("3")
                   else -> ZCListFragment("4")
            }

        }
        TabLayoutMediator(
            binding.tab,
            binding.vp,
            false,
            false
        ){tab,position->
            val tabBinding = CustomtabNewTackItemBinding.inflate(layoutInflater)
            tab.customView = tabBinding.root
            when(position){
                0->tabBinding.tabItemText.text = "近2日待取车"
                1->tabBinding.tabItemText.text = "近2日待还车"
                2->tabBinding.tabItemText.text = "异常订单"
                else ->tabBinding.tabItemText.text = "全部订单"
            }

        }.attach()

        binding.refresh.apply {
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
    }

    override fun initListener() {
        viewModel.bubbleBean.observe(this) { bean ->
            val takeCar = bean.waitToGetNum
            val returnCar = bean.waitToReturnNum
            val auth = bean.authenticationNum
//            binding.tab.getTabAt(0)?.orCreateBadge?.number = 199
            setBadge(switchBadge(takeCar), switchBadge(returnCar), switchBadge(auth))
        }
        viewModel.isChildFinishRefresh.observe(this) {
            if (it) {
                binding.refresh.finishRefresh()
                viewModel.isMainRefreshing.value = false
            }
        }
    }

    private fun setBadge(takeNum: Int = 0, returnNum: Int = 0, authNum: Int = 0) {
        val badgeTake =
            binding.tab.getTabAt(0)?.customView?.findViewById<TextView>(com.lanyou.lib_base.R.id.rv_num)
        badgeTake?.apply {
            visibility = if (takeNum <= 0) View.GONE else View.VISIBLE
            text = takeNum.toString()
        }
        val badgeReturn =
            binding.tab.getTabAt(1)?.customView?.findViewById<TextView>(com.lanyou.lib_base.R.id.rv_num)
        badgeReturn?.apply {
            visibility = if (returnNum <= 0) View.GONE else View.VISIBLE
            text = returnNum.toString()
        }
        val badgeAuth =
            binding.tab.getTabAt(2)?.customView?.findViewById<TextView>(com.lanyou.lib_base.R.id.rv_num)
        badgeAuth?.apply {
            visibility = if (authNum <= 0) View.GONE else View.VISIBLE
            text = authNum.toString()
        }
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
}