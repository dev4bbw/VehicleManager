package com.lanyou.lib_base.dialog

import android.content.Context
import kotlin.jvm.JvmOverloads
import com.lanyou.lib_base.R
import androidx.appcompat.app.AppCompatDialog
import android.content.DialogInterface
import android.os.Build
import android.text.TextUtils
import android.view.View
import com.lanyou.lib_base.databinding.LayoutProgressDialogBinding

class ProgressDialog @JvmOverloads constructor(
    context: Context?,
    theme: Int = R.style.customDialog
) : AppCompatDialog(context, theme) {
    private var progressBinding:LayoutProgressDialogBinding =
        LayoutProgressDialogBinding.inflate(layoutInflater,null,false)

    class Builder(context: Context?) {
        private val dialog: ProgressDialog
        init {
            dialog = ProgressDialog(context)
            dialog.window!!.setDimAmount(0f)
        }
        fun noClose(): Builder {
            dialog.setCancelable(false)
            return this
        }

        fun setOnDismissListener(listener: DialogInterface.OnDismissListener?): Builder {
            dialog.setOnDismissListener(listener)
            return this
        }

        fun get(): ProgressDialog {
            return dialog
        }
    }

    fun hideNavigationBar() {
        if (Build.VERSION.SDK_INT < 19) { // lower api
            this.window!!.decorView.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window!!.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.INVISIBLE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }

    fun showDialog(msg: String ="") {
        if (TextUtils.isEmpty(msg)) {
            progressBinding.progressMessage.visibility = View.GONE
        }else{
            progressBinding.progressMessage.text = msg
            progressBinding.progressMessage.visibility = View.VISIBLE
        }
        show()
    }

    companion object {
        fun newBuilder(context: Context?): Builder {
            return Builder(context)
        }
    }

    init {
        setContentView(progressBinding.root)
    }
}