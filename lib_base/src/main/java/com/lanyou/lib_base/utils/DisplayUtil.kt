package com.lanyou.lib_base.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.widget.EditText
import android.graphics.Bitmap
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager
import com.lanyou.lib_base.utils.DisplayUtil
import java.lang.Error
import java.lang.Exception

/**
 * 系统显示相关工具类
 */
class DisplayUtil private constructor() {
    companion object {
        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         *
         * @param context 上下文
         * @param dpValue 尺寸dip
         * @return 像素值
         */
        fun dp2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         *
         * @param context 上下文
         * @param pxValue 尺寸像素
         * @return DIP值
         */
        fun px2dp(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
         *
         * @param context 上下文
         * @param pxValue 尺寸像素
         * @return SP值
         */
        fun px2sp(context: Context, pxValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 sp 的单位 转成为 px
         *
         * @param context 上下文
         * @param spValue SP值
         * @return 像素值
         */
        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 sp 的单位 转成为 px
         *
         * @param context 上下文
         * @param spValue SP值
         * @return 像素值
         */
        fun spTpx(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.density
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * 获取dialog宽度
         *
         * @param activity Activity
         * @return Dialog宽度
         */
        fun getDialogW(activity: Activity): Int {
            var dm = DisplayMetrics()
            dm = activity.resources.displayMetrics
            // int w = aty.getWindowManager().getDefaultDisplay().getWidth() - 100;
            return dm.widthPixels - 100
        }

        /**
         * 获取屏幕宽度
         *
         * @param context Context
         * @return 屏幕宽度
         */
        fun getScreenW(context: Context): Int {
            var dm = DisplayMetrics()
            dm = context.resources.displayMetrics
            // int w = aty.getWindowManager().getDefaultDisplay().getWidth();
            return dm.widthPixels
        }

        /**
         * 获取屏幕高度
         *
         * @param context Context
         * @return 屏幕高度
         */
        fun getScreenH(context: Context): Int {
            var dm = DisplayMetrics()
            dm = context.resources.displayMetrics
            // int h = aty.getWindowManager().getDefaultDisplay().getHeight();
            return dm.heightPixels
        }

        /**
         * Toggle keyboard If the keyboard is visible,then hidden it,if it's
         * invisible,then show it
         *
         * @param context 上下文
         */
        fun toggleKeyboard(context: Context) {
            val imm = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive) {
                imm.toggleSoftInput(
                    InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            } else {
                imm.toggleSoftInput(
                    InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }

        fun hideKeyboard(editText: EditText) {
            val imm = editText.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        }

        /**
         * 获得状态栏的高度
         *
         * @param context
         * @return
         */
        fun getStatusHeight(context: Context): Int {
            var statusHeight = -1
            try {
                val clazz = Class.forName("com.android.internal.R\$dimen")
                val `object` = clazz.newInstance()
                val height = clazz.getField("status_bar_height")[`object`].toString().toInt()
                statusHeight = context.resources.getDimensionPixelSize(height)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return statusHeight
        }

        /**
         * 获取当前屏幕截图，包含状态栏
         *
         * @param activity
         * @return
         */
        fun snapShotWithStatusBar(activity: Activity): Bitmap? {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache
            val width = getScreenW(activity)
            val height = getScreenH(activity)
            var bp: Bitmap? = null
            bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
            view.destroyDrawingCache()
            return bp
        }

        /**
         * 获取当前屏幕截图，不包含状态栏
         *
         * @param activity
         * @return
         */
        fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache
            val frame = Rect()
            activity.window.decorView.getWindowVisibleDisplayFrame(frame)
            val statusBarHeight = frame.top
            val width = getScreenW(activity)
            val height = getScreenH(activity)
            var bp: Bitmap? = null
            bp = Bitmap.createBitmap(
                bmp, 0, statusBarHeight, width, height
                        - statusBarHeight
            )
            view.destroyDrawingCache()
            return bp
        }
    }

    /**
     * Don't let anyone instantiate this class.
     */
    init {
        throw Error("Do not need instantiate!")
    }
}