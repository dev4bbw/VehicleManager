package com.lanyou.zxc.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import com.lanyou.lib_base.ARouterConstant
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.zxc.databinding.ActivityZxcMainBinding

@Route(path = ARouterConstant.ZXC_MAIN)
class MainZXCActivity : BaseActivity<ActivityZxcMainBinding, ListViewModel>() {
    override fun getViewBinding(): ActivityZxcMainBinding =
        ActivityZxcMainBinding.inflate(layoutInflater)

    override fun initView() {
        binding.bottom.vpContent.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3
            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0-> ListFragment("1")
                    1-> ListFragment("2")
                    else-> ListFragment("3")
                }
            }
        }
        TabLayoutMediator(
            binding.bottom.tab,
            binding.bottom.vpContent
        ) { tab, position ->
            when(position){
                0-> tab.text = "近2日待取车"
                1-> tab.text = "近2日待还车"
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
    }

    override fun initListener() {
        viewModel.isChildFinishRefresh.observe(this){
            if (it){
                binding.bottom.refresh.finishRefresh()
                viewModel.isMainRefreshing.value = false
            }
        }
        initClick()
    }

    private fun initClick() {
        binding.bottom.apply {
            rlOrder.setOnClickListener {
            }
            rlCarManage.setOnClickListener {
            }
            rlInventory.setOnClickListener {
            }
            rlViolation.setOnClickListener {
            }
            rlAuth.setOnClickListener {
            }
            rlRepair.setOnClickListener {
            }
            rlOverview.setOnClickListener {
            }

        }
    }
}