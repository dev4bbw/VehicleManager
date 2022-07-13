package com.lanyou.lib_base.utils.update

interface DownloadListener {
    fun downloading(progress: Int)
    fun downloadFail(msg: String?)
    fun downloadComplete(path: String?)
    fun downloadStart()
    fun reDownload()
    fun pause()
}