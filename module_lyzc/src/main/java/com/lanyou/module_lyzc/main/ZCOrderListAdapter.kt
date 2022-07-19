package com.lanyou.module_lyzc.main

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lanyou.lib_base.net.beans.zxc.ZXCOrderBean
import com.lanyou.module_lyzc.databinding.LayoutListItemZcBinding

class ZCOrderListAdapter :
    ListAdapter<ZXCOrderBean.RecordsBean, ZCOrderListAdapter.ListHolder>(DiffCallback) {
    var buttonClick: ((itemData: ZXCOrderBean.RecordsBean, action: Int, position: Int) -> Unit)? =
        null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder =
        ListHolder(LayoutListItemZcBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(position,getItem(position),buttonClick)
    }

    class ListHolder(private var binding: LayoutListItemZcBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            position: Int,
            bean: ZXCOrderBean.RecordsBean,
            buttonClick: ((itemData: ZXCOrderBean.RecordsBean, action: Int, position: Int) -> Unit)?) {

            val orderNo = "订单号：" + (bean.orderNo ?: "--")
            val phone = bean.phone ?: "--"
            val plate = bean.plateNumber ?: "--"
            var getDate = bean.planGetDate ?: "--"
            var returnDate = bean.planReturnDate ?: "--"
            val orderDay = bean.orderDays ?: "--"

            val name = bean.planReturnDate ?: "--"

            binding.tvOrderNo.text = orderNo
            binding.tvUser.text = " $name $phone"
            binding.tvPlate.text = plate

            if (getDate.length > 8) {
                getDate = getDate.substring(5, getDate.length - 3)
            }

            if (returnDate.length > 8) {
                returnDate = returnDate.substring(5, returnDate.length - 3)
            }
            binding.tvTime.text = "$getDate-$returnDate($orderDay" + "天)"

            setStatus(
                bean.orderStatus!!,
                bean.isBeenPerformed!!,
                bean.isTimeOut,
                binding
            )

            itemView.setOnClickListener {
                buttonClick?.invoke(bean, ACTION_DETAIL,position)
            }
            binding.tvReturn.setOnClickListener {
                buttonClick?.invoke(bean, ACTION_RETURN,position)
            }
            binding.tvExchange.setOnClickListener {
                buttonClick?.invoke(bean, ACTION_EXCHANGE,position)
            }
            binding.tvPrepare.setOnClickListener {
                buttonClick?.invoke(bean, ACTION_PREPARE,position)
            }
            binding.tvAllocation.setOnClickListener {
                buttonClick?.invoke(bean, ACTION_ALLOCATION,position)
            }
            binding.tvPickup.setOnClickListener {
                buttonClick?.invoke(bean, ACTION_PICKUP,position)
            }

        }

        private fun setStatus(
            orderStatus: String,
            isBeenPerformed: String,
            timeOut: String?,
            binding: LayoutListItemZcBinding
        ) {
            binding.tvReturn.visibility = View.GONE
            binding.tvExchange.visibility = View.GONE
            binding.tvPrepare.visibility = View.GONE
            binding.tvAllocation.visibility = View.GONE
            binding.tvPickup.visibility = View.GONE
            binding.tvErr.visibility = View.GONE
            binding.tvStatus.text =
                if (TextUtils.isEmpty(orderStatus)) {
                    ""
                } else when (orderStatus) {
                    "30" -> "待支付"
                    "31" -> {
                        if (!timeOut.isNullOrBlank()) {
                            binding.tvErr.visibility = View.VISIBLE
                            binding.tvErr.text = timeOut
                        }
                        binding.tvAllocation.visibility = View.VISIBLE
                        "待配车"
                    }
                    "32" -> {
                        if (!timeOut.isNullOrBlank()) {
                            binding.tvErr.visibility = View.VISIBLE
                            binding.tvErr.text = timeOut
                        }
                        binding.tvExchange.visibility = View.VISIBLE
                        binding.tvPrepare.visibility = View.VISIBLE
                        "待整备"
                    }
                    "33" -> {
                        binding.tvReturn.visibility = View.VISIBLE
                        "待还车"
                    }
                    "34" -> {
                        if (!timeOut.isNullOrBlank()) {
                            binding.tvErr.visibility = View.VISIBLE
                            binding.tvErr.text = timeOut
                        }
                        if ("1" == isBeenPerformed) {
                            binding.tvPickup.text = "修改取车资料"
                        } else {
                            binding.tvPickup.text = "取车"
                        }
                        binding.tvExchange.visibility = View.VISIBLE
                        binding.tvPickup.visibility = View.VISIBLE
                        "待取车"
                    }
                    "35" -> {
                        if (!timeOut.isNullOrBlank()) {
                            binding.tvErr.visibility = View.VISIBLE
                            binding.tvErr.text = timeOut
                        }
                        "进行中"
                    }
                    "36" -> "已结算"
                    "38" -> "异常"
                    "39" -> "待结算"
                    "40" -> "已取消"
                    "50" -> "已完成"
                    "60", "61", "62" -> "待退费"
                    else -> ""
                }
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<ZXCOrderBean.RecordsBean>() {
        val ACTION_DETAIL = 1000
        val ACTION_RETURN = 1001
        val ACTION_EXCHANGE = 1002
        val ACTION_PREPARE = 1003
        val ACTION_ALLOCATION = 1004
        val ACTION_PICKUP = 1005

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