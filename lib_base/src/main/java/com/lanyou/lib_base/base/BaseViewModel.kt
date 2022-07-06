package com.lanyou.lib_base.base

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

open class BaseViewModel:ViewModel() {
    val uiChangeLiveData = UIChangeLiveData()

    companion object {
        class UIChangeLiveData : SingleLiveEvent<Void>() {
            val showDialogEvent = SingleLiveEvent<String>()
            val dismissDialogEvent = SingleLiveEvent<Void>()
            val finishEvent = SingleLiveEvent<Void>()
            val uiMessageEvent = SingleLiveEvent<UIMessage>()
            val toastEvent = SingleLiveEvent<String>()
        }
    }
}