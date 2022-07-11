package com.lanyou.lib_base.utils.update

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class UpdateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            context?.packageName + DOWNLOAD_ONLY -> {
                val systemService =
                    context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val progress = intent.getIntExtra(PROGRESS, 0)

                showNotification(context, progress, systemService)

                if(progress == 100){
                    downloadComplete(systemService)
                }
            }
            context?.packageName + RE_DOWNLOAD -> {

            }
            context?.packageName + CANCEL_DOWNLOAD -> {
                val systemService =
                    context?.getSystemService(Context.NOTIFICATION_SERVICE)
                            as NotificationManager
                downloadComplete(systemService)
            }
        }

    }

    private fun showNotification(
        context: Context,
        progress: Int,
        systemService: NotificationManager
    ) {
        createNotificationChannel(systemService)
        val builder =
            NotificationCompat.Builder(context, DOWNLOAD_CHANNEL_ID)   //Android 8 以下会自动忽略CHANNEL
                .setSmallIcon(android.R.drawable.stat_sys_download)   //* 必须
//            .setWhen(System.currentTimeMillis())
//            .setStyle(NotificationCompat.BigTextStyle()
//                .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(false)
                .setProgress(100, progress, false)
                .setOnlyAlertOnce(true)
        if (progress == -1) {
            val intent = Intent(context.packageName + RE_DOWNLOAD)
            intent.setPackage(context.packageName)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )


            builder.setContentIntent(pendingIntent)
            builder.setContentTitle("下载失败")
        } else {
            builder.setContentTitle("已下载${progress}%")
        }
        val notification = builder.build()
        systemService.notify(1, notification)

    }

    private fun createNotificationChannel(systemService: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                DOWNLOAD_CHANNEL_ID,
                DOWNLOAD_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            systemService.createNotificationChannel(channel)
        }
    }

    private fun downloadComplete(systemService: NotificationManager) {
        systemService.cancel(1)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            systemService.deleteNotificationChannel(DOWNLOAD_CHANNEL_ID)
        }
    }

    companion object {
        const val DOWNLOAD_CHANNEL_ID = "10000"
        const val DOWNLOAD_CHANNEL_NAME = "downloadChannel"
        const val PROGRESS = "progress"
        const val REQUEST_CODE = 1001;

        /**
         * ACTION_UPDATE
         */
        const val DOWNLOAD_ONLY = "app.update"

        /**
         * ACTION_RE_DOWNLOAD
         */
        const val RE_DOWNLOAD = "app.re_download"

        /**
         * 取消下载
         */
        const val CANCEL_DOWNLOAD = "app.download_cancel"


        fun send(context: Context, progress: Int){
            val intent = Intent(context.packageName+ DOWNLOAD_ONLY)
            context.sendBroadcast(intent)
        }

        fun cancelDown(context: Context){
            val intent = Intent(context.packageName+ CANCEL_DOWNLOAD)
            context.sendBroadcast(intent)
        }
    }


}