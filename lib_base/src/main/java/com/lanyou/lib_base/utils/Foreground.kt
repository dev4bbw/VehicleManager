package com.lanyou.lib_base.utils

import com.lanyou.lib_base.utils.ActivityController.addActivity
import com.lanyou.lib_base.utils.ActivityController.removeActivity
import com.lanyou.lib_base.utils.ActivityController.setCurrActivity
import android.app.Application.ActivityLifecycleCallbacks
import android.app.Activity
import android.os.Bundle
import android.app.Application
import android.os.Handler
import java.lang.ref.WeakReference

class Foreground private constructor() : ActivityLifecycleCallbacks {
    private val CHECK_DELAY = 500

    //用于判断是否程序在前台
    var isForeground = false
        private set
    private var paused = true

    //handler用于处理切换activity时的短暂时期可能出现的判断错误
    private val handler = Handler()
    private var check: Runnable? = null
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        addActivity(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
//        if(ActivityController.getSize() == 1){
//            activity.stopService(new Intent(activity, GDLocationService.class));
//        }
        removeActivity(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        paused = true
        if (check != null) handler.removeCallbacks(check!!)
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (isForeground && paused) {
                    isForeground = false
                } else {
                }
            }
        }.also { check = it }, CHECK_DELAY.toLong())

        // PgyFeedbackShakeManager.unregister();
    }

    override fun onActivityResumed(activity: Activity) {
        paused = false
        isForeground = true
        if (check != null) handler.removeCallbacks(check!!)
        setCurrActivity(WeakReference(activity))

        /*   // 自定义摇一摇的灵敏度，默认为950，数值越小灵敏度越高。
        PgyFeedbackShakeManager.setShakingThreshold(800);
        // 以对话框的形式弹出
        PgyFeedbackShakeManager.register(activity);*/
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}

    companion object {
        //单例
        private val instance = Foreground()
        private val TAG = Foreground::class.java.simpleName
        fun init(app: Application) {
            app.registerActivityLifecycleCallbacks(instance)
        }

        fun get(): Foreground {
            return instance
        }
    }
}