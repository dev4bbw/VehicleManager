package com.lanyou.lib_base.utils

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Process
import android.provider.Settings
import com.lanyou.lib_base.BuildConfig
import com.lanyou.lib_base.base.APP
import java.io.File

/**
 *
 * @author hy
 * @date 2016/7/29 0029
 */
object AppUtil {
    private const val REQUEST_CODE_APP_INSTALL = 111

    /**请求安装apk权限的请求码 */
    private const val REQUEST_CODE_INSTALL_APK_PERMISSION = 1001

    /**
     * 获取系统版本号
     * @param context
     * @return
     */
    fun getVersionCode(context: Context): Int {
        try {
            val ac = context.applicationContext
            val pm = ac.packageManager
            val pi = pm.getPackageInfo(ac.packageName, 0)
            return pi.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * root、刷机或恢复出厂设置都会导致设备的ANDROID_ID发生改变
     * 某些厂商定制系统的Bug会导致不同的设备可能会产生相同的ANDROID_ID，而且某些设备获取到的ANDROID_ID为null
     * @return
     */
    val deviceId: String
        get() = Settings.System.getString(
            APP.getInstance().contentResolver,
            Settings.Secure.ANDROID_ID
        )

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    val deviceBrand: String
        get() = Build.BRAND

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    val systemModel: String
        get() = Build.MODEL

    /**
     * 获取手机系统版本号
     */
    val systemVersion: String
        get() = Build.VERSION.RELEASE

    fun getVersionName(context: Context): String {
        try {
            val ac = context.applicationContext
            val pm: PackageManager = ac.packageManager
            val pi: PackageInfo = pm.getPackageInfo(ac.packageName, 0)
            return pi.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getNDVersionName(): String {
        return BuildConfig.N_D_VERSION
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    fun getCurProcessName(context: Context): String? {
        val pid = Process.myPid()
        val activityManager: ActivityManager = context
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in activityManager
            .runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    fun deleteFile(filePath: String?) {
        if (filePath == null) {
            return
        }
        val file = File(filePath)
        try {
            if (file.isFile) {
                file.delete()
            }
        } catch (e: Exception) {
        }
    }

}