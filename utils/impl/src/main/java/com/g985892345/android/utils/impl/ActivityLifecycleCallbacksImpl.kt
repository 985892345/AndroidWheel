package com.g985892345.android.utils.impl

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

/**
 * .
 *
 * @author 985892345
 * @date 2023/11/19 17:34
 */
interface ActivityLifecycleCallbacksImpl : ActivityLifecycleCallbacks {

  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
  }

  override fun onActivityStarted(activity: Activity) {
  }

  override fun onActivityResumed(activity: Activity) {
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