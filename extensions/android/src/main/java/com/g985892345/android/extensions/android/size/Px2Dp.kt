package com.g985892345.android.extensions.android.size

import com.g985892345.android.utils.context.appContext
import kotlin.math.roundToInt

/**
 * .
 *
 * @author 985892345
 * @date 2023/10/20 19:37
 */

val Int.dp2pxF: Float
  get() = appContext.resources.displayMetrics.density * this

val Int.dp2px: Int
  get() = dp2pxF.roundToInt()

val Float.dp2pxF: Float
  get() = appContext.resources.displayMetrics.density * this

val Float.dp2px: Int
  get() = dp2pxF.roundToInt()

val Int.px2dpF: Float
  get() = this / appContext.resources.displayMetrics.density

val Int.px2dp: Int
  get() = px2dpF.roundToInt()

val Float.px2dpF: Float
  get() = this / appContext.resources.displayMetrics.density

val Float.px2dp: Int
  get() = px2dpF.roundToInt()