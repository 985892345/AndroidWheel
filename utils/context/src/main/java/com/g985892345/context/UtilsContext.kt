package com.g985892345.context

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import java.util.ServiceLoader

/**
 * .
 *
 * @author 985892345
 * 2023/5/25 15:22
 */
object UtilsContext {
  
  lateinit var application: Application
    private set
  
  val appContext: Context
    get() = application
  
  internal fun setApplication(application: Application) {
    UtilsContext.application = application
  }
}

