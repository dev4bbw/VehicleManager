package com.lanyou.module_lyzc.vehiclemanage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.lanyou.lib_base.base.BaseActivity
import com.lanyou.module_lyzc.databinding.ActivityZcVehicleManageBinding

class VehicleManageActivity :
    BaseActivity<ActivityZcVehicleManageBinding, VehicleManageViewModel>() {
    override fun getViewBinding() = ActivityZcVehicleManageBinding.inflate(layoutInflater)

    override fun initView() {
        binding.vp.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 6

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> ZCVehicleManageFragment("-1")
                    1 -> ZCVehicleManageFragment("0")
                    2 -> ZCVehicleManageFragment("1")
                    3 -> ZCVehicleManageFragment("2")
                    4 -> ZCVehicleManageFragment("3")
                    5 -> ZCVehicleManageFragment("4")
                    else ->ZCVehicleManageFragment("-1")
                }
            }
        }

        TabLayoutMediator(
            binding.tab,
            binding.vp,
            false,
            false
        ) { tab, position ->
            tab.text = when(position){
                0-> "全部"
                1->"空闲"
                2->"已分配"
                3->"已取走"
                4->"维修中"
                5->"禁用"
                else->"全部"
            }

        }.attach()
    }

    override fun initData() {
    }

    override fun initListener() {
    }
}