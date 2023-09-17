package com.g985892345.jvm.exception

import kotlin.reflect.KClass

/**
 * 配合 Rxjava 和 Flow 使用 DSL 处理异常的工具类
 */
open class ExceptionResult<Emitter>(
  val throwable: Throwable,
  val emitter: Emitter
) {

  /**
   * 处理 [T] 类型的异常
   * ```
   * NullPointerException::class.catch {
   *     val exception = it // 闭包里面的 it 就是 NullPointerException
   *     val emitter = this // 闭包里面的 this 是发射器，用于重新发送值给下游
   * }
   * ```
   */
  inline infix fun <reified T : Throwable> KClass<T>.catch(
    action: Emitter.(T) -> Unit
  ): KClass<T> {
    if (throwable is T) {
      action.invoke(emitter, throwable)
    }
    return this
  }

  /**
   * 抓取除了 [T] 以外的其他异常
   */
  inline infix fun <reified T : Throwable> KClass<T>.catchOther(
    action: Emitter.(Throwable) -> Unit
  ): KClass<T> {
    if (throwable !is T) {
      action.invoke(emitter, throwable)
    }
    return this
  }
}