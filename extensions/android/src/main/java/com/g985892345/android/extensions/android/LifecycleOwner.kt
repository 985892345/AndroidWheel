package com.g985892345.android.extensions.android

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * 提供给 LifecycleOwner 快速开启协程
 *
 * 多用于 Activity、Fragment 中
 */
fun LifecycleOwner.launch(
  context: CoroutineContext = EmptyCoroutineContext,
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit
): Job = lifecycleScope.launch(context, start, block)

fun LifecycleOwner.doOnCreate(action: (owner: LifecycleOwner) -> Unit) {
  lifecycle.addObserver(object : DefaultLifecycleObserver{
    override fun onCreate(owner: LifecycleOwner) {
      super.onCreate(owner)
      action.invoke(owner)
    }
  })
}

fun LifecycleOwner.doOnResume(action: (owner: LifecycleOwner) -> Unit) {
  lifecycle.addObserver(object : DefaultLifecycleObserver{
    override fun onResume(owner: LifecycleOwner) {
      super.onResume(owner)
      action.invoke(owner)
    }
  })
}

fun LifecycleOwner.doOnStart(action: (owner: LifecycleOwner) -> Unit) {
  lifecycle.addObserver(object : DefaultLifecycleObserver{
    override fun onStart(owner: LifecycleOwner) {
      super.onStart(owner)
      action.invoke(owner)
    }
  })
}

fun LifecycleOwner.doOnPause(action: (owner: LifecycleOwner) -> Unit) {
  lifecycle.addObserver(object : DefaultLifecycleObserver{
    override fun onPause(owner: LifecycleOwner) {
      super.onPause(owner)
      action.invoke(owner)
    }
  })
}

fun LifecycleOwner.doOnStop(action: (owner: LifecycleOwner) -> Unit) {
  lifecycle.addObserver(object : DefaultLifecycleObserver{
    override fun onStop(owner: LifecycleOwner) {
      super.onStop(owner)
      action.invoke(owner)
    }
  })
}

fun LifecycleOwner.doOnDestroy(action: (owner: LifecycleOwner) -> Unit) {
  lifecycle.addObserver(object : DefaultLifecycleObserver{
    override fun onDestroy(owner: LifecycleOwner) {
      super.onDestroy(owner)
      action.invoke(owner)
    }
  })
}