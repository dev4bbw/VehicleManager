package com.lanyou.lib_base.base

import android.content.Context
import androidx.annotation.Size
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.guolindev.PermissionX

open class PermissionActivity : AppCompatActivity() {

    fun checkPermission(vararg perms: String, callback: (isAllGranted: Boolean) -> Unit) {
        PermissionX.init(this)
            .permissions(*perms)
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限",
                    "马上设置","暂不授权")
            }
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "您需要去应用程序设置当中手动开启权限",
                    "马上设置","暂不授权"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    callback.invoke(true)
                }
            }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}