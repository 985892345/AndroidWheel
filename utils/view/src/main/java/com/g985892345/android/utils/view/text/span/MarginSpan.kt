package com.g985892345.android.utils.view.text.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ReplacementSpan

/**
 * .
 *
 * @author 985892345
 * @date 2023/11/30 11:15
 */
class MarginSpan(
  val margin: Int
) : ReplacementSpan() {

  companion object {
    fun getMarginBuilder(margin: Int): SpannableStringBuilder {
      return SpannableStringBuilder(" ").apply {
        setSpan(MarginSpan(margin), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      }
    }
  }

  override fun getSize(
    paint: Paint,
    text: CharSequence?,
    start: Int,
    end: Int,
    fm: Paint.FontMetricsInt?
  ): Int = margin

  override fun draw(
    canvas: Canvas,
    text: CharSequence?,
    start: Int,
    end: Int,
    x: Float,
    top: Int,
    y: Int,
    bottom: Int,
    paint: Paint
  ) = Unit
}