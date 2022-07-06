package com.lanyou.lib_base.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.lanyou.lib_base.R
import com.lanyou.lib_base.databinding.DialogAppMessagePublicBinding


object CommonDialog {

    fun showConfirmDialog(
        context: Context,
        titleTx: String = "",
        contentTx: String = "",
        onlyConfirm: Boolean = false,
        allowCancel: Boolean = false,
        onCancelListener: OnCancelListener,
        onConfirmListener: OnConfirmListener
    ) {
        val dialogView: View = View.inflate(context, R.layout.dialog_app_message_public, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView).create()
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        if (!allowCancel) dialog.setCancelable(false)
        val title = dialogView.findViewById<TextView>(R.id.tv_title)
        val content = dialogView.findViewById<TextView>(R.id.tv_content)
        val cancel = dialogView.findViewById<TextView>(R.id.tv_cancel)
        val vLine = dialogView.findViewById<View>(R.id.view_line)
        val confirm = dialogView.findViewById<TextView>(R.id.tv_confirm)

        if (titleTx.isBlank()) title.visibility = View.GONE else title.text = titleTx
        if (contentTx.isBlank()) content.visibility = View.GONE else content.text = contentTx
        cancel.setOnClickListener {
            onCancelListener.onClick(dialog)
        }
        confirm.setOnClickListener {
            onConfirmListener.onClick(dialog)
        }
        if (onlyConfirm) {
            cancel.visibility = View.GONE
            vLine.visibility = View.GONE
        }
        dialog.show()

    }

    // 点击弹窗取消按钮回调
    interface OnCancelListener {
        fun onClick(dialog:AlertDialog)
    }

    // 点击确定跳转回调
    interface OnConfirmListener {
        fun onClick(dialog:AlertDialog)
    }
}