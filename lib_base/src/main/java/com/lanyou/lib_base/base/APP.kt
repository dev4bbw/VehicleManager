package com.lanyou.lib_base.base

import android.app.Application
import com.alibaba.android.arouter.BuildConfig
import com.alibaba.android.arouter.launcher.ARouter
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.lanyou.lib_base.utils.Foreground
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV

class APP : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        //日志
        XLog.init(LogLevel.ALL)
        //数据存储
        MMKV.initialize(this)
        //ARouter
        initARouter()

        Foreground.init(this)
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {
        }
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }

    companion object {
        private lateinit var INSTANCE: APP
        fun getInstance() = INSTANCE

        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
                ClassicsHeader(context)
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreator{context, _ ->
                ClassicsFooter(context).setDrawableSize(20f);
            }
        }
    }
}