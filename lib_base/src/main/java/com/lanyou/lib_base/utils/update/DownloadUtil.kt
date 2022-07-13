package com.lanyou.lib_base.utils.update

import android.content.Context
import com.lanyou.lib_base.base.APP
import com.lanyou.lib_base.utils.AppUtil
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.util.FileDownloadUtils
import java.io.File

object DownloadUtil {
    private var downloadUpdateApkFilePath = ""

    fun download(context: Context, url: String, callBack: DownloadCallBack.() -> Unit) {
        val downloadCallBack = registerDownLoadCallBack(callBack)
        downloadUpdateApkFilePath = getApkPath()

        //检查文件已经存在，就删除掉
        val tempFile =
            File(downloadUpdateApkFilePath)
        if (tempFile.exists()) {
            AppUtil.deleteFile(downloadUpdateApkFilePath)
            AppUtil.deleteFile(FileDownloadUtils.getTempPath(downloadUpdateApkFilePath))
        }

        val downTask = FileDownloader.getImpl()
            .create(url)
            .setPath(downloadUpdateApkFilePath)
        downTask.setListener(object : FileDownloadListener() {
            override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                UpdateReceiver.send(context, 0)
                downloadCallBack.downloadStart()
                if (totalBytes < 0) {
                    downTask.pause()
                }
            }

            override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                var progress = (soFarBytes * 100.0 / totalBytes).toInt()
                if (progress < 0) progress = 0

                UpdateReceiver.send(context, progress)
                downloadCallBack.downloading(progress)

                if (totalBytes < 0) {
                    downTask.pause()
                }
            }

            override fun completed(task: BaseDownloadTask?) {
                task?.let {
                    UpdateReceiver.send(context, 100)
                    downloadCallBack.downloadComplete(it.path)
                }
            }

            override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
            }

            override fun error(task: BaseDownloadTask?, e: Throwable?) {
                AppUtil.deleteFile(downloadUpdateApkFilePath)
                AppUtil.deleteFile(FileDownloadUtils.getTempPath(downloadUpdateApkFilePath))
                e?.let {
                    UpdateReceiver.send(context, -1)
                    downloadCallBack.downloadFail(e.message)
                }
            }

            override fun warn(task: BaseDownloadTask?) {
            }

        })
            .start()
    }
    private fun getApkPath(): String {
        return APP.getInstance().applicationContext.externalCacheDir.toString() +
                "/" + APP.getInstance().applicationContext.packageName + "/apks/" +
                "VehicleManager_" + AppUtil.getVersionName(APP.getInstance().applicationContext) + ".apk"
    }
}