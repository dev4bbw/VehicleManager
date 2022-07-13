package com.lanyou.lib_base.utils.update

private typealias DownCommonCallBack = () -> Unit
private typealias DownCompleteCallBack = (path: String?) -> Unit
private typealias DownFailCallBack = (msg: String?) -> Unit
private typealias DownLoadingCallBack = (progress: Int) -> Unit

class DownloadCallBack : DownloadListener {
    private var downCommonCallBack: DownCommonCallBack? = null
    private var downCompleteCallBack: DownCompleteCallBack? = null
    private var downFailCallBack: DownFailCallBack? = null
    private var downLoadingCallBack: DownLoadingCallBack? = null

    override fun downloading(progress: Int) = downLoadingCallBack?.invoke(progress) ?: Unit

    override fun downloadFail(msg: String?) = downFailCallBack?.invoke(msg) ?: Unit

    override fun downloadComplete(path: String?) = downCompleteCallBack?.invoke(path) ?: Unit

    override fun downloadStart() = downCommonCallBack?.invoke() ?: Unit

    override fun reDownload() = downCommonCallBack?.invoke() ?: Unit

    override fun pause() = downCommonCallBack?.invoke() ?: Unit

    fun downloading(callBack: DownLoadingCallBack) {
        downLoadingCallBack = callBack
    }

    fun downloadFail(callBack: DownFailCallBack) {
        downFailCallBack = callBack
    }

    fun downloadComplete(callBack: DownCompleteCallBack) {
        downCompleteCallBack = callBack
    }

    fun downloadStart(callBack: DownCommonCallBack) {
        downCommonCallBack = callBack
    }

    fun reDownload(callBack: DownCommonCallBack) {
        downCommonCallBack = callBack
    }

    fun pause(callBack: DownCommonCallBack) {
        downCommonCallBack = callBack
    }
}

fun registerDownLoadCallBack(function:DownloadCallBack.()->Unit) = DownloadCallBack().also(function)