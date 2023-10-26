package com.g985892345.android.extensions.android.size

import android.os.Build
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_SP
import com.g985892345.android.extensions.android.appContext
import kotlin.math.roundToInt

/**
 * .
 *
 * @author 985892345
 * @date 2023/10/20 19:38
 */

val Int.sp2pxF: Float
  get() = toFloat().sp2pxF

val Int.sp2px: Int
  get() = sp2pxF.roundToInt()

val Float.sp2pxF: Float
  get() = TypedValue.applyDimension(COMPLEX_UNIT_SP, this, appContext.resources.displayMetrics)

val Float.sp2px: Int
  get() = sp2pxF.roundToInt()

val Int.px2spF: Float
  get() = toFloat().px2spF

val Int.px2sp: Int
  get() = px2spF.roundToInt()

val Float.px2spF: Float
  get() = if (Build.VERSION.SDK_INT < 34) {
    @Suppress("DEPRECATION")
    this / appContext.resources.displayMetrics.scaledDensity
  } else {
    TypedValue.convertPixelsToDimension(COMPLEX_UNIT_SP, this, appContext.resources.displayMetrics)
  }

val Float.px2sp: Int
  get() = px2spF.roundToInt()