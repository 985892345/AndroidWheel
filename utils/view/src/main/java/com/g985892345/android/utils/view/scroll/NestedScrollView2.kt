package com.g985892345.android.utils.view.scroll

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.NestedScrollView
import kotlin.math.max

/**
 * 修护官方 NestedScrollView 自带的一些问题
 *
 * ## 一、嵌套 RV 导致 RV 复用失效，全部 item 被加载
 *
 * ## 二、子类调用 requestDisallow 后不再触发 stopNestedScroll() 导致嵌套滑动问题
 * 事例：
 * 长按课表 item 后，下一次触摸时上下滑动不再与 BottomSheet 关联
 * 课表结构：CoordinatorLayout + [NestedDispatchLayout] + Vp2 + NSV
 *
 * 原因：
 * 0. 触摸事件分为两段，分别为前一段和后一段，两段是分开独立的事件，即前一段经过了 UP 事件
 *
 * 1. 长按课表 item 时，此时为前一段事件，DOWN 事件经过了 RV(VP2中的) 和 NSV 的 onInterceptTouchEvent()，
 *   其中 RV 和 NSV 依次调用了 startNestedScroll()，最后 NSV 跟 [NestedDispatchLayout] 嵌套成功，
 *   RV 没有找到嵌套滑动父布局 (因为 RV 是左右滑动) (所以前一段事件中 RV 全程不会触发嵌套滑动，后面将不再考虑前一段事件中的 RV)
 *
 * 2. 前一段事件因为触发长按，调用了 parent.requestDisallowInterceptTouchEvent(true)
 *   导致 NSV 的 onInterceptTouchEvent() 不再回调
 *
 * 3. 前一段事件在收到 UP 事件时，因为 NSV 的 onInterceptTouchEvent() 不再被调用，
 *   所以 NSV 无法调用 stopNestedScroll() 结束嵌套
 *
 * 4. 后一段事件正常上下滑动，先是 DOWN，与前一段事件中的 DOWN 一样的步骤，但是 NSV 因为没有停止上一次的嵌套滑动，
 *   导致 NSV 这次调用 startNestedScroll() 不会通知 [NestedDispatchLayout]
 *
 * 5. 但是 RV 却向上遍历到 CoordinatorLayout，CoordinatorLayout 的 onStartNestedScroll() 被回调，
 *   但是却没有 Behavior 同意嵌套，最后 CoordinatorLayout 调用了 lp.setNestedScrollAccepted(type, false)，
 *   把 BottomSheetBehavior 对应 View 中的 lp.mDidAcceptNestedScrollTouch 设置成了 false，
 *   导致 BottomSheetBehavior 被取消了嵌套滑动 (CoordinatorLayout onNestedPreScroll() 会判断这个变量以决定是否分发嵌套滑动)
 *
 * 6. 所以就导致 bug 的出现
 *
 * 解决办法：
 * - 从 5 入手，取消 VP2 中 RV 的嵌套滑动 (但是如果有其他嵌套的 View，此问题会再次出现)
 * - 从 3 入手，在合适时间调用 NSV 的 stopNestedScroll() (原因：因为 NSV 在 RV 后面调用 startNestedScroll()，在 RV 设置成 false 后会被 NSV 再次改为 true)
 *
 *
 * @author 985892345
 * @date 2023/12/3 19:45
 */
open class NestedScrollView2 : NestedScrollView {

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
  ) : super(context, attrs, defStyleAttr)

  /**
   * 重写该方法的几个原因：
   * 1、为了在 UNSPECIFIED 模式下，课表也能得到 NestedScrollView 的高度
   *
   * 2、NestedScrollView 与 ScrollView 在对于子 View 高度处理时在下面这个方法不一样, 导致
   *    NestedScrollView 中子 View 必须使用具体的高度, 设置成 wrap_content 或 match_parent
   *    都将无效，具体的可以去看 ScrollView 和 NestedScrollView 中对于这同一方法的源码
   *
   * 3、题外话：在 NestedScrollView 中嵌套 RecyclerView 会使 RecyclerView 的懒加载失效，直接原因就与
   *    这个方法有关，而使用 ScrollView 就不会造成懒加载失效的情况
   *
   * 4、至于为什么 NestedScrollView 与 ScrollView 在该方法不同，我猜测原因是为了兼容以前的 Android 版本，
   *    在 ViewGroup#getChildMeasureSpec() 方法中可以发现使用了一个静态变量 sUseZeroUnspecifiedMeasureSpec
   *    来判断 UNSPECIFIED 模式下子 View 该得到的大小，但可能设计 NestedScrollView “偷懒”了，没有加这个东西
   */
  override fun measureChildWithMargins(
    child: View,
    parentWidthMeasureSpec: Int,
    widthUsed: Int,
    parentHeightMeasureSpec: Int,
    heightUsed: Int
  ) {
    val lp = child.layoutParams as MarginLayoutParams

    val childWidthMeasureSpec = getChildMeasureSpec(
      parentWidthMeasureSpec,
      paddingLeft + paddingRight + lp.leftMargin + lp.rightMargin
          + widthUsed, lp.width
    )
    val usedTotal = paddingTop + paddingBottom + lp.topMargin + lp.bottomMargin + heightUsed
    val childHeightMeasureSpec: Int = MeasureSpec.makeMeasureSpec(
      max(0, MeasureSpec.getSize(parentHeightMeasureSpec) - usedTotal),
      MeasureSpec.UNSPECIFIED
    )

    child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
  }

  // 当前 requestDisallowInterceptTouchEvent 状态
  protected var mIsRequestedDisallowIntercept = false
    private set

  override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    super.requestDisallowInterceptTouchEvent(disallowIntercept)
    mIsRequestedDisallowIntercept = disallowIntercept
  }

  override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
      mIsRequestedDisallowIntercept = false
    }
    val result = super.dispatchTouchEvent(ev)
    when (ev.actionMasked) {
      MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
        if (mIsRequestedDisallowIntercept) {
          /**
           * 如果此时因为被 requestDisallow 导致 onInterceptTouchEvent 不被回调，就手动回调一次，用于清理状态
           * 主要是为了触发 stopNestedScroll()
           */
          super.onInterceptTouchEvent(ev)
        }
      }
    }
    return result
  }
}