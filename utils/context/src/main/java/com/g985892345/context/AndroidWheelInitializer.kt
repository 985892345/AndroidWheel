package com.g985892345.context

import android.app.Application
import android.content.Context
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
class AndroidWheelInitializer : Initializer<Unit> {
  override fun create(context: Context) {
    UtilsContext.setApplication(context.applicationContext as Application)
  }
  
  override fun dependencies() = emptyList<Class<out Initializer<*>>>()
}