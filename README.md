# 开发须知


## KOTLIN语言(尽可能使用)

## 项目结构
### 在无特殊需求的情况下，必须遵循框架开发，Activity/Fragment等应该继承Base基类，以便统一管理
- [框架] MVVM
- [网络请求] Retrofit+Okhttp
- [组件化工具] ComponentLight
- [数据存储] MMKV  （替代SharePreferences）,后续如果遇到复杂数据需要，增加ROOM数据库存储
PS.暂时未使用DataBinding,绑定控件使用ViewBinding

## 组件和工具类
### 工具类在lib_base模块中，勿重复引入已有功能或相似的工具类，尽量在已有的相似工具类内做扩展
#### [Jetpack组件]
- [Lifecycle]
- [LiveDate]
- [ViewModel]
#### [工具类]
- [Moshi](https://github.com/square/moshi):Moshi是一个适用于Android、Java和Kotlin的JSON 库;
- [coil](https://github.com/coil-kt/coil):由Kotlin Coroutines支持的Android图像加载库；
- [coroutines](https://github.com/Kotlin/kotlinx.coroutines):Kotlin coroutines;
- [XLog](https://github.com/elvishew/xLog):轻量、美观强大、可扩展的 Android 和 Java 日志库；
- [ToastUtil]吐司
- [DialogUtil]
````待补充