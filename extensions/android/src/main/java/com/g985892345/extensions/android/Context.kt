package com.g985892345.extensions.android

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.coroutineScope
import com.g985892345.context.UtilsContext

/**
 * ...
 * @author 985892345 (Guo Xiangrui)
 * @email 2767465918@qq.com
 * @date 2022/5/30 15:57
 */

val appContext: Context
  get() = UtilsContext.appContext

/**
 * 应用程序的生命周期
 *
 * 注意：
 * - ON_START、ON_RESUME 在应用程序进入前台时回调
 * - ON_PAUSE、ON_STOP 在应用程序进入后台时回调
 * - ON_DESTROY 永远不会回调
 */
val processLifecycle: Lifecycle
  get() = ProcessLifecycleOwner.get().lifecycle

/**
 * 应用程序的生命周期内的协程，代替 GlobalScope 的最好方式
 */
val processLifecycleScope: LifecycleCoroutineScope
  get() = processLifecycle.coroutineScope

/**
 * 是否是 debug 构建
 */
val isDebuggableBuild: Boolean
  get() = (UtilsContext.application.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0