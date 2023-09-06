package com.g985892345.android.utils.context

import android.app.Application
import android.content.Context

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

