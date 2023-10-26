package com.g985892345.android.extensions.android.size

/**
 * .
 *
 * @author 985892345
 * @date 2023/10/20 19:54
 */

val Int.dp2spF: Float
  get() = dp2pxF.px2spF

val Int.dp2sp: Int
  get() = dp2pxF.px2sp

val Float.dp2spF: Float
  get() = dp2pxF.px2spF

val Float.dp2sp: Int
  get() = dp2pxF.px2sp

val Int.sp2dpF: Float
  get() = sp2pxF.px2dpF

val Int.sp2dp: Int
  get() = sp2pxF.px2dp

val Float.sp2dpF: Float
  get() = sp2pxF.px2dpF

val Float.sp2dp: Int
  get() = sp2pxF.px2dp