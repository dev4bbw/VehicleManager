package com.lanyou.lib_base.utils

import android.app.Activity
import com.lanyou.lib_base.utils.ActivityController
import android.content.Intent
import java.lang.ref.WeakReference
import java.util.ArrayList

/**
 * className: ActivityController
 * description: 活动管理类
 * author: hong
 * datetime: 2016/4/5 0005 上午 8:54
 */
object ActivityController {
    private val activities: MutableList<Activity> = ArrayList()
    private var currActivity: WeakReference<Activity>? = null

    /**
     * 添加活动
     *
     * @param activity
     */
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    /**
     * 销毁活动
     *
     * @param activity
     */
    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    /**
     * 销毁指定类名其他的activity
     */
    fun finishOtherActivity(clazz: Class<*>) {
        for (i in activities.indices) {
            val activity = activities[i]
            if (activity.javaClass != clazz && !activity.isFinishing) {
                activity.finish()
            }
        }
    }

    /**
     * 销毁指定类名其他的activity
     */
    fun hasActivity(clazz: Class<*>): Boolean {
        for (i in activities.indices) {
            val activity = activities[i]
            if (activity.javaClass == clazz && !activity.isFinishing) {
                return true
            }
        }
        return false
    }

    /**
     * 销毁活动
     *
     * @param tags
     */
    fun removeActivity(vararg tags: String) {
        for (activity in activities) {
            if (!activity.isFinishing) {
                for (i in 0 until tags.size) {
                    if (activity.javaClass.name == tags[i]) {
                        activity.finish()
                    }
                }
            }
        }
    }

    /**
     * 销毁指定类名的activity
     */
    fun finishActivity(clazz: Class<*>) {
        for (i in activities.indices) {
            val activity = activities[i]
            if (activity.javaClass == clazz && !activity.isFinishing) {
                activity.finish()
                return
            }
        }
    }

    /**
     * 销毁所有活动
     */
    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }

    val size: Int
        get() = activities.size

    /**
     * 销毁指定活动之外的所有活动
     *
     * @param tags
     */
    fun finishIgnoreTag(vararg tags: String) {
        for (activity in activities) {
            if (!activity.isFinishing) {
                var flag = true
                for (i in 0 until tags.size) {
                    if (activity.javaClass.name == tags[i]) {
                        flag = false
                    }
                }
                if (flag) {
                    activity.finish()
                }
            }
        }
    }

    /**
     * 判断活动是否在集合里
     *
     * @param tag
     * @return
     */
    fun hasAdded(tag: String): Boolean {
        for (activity in activities) {
            if (activity.javaClass.name == tag) {
                return true
            }
        }
        return false
    }

    /**
     * 设置当前Activity
     *
     * @param activity
     */
    fun setCurrActivity(activity: WeakReference<Activity>?) {
        currActivity = activity
    }

    val allRunningActvity: List<Activity>
        get() = activities

    /**
     * 获取当前Activity
     *
     * @return
     */
    fun getCurrActivity(): Activity? {
        return if (currActivity == null) {
            null
        } else currActivity!!.get()
    }

    /**
     * 获取当前Activity
     *
     * @return
     */
    fun startFirstActivity(clazz: Class<*>) {
        if (currActivity == null) {
            return
        }
        if (!hasActivity(clazz)) {
            val activity = getCurrActivity()
            activity?.startActivity(Intent(activity, clazz))
        }
    }
}