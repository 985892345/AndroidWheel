@file:Suppress("UNCHECKED_CAST")

package com.g985892345.extensions.android

/**
 * ...
 * @author 985892345 (Guo Xiangrui)
 * @email guo985892345@foxmail.com
 * @date 2022/7/25 14:37
 */

@Suppress("UNCHECKED_CAST")
fun <T> Any.cast(): T = this as T

@Suppress("UNCHECKED_CAST")
fun <T> Any?.cast2(): T = this as T