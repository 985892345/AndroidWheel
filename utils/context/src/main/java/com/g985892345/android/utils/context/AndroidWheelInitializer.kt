package com.g985892345.android.utils.context

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.annotation.Keep
import androidx.startup.Initializer
import java.lang.ref.WeakReference

/**
 * 项目启动时的初始化器
 *
 * 由官方实现：startup-runtime
 * 使用教程可看：https://juejin.cn/post/6983511491702063135
 *
 * @author 985892345
 * 2023/5/25 16:04
 */
@Keep
class AndroidWheelInitializer : Initializer<Unit> {

  companion object {
    internal lateinit var application: Application
      private set
    internal lateinit var topActivity: WeakReference<Activity>
      private set
  }

  override fun create(context: Context) {
    application = context.applicationContext as Application
    application.registerActivityLifecycleCallbacks(
      object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
          topActivity = WeakReference(activity)
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
          if (activity !== topActivity.get()) {
            topActivity = WeakReference(activity)
          }
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
        }
      }
    )
  }

  override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

val application: Application
  get() = AndroidWheelInitializer.application

val appContext: Context
  get() = application

val topActivity: Activity?
  get() = AndroidWheelInitializer.topActivity.get()