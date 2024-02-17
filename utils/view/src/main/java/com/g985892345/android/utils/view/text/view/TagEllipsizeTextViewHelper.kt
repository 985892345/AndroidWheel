package com.g985892345.android.utils.view.text.view

import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.view.View
import android.view.View.MeasureSpec
import android.widget.TextView
import com.g985892345.android.utils.view.text.span.MarginSpan
import com.g985892345.android.utils.view.text.span.TextTagSpan

/**
 * 支持给文本末尾添加标签的 EmojiTextView
 *
 * 功能:
 * - 文本末尾添加标签，标签使用 View，支持自定义
 * - 支持设置 maxLine，在文本超 maxLines 时，文本进行 ... 省略，标签显示在 ... 之后，如下图
 * ```
 *    abcdefghijk
 *    lmn...[TAG]
 * ```
 *
 * 注意:
 * - 不要设置 android:ellipsize="end"，设置后会失效，跟 TextView 底层计算宽度有关
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
        // 已经添加上了，防止因为设置 text 可能造成的 onMeasure 循环
        return
      }
    }
    val suffixTagSpannable = SpannableStringBuilder("[tag]").apply {
      setSpan(tagSpan, 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    if (textView.layout.lineCount < textView.maxLines || textView.maxLines == -1) {
      resetTextLessThanMaxLines(originText, suffixTagSpannable, width)
    } else {
      resetTextGreaterOrEqualMaxLines(originText, suffixTagSpannable, width)
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
      val removedCharacterCount = computeRemovedCharacterCount(
        widthDiff, originText.subSequence(0, lineEndIndex), textView.paint)
      val marginSpan = SpannableStringBuilder(" ").apply {
        setSpan(MarginSpan(mMargin), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      }
      var subStr = originText.subSequence(0, lineEndIndex - removedCharacterCount)
      if (subStr.endsWith('\n')) {
        subStr = subStr.subSequence(0, subStr.length - 1)
      }
      textView.text = SpannableStringBuilder(subStr)
        .append(More)
        .append(marginSpan)
        .append(suffixTagBuilder)
    }
  }

  private fun computeRemovedCharacterCount(
    widthDiff: Int,
    text: CharSequence,
    paint: TextPaint
  ): Int {
    if (text.isEmpty()) return 0
    val builder = SpannableStringBuilder.valueOf(text)
    val characterStyles = builder
      .getSpans(0, builder.length, CharacterStyle::class.java).toMutableList()
    var index = getRemoveIndex(builder.length - 1, builder, characterStyles)
    while (Layout.getDesiredWidth(text.subSequence(index, builder.length), paint) < widthDiff) {
      index = getRemoveIndex(index - 1, builder, characterStyles)
    }
    return builder.length - index
  }

  private fun getRemoveIndex(
    index: Int,
    builder: SpannableStringBuilder,
    characterStyles: MutableList<CharacterStyle>
  ): Int {
    for (style in characterStyles) {
      val spanStart = builder.getSpanStart(style)
      val spanEnd = builder.getSpanEnd(style)
      if (index in spanStart until spanEnd) {
        characterStyles.remove(style)
        return spanStart
      }
    }
    return index
  }

  companion object {
    private const val More = "..."
  }
}