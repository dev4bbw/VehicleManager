package com.lanyou.zxc.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lanyou.lib_base.net.beans.zxc.ZXCOrderBean
import com.lanyou.zxc.databinding.LayoutListItemBinding

class OrderListAdapter :
    ListAdapter<ZXCOrderBean.RecordsBean, OrderListAdapter.ListHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder =
        ListHolder(LayoutListItemBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListHolder(private var binding: LayoutListItemBinding) :
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