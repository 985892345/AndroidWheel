package com.g985892345.android.extensions.rxjava

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.g985892345.jvm.rxjava.RxjavaLifecycle
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable


/**
 * RxjavaLifecycle 绑定 Lifecycle
 */
fun RxjavaLifecycle.bindLifecycle(disposable: Disposable, lifecycle: Lifecycle) {
  lifecycle.addObserver(
    object : LifecycleEventObserver {
      override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event.targetState == Lifecycle.State.DESTROYED) {
          source.lifecycle.removeObserver(this)
          disposable.dispose() // 在 DESTROYED 时关掉流
        } else {
          if (disposable.isDisposed) {
            // 如果在其他生命周期时流已经被关了，就取消该观察者
            source.lifecycle.removeObserver(this)
          }
        }
      }
    }
  )
}

fun <T : Any> Single<T>.safeSubscribeBy(
  lifecycle: RxjavaLifecycle,
  onError: (Throwable) -> Unit = {},
  onSuccess: (T) -> Unit = {}
): Disposable = lifecycle.run { safeSubscribeBy(onError, onSuccess) }

fun <T : Any> Observable<T>.safeSubscribeBy(
  lifecycle: RxjavaLifecycle,
  onError: (Throwable) -> Unit = {},
  onComplete: () -> Unit = {},
  onNext: (T) -> Unit = {}
): Disposable = lifecycle.run { safeSubscribeBy(onError, onComplete, onNext) }

fun Completable.safeSubscribeBy(
  lifecycle: RxjavaLifecycle,
  onError: (Throwable) -> Unit = {},
  onComplete: () -> Unit = {},
): Disposable = lifecycle.run { safeSubscribeBy(onError, onComplete) }

/**
 * 关联 View 的生命周期，因为 View 生命周期回调的残缺，只能绑定 OnAttachStateChangeListener ，
 * 但这个其实是不完整的，因为 View 不显示在屏幕上时也会回调
 *
 * ## 所以建议用在只会显示一次的 View 上
 */
fun <T : Any> Single<T>.safeSubscribeBy(
  view: View,
  onError: (Throwable) -> Unit = {},
  onSuccess: (T) -> Unit = {}
): Disposable = subscribe(onSuccess, onError).also {
  view.addOnAttachStateChangeListener(
    object : View.OnAttachStateChangeListener {
      override fun onViewAttachedToWindow(v: View) {}
      override fun onViewDetachedFromWindow(v: View) {
        v.removeOnAttachStateChangeListener(this)
        it.dispose()
      }
    }
  )
}

/**
 * 关联 View 的生命周期，因为 View 生命周期回调的残缺，只能绑定 OnAttachStateChangeListener ，
 * 但这个其实是不完整的，因为 View 不显示在屏幕上时也会回调
 *
 * ## 所以建议用在只会显示一次的 View 上
 */
fun <T : Any> Observable<T>.safeSubscribeBy(
  view: View,
  onError: (Throwable) -> Unit = {},
  onComplete: () -> Unit = {},
  onNext: (T) -> Unit = {}
): Disposable = subscribe(onNext, onError, onComplete).also {
  view.addOnAttachStateChangeListener(
    object : View.OnAttachStateChangeListener {
      override fun onViewAttachedToWindow(v: View) {}
      override fun onViewDetachedFromWindow(v: View) {
        v.removeOnAttachStateChangeListener(this)
        it.dispose()
      }
    }
  )
}

/**
 * 关联 View 的生命周期，因为 View 生命周期回调的残缺，只能绑定 OnAttachStateChangeListener ，
 * 但这个其实是不完整的，因为 View 不显示在屏幕上时也会回调
 *
 * ## 所以建议用在只会显示一次的 View 上
 */
fun Completable.safeSubscribeBy(
  view: View,
  onError: (Throwable) -> Unit = {},
  onComplete: () -> Unit = {},
): Disposable = subscribe(onComplete, onError).also {
  view.addOnAttachStateChangeListener(
    object : View.OnAttachStateChangeListener {
      override fun onViewAttachedToWindow(v: View) {}
      override fun onViewDetachedFromWindow(v: View) {
        v.removeOnAttachStateChangeListener(this)
        it.dispose()
      }
    }
  )
}

