package com.lanyou.lib_base.utils.update

import android.app.DownloadManager
import android.app.DownloadManager.Request
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import com.lanyou.lib_base.KeyConstant
import com.lanyou.lib_base.base.APP
import com.lanyou.lib_base.utils.AppUtil
import com.lanyou.lib_base.utils.mmkvUtil
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader

object DownloadUtil {

    fun download(context: Context, url: String) {
        val downTask = FileDownloader.getImpl()
            .create(url)
            .setPath(getApkPath())
        downTask.setListener(object : FileDownloadListener() {
            override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                downStart(context)
                if (totalBytes < 0) {
                    downTask.pause()
                }
            }

            override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                downLoading(context,soFarBytes, totalBytes)
                if (totalBytes < 0) {
                    downTask.pause()
                }
            }

            override fun completed(task: BaseDownloadTask?) {
                task?.let {
                    downComplete(context,it.path)
                }
            }

            override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                downPause()
            }

            override fun error(task: BaseDownloadTask?, e: Throwable?) {
                e?.let {
                    downError(it)
                }
            }

            override fun warn(task: BaseDownloadTask?) {
            }

        })
            .start()
    }

    fun downStart(context: Context) {
        UpdateReceiver.send(context,0)
    }
    fun downLoading(context: Context,soFarBytes: Int, totalBytes: Int) {
        var progress = (soFarBytes*100.0 /totalBytes).toInt()
        if (progress<0) progress = 0
        UpdateReceiver.send(context,progress)
    }
    fun downComplete(context: Context,downPath: String) {
        UpdateReceiver.send(context,100)

    }
    fun downPause() {

    }
    fun downError(e: Throwable) {

    }

    private fun getApkPath(): String {
        return APP.getInstance().applicationContext.externalCacheDir.toString() +
                "/" + APP.getInstance().applicationContext.packageName + "/apks/" +
                "VehicleManager_" + AppUtil.getVersionName(APP.getInstance().applicationContext) + ".apk"
    }
}