package com.g985892345.android.utils.text.span

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ReplacementSpan
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec
import android.widget.FrameLayout
import androidx.annotation.LayoutRes

/**
 * 给 TextView 添加标签的 Span
 *
 * @author 985892345
 * @date 2023/11/30 09:59
 */
class TextTagSpan private constructor(
  val parent: FrameLayout
): ReplacementSpan() {

  companion object {
    fun getTagBuilder(view: View): SpannableStringBuilder {
      return SpannableStringBuilder("[tag]").apply {
        setSpan(TextTagSpan(view), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      }
    }

    fun getTagBuilder(context: Context, @LayoutRes id: Int): SpannableStringBuilder {
      return SpannableStringBuilder("[tag]").apply {
        setSpan(TextTagSpan(context, id), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      }
    }
  }

  constructor(view: View) : this(
    FrameLayout(view.context).apply { addView(view) }
  )

  constructor(context: Context, @LayoutRes id: Int) : this(
    LayoutInflater.from(context).inflate(id, FrameLayout(context)) as FrameLayout,
  )

  override fun getSize(
    paint: Paint,
    text: CharSequence?,
    start: Int,
    end: Int,
    fm: Paint.FontMetricsInt?
  ): Int {
    parent.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
    return parent.measuredWidth
  }

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
  ) {
    parent.measure(
      MeasureSpec.UNSPECIFIED,
      MeasureSpec.makeMeasureSpec(bottom - top, MeasureSpec.EXACTLY)
    )
    parent.layout(0, 0, parent.measuredWidth, parent.measuredHeight)
    canvas.save()
    canvas.translate(x, 0F)
    parent.draw(canvas)
    canvas.restore()
  }
}