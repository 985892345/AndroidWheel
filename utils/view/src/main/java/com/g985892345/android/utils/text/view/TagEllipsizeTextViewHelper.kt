package com.g985892345.android.utils.text.view

import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.View
import android.view.View.MeasureSpec
import android.widget.TextView
import com.g985892345.android.utils.text.span.MarginSpan
import com.g985892345.android.utils.text.span.TextTagSpan

/**
 * .
 *
 * @author 985892345
 * @date 2023/11/30 11:08
 */
class TagEllipsizeTextViewHelper(
  val textView: TextView
) {

  private var mTagSpan: TextTagSpan? = null
  private var mMargin: Int = 0


  fun setTagView(text: String?, view: View?, marginWhenLine: Int) {
    mMargin = marginWhenLine
    mTagSpan = if (view != null) TextTagSpan(view) else null
    textView.text = text
  }

  fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val tagSpan = mTagSpan ?: return
    val layout = textView.layout
    if (layout != null) {
      try {
        val width = MeasureSpec.getSize(widthMeasureSpec) - textView.paddingLeft - textView.paddingRight
        resetText(textView.text, tagSpan, width)
      } catch (e: Exception) {

      }
    }
  }

  private fun resetText(
    originText: CharSequence,
    tagSpan: TextTagSpan,
    width: Int,
  ) {
    if (textView.layout.lineCount <= textView.maxLines || textView.maxLines == -1) {
      val builder = SpannableStringBuilder.valueOf(originText)
      val spans = builder.getSpans(0, builder.length, TextTagSpan::class.java)
      if (spans != null && spans.contains(tagSpan)) {
        // 已经添加上了
        return
      }
    }
    val suffixTagSpannable = SpannableStringBuilder("[tag]").apply {
      setSpan(tagSpan, 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    if (textView.layout.lineCount < textView.maxLines || textView.maxLines == -1) {

    }
  }

  // 当前总行数小于最大行数，判断 tag 能否放得下，放不下就换行显示
  private fun resetTextLessThanMaxLines(
    originText: CharSequence,
    suffixTagBuilder: SpannableStringBuilder,
    width: Int,
  ) {
    val lastLineWidth = textView.layout.getLineWidth(textView.layout.lineCount - 1).toInt()
    val tagWidth = Layout.getDesiredWidth(suffixTagBuilder, textView.paint)
    if (lastLineWidth + mMargin + tagWidth <= width) {
      // 最后一行 文本 + 间距 + 标签 <= 显示宽度
      val marginBuilder = MarginSpan.getMarginBuilder(mMargin)
      textView.text = SpannableStringBuilder(originText)
        .append(marginBuilder)
        .append(suffixTagBuilder)
    } else {
      // 最后一行 文本 + 间距 + 标签 > 显示宽度，则 tag 换行显示
      textView.text = SpannableStringBuilder(originText)
        .append('\n')
        .append(suffixTagBuilder)
    }
  }

  // 当前总行数大于等于最大行数，判断 tag 能否放得下，放不下就压缩文本显示
  private fun resetTextGreaterOrEqualMaxLines(
    originText: CharSequence,
    suffixTagBuilder: SpannableStringBuilder,
    width: Int,
  ) {
    val lineStartIndex = textView.layout.getLineStart(textView.maxLines - 1)
    val lineEndIndex = textView.layout.getLineEnd(textView.maxLines - 1)
    val lineWidth = textView.layout.getLineWidth(textView.maxLines - 1)
    val tagWidth = Layout.getDesiredWidth(suffixTagBuilder, textView.paint)
    if (lineWidth + mMargin + tagWidth <= width) {
      // maxLines 那一行 文本 + 间距 + 标签 <= 显示宽度
      val marginBuilder = MarginSpan.getMarginBuilder(mMargin)
      var subStr = originText.subSequence(0, lineEndIndex)
      while (subStr.endsWith('\n')) {
        subStr = subStr.subSequence(0, subStr.length - 1)
      }
      textView.text = SpannableStringBuilder(subStr)
        .append(marginBuilder)
        .append(suffixTagBuilder)
    } else {
      // maxLines 那一行 文本 + 间距 + 标签 > 显示宽度，则压缩文本为 ...
      val moreWidth = Layout.getDesiredWidth(More, textView.paint)
      val widthDiff = (lineWidth + moreWidth + mMargin + tagWidth - width).toInt()
      // 需要 remove 掉的字符数量
      // todo 待完成
    }
  }

//  private fun computeRemovedCharacterCount(
//    widthDiff: Int,
//    text: CharSequence,
//    paint: TextPaint
//  ): Int {
//    if (text.isEmpty()) return 0
//    val builder = SpannableStringBuilder.valueOf(text)
//    val characterStyles = builder.getSpans(0, builder.length, CharacterStyle::class.java)
//    var removed = 1
//    while (true) {
//      var end = text.length - removed
//      Layout.getDesiredWidth(text.subSequence())
//      for (style in characterStyles) {
//        val spanStart = builder.getSpanStart(style)
//        val spanEnd = builder.getSpanEnd(style)
//        if (end in spanStart until spanEnd) {
//          removed = text.length - spanStart
//        }
//      }
//    }
//  }
//
//  private fun computeCharacterStyleRanged(text: CharSequence): List<> {
//
//  }

  companion object {
    private const val More = "..."
  }
}