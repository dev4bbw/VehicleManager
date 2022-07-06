package com.lanyou.zxc.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elvishew.xlog.XLog
import com.lanyou.lib_base.base.BaseFragment
import com.lanyou.lib_base.utils.DisplayUtil
import com.lanyou.lib_base.utils.SpaceItemDecoration
import com.lanyou.zxc.databinding.FragmentListBinding

class ListFragment(private val type: String) : BaseFragment<FragmentListBinding, ListViewModel>() {
    private val vm: ListViewModel by activityViewModels()
    private var mPage: Int = 1
    private var currentVisible:Boolean = false
    private lateinit var mAdapter: OrderListAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListBinding = FragmentListBinding.inflate(inflater, container, false)

    override fun initViewModel(): ListViewModel = vm

    override fun initView() {

        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(SpaceItemDecoration(top = DisplayUtil.dp2px(requireActivity(), 10f)))
            mAdapter = OrderListAdapter()
            adapter = mAdapter
        }

        binding.refresh.apply {
            setEnableRefresh(false)
            setOnLoadMoreListener {
                viewModel.isChildFinishRefresh.value = false
                mPage++
                viewModel.getListData(mPage.toString(), type)
            }
        }
    }


    override fun initData() {
        binding.tvNoData.text = type
        viewModel.isMainRefreshing.observe(this) {
            if (it && currentVisible) {
                mPage = 1
                viewModel.getListData(mPage.toString(), type)
            }
        }
        viewModel.isChildFinishRefresh.observe(this) {
            if (it && currentVisible)
                binding.refresh.finishLoadMore()
        }
        viewModel.listData.observe(this) {
            if (!currentVisible)return@observe

            mAdapter.submitList(it.records)
            if (mPage >= it.pages!!) {
                binding.refresh.finishLoadMoreWithNoMoreData()
                binding.refresh.setEnableLoadMore(false)
            } else {
                binding.refresh.setEnableLoadMore(true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        XLog.tag("FragmentState").i("Fragment" + type + "可见")
        currentVisible = true

        mPage = 1
        viewModel.getListData(mPage.toString(), type)
    }

    override fun onPause() {
        super.onPause()
        currentVisible = false

        XLog.tag("FragmentState").i("Fragment" + type + "不可见")
    }

}