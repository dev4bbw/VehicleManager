package com.lanyou.module_lyzc.vehiclemanage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lanyou.lib_base.base.BaseFragment
import com.lanyou.module_lyzc.databinding.FragmentZcVehiclemanageBinding

class ZCVehicleManageFragment(private val type:String) :
    BaseFragment<FragmentZcVehiclemanageBinding, VehicleManageViewModel>() {
    private val vm :VehicleManageViewModel by viewModels()
    private var currentVisible: Boolean = false

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentZcVehiclemanageBinding.inflate(inflater, container, false)

    override fun initViewModel() = vm

    override fun initData() {
    }

    override fun initView() {
    }

    override fun onResume() {
        super.onResume()
        currentVisible = true
        viewModel.loadZCManageList(1,type)
    }

    override fun onPause() {
        super.onPause()
        currentVisible = false
    }
}