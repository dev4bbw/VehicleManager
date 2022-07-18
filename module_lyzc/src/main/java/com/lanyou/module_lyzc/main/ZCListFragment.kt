package com.lanyou.module_lyzc.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elvishew.xlog.XLog
import com.lanyou.lib_base.base.BaseFragment
import com.lanyou.lib_base.utils.DisplayUtil
import com.lanyou.lib_base.utils.SpaceItemDecoration
import com.lanyou.module_lyzc.databinding.FragmentListZcBinding
import kotlin.math.ceil

class ZCListFragment(private val type: String) :
    BaseFragment<FragmentListZcBinding, ZCListViewModel>() {
    private val vm: ZCListViewModel by activityViewModels()
    private var mPage: Int = 1
    private var currentVisible: Boolean = false
    private lateinit var mOrderAdapter: ZCOrderListAdapter
    private lateinit var mAuthAdapter: ZCAuthListAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListZcBinding = FragmentListZcBinding.inflate(inflater, container, false)

    override fun initViewModel(): ZCListViewModel = vm

    override fun initView() {

        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(SpaceItemDecoration(top = DisplayUtil.dp2px(requireActivity(), 10f)))
            mOrderAdapter = ZCOrderListAdapter()
            mAuthAdapter = ZCAuthListAdapter()

            adapter = if ("102" == type) {
                mAuthAdapter
            } else mOrderAdapter
        }

        binding.refresh.apply {
            setEnableRefresh(false)
            setOnLoadMoreListener {
                viewModel.isChildFinishRefresh.value = false
                mPage++
                if ("102" == type) viewModel.getAuthListData(mPage.toString(), type)
                else viewModel.getListData(mPage.toString(), type)
            }
        }
    }


    override fun initData() {
        binding.tvNoData.text = type
        viewModel.isMainRefreshing.observe(this) {
            if (it && currentVisible) {
                mPage = 1
                if ("102" == type) viewModel.getAuthListData(mPage.toString(), type)
                else viewModel.getListData(mPage.toString(), type)
            }
        }
        viewModel.isChildFinishRefresh.observe(this) {
            if (it && currentVisible)
                binding.refresh.finishLoadMore()
        }
        if ("102" == type) {
            viewModel.authListData.observe(this) {
                if (!currentVisible) return@observe
                mAuthAdapter.submitList(it.records)
                val maxPage = it.pagination?.total?.let { it1 -> getMaxPage(it1,20) }
                if (mPage >= maxPage!!) {
                    binding.refresh.finishLoadMoreWithNoMoreData()
                    binding.refresh.setEnableLoadMore(false)
                } else {
                    binding.refresh.setEnableLoadMore(true)
                }
            }
        } else {
            viewModel.listData.observe(this) {
                if (!currentVisible) return@observe
                mOrderAdapter.submitList(it.records)
                if (mPage >= it.pages!!) {
                    binding.refresh.finishLoadMoreWithNoMoreData()
                    binding.refresh.setEnableLoadMore(false)
                } else {
                    binding.refresh.setEnableLoadMore(true)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        XLog.tag("FragmentState").i("Fragment" + type + "可见")
        currentVisible = true

        mPage = 1
        if ("102" == type) viewModel.getAuthListData(mPage.toString(), type)
        else viewModel.getListData(mPage.toString(), type)
    }

    override fun onPause() {
        super.onPause()
        currentVisible = false
        XLog.tag("FragmentState").i("Fragment" + type + "不可见")
    }


}