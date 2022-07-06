package com.lanyou.lib_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.lanyou.lib_base.dialog.ProgressDialog
import com.lanyou.lib_base.utils.ToastUtil
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<V : ViewBinding, VM : BaseViewModel> : Fragment() {
    protected lateinit var binding: V
    protected lateinit var viewModel: VM
    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater,container)
        viewModel = initViewModel()
        return binding.root
    }

    abstract fun getViewBinding(inflater: LayoutInflater,
                                container: ViewGroup?): V

    //这里交给Fragment实现是因为不同情况有不同的初始化方法
    abstract fun initViewModel():VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog.Builder(requireContext()).noClose().get()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initData()

        viewModel.uiChangeLiveData.showDialogEvent.observe(viewLifecycleOwner) {
            showProgress()
        }

        viewModel.uiChangeLiveData.dismissDialogEvent.observe(viewLifecycleOwner) {
            dismissProgress()
        }
        viewModel.uiChangeLiveData.toastEvent.observe(viewLifecycleOwner) {
            ToastUtil.toastCustomer(it)
        }
        viewModel.uiChangeLiveData.uiMessageEvent.observe(viewLifecycleOwner) {
        }

    }


    abstract fun initData()

    abstract fun initView()

    fun showProgress() {
        if (progressDialog != null) {
            progressDialog?.showDialog()
        } else {
            progressDialog = ProgressDialog.newBuilder(requireActivity())
                .noClose()
                .get()
            progressDialog?.showDialog()
        }
    }

    fun dismissProgress() {
        progressDialog?.dismiss()
    }
}