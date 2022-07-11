package com.lanyou.lib_base.utils.update

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

class UpdateService:Service() {
    private val updateReceiver by lazy { UpdateReceiver() }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        registerReceiver(updateReceiver,IntentFilter(packageName+UpdateReceiver.DOWNLOAD_ONLY))
        registerReceiver(updateReceiver,IntentFilter(packageName+UpdateReceiver.RE_DOWNLOAD))
        registerReceiver(updateReceiver,IntentFilter(packageName+UpdateReceiver.CANCEL_DOWNLOAD))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(updateReceiver)
    }
}