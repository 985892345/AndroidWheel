package com.g985892345.android.extensions.android

import android.graphics.drawable.Drawable
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.g985892345.android.utils.context.appContext

/**
 * .
 *
 * @author 985892345
 * @date 2023/10/20 19:57
 */

val Int.color: Int
  get() = ContextCompat.getColor(appContext, this)

val Int.string: String
  get() = appContext.getString(this)

val Int.drawable: Drawable
  get() = AppCompatResources.getDrawable(appContext, this)!!

val Int.dimen: Float
  get() = appContext.resources.getDimension(this)

val Int.anim: Animation
  get() = AnimationUtils.loadAnimation(appContext, this)