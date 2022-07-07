package com.lanyou.module_lyzc.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lanyou.lib_base.net.beans.zxc.ZXCOrderBean
import com.lanyou.module_lyzc.databinding.LayoutListItemZcBinding

class ZCOrderListAdapter :
    ListAdapter<ZXCOrderBean.RecordsBean, ZCOrderListAdapter.ListHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder =
        ListHolder(LayoutListItemZcBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListHolder(private var binding: LayoutListItemZcBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bean: ZXCOrderBean.RecordsBean) {
            val orderNo = "订单号：" + (bean.orderNo?:"--")
            binding.tvOrderNo.text = orderNo
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ZXCOrderBean.RecordsBean>() {
        override fun areItemsTheSame(
            oldItem: ZXCOrderBean.RecordsBean,
            newItem: ZXCOrderBean.RecordsBean
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ZXCOrderBean.RecordsBean,
            newItem: ZXCOrderBean.RecordsBean
        ): Boolean {
            return oldItem == newItem
        }

    }
}