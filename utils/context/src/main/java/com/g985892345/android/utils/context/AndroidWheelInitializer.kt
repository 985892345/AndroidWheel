package com.g985892345.android.utils.context

import android.app.Application
import android.content.Context
import androidx.annotation.Keep
import androidx.startup.Initializer

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
  }

  override fun create(context: Context) {
    application = context.applicationContext as Application
  }

  override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

val application: Application
  get() = AndroidWheelInitializer.application

val appContext: Context
  get() = application