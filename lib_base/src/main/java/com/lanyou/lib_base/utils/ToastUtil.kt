package com.lanyou.lib_base.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lanyou.lib_base.R
import com.lanyou.lib_base.base.APP
import com.lanyou.lib_base.databinding.CustomerTextToastBinding

/**
 * 吐司工具类
 *
 * @ClassName: ToastUtil
 * @Description: 包含: 测试用的系统自带的吐司, 自定义的吐司
 * @author feng
 * @date 2015-2-6
 * @see
 * @modify chenmignjia
 * @date 2016-10-27
 */
object ToastUtil {

    /**
     * 显示toast
     */
    fun toastCustomer(content: String?) {
        val toast = Toast(APP.getInstance())
        val view: View = CustomerTextToastBinding.inflate(LayoutInflater.from(APP.getInstance())).root
        toast.view= view
        val tv: TextView = view.findViewById<TextView>(R.id.tv_content)
        tv.text = content
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
    }

    /**
     * 显示toast
     */
    fun toastRegisterForbid() {
        val mToast = Toast(APP.getInstance())
        val view: View = LayoutInflater.from(APP.getInstance()).inflate(R.layout.customer_toast,null)
        mToast.view = view
        mToast.setGravity(Gravity.CENTER, 0, 0)
        mToast.duration = Toast.LENGTH_SHORT
        mToast.show()
    }
}