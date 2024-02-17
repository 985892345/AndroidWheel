package com.g985892345.android.base.ui.page

import android.content.Context
import android.view.View
import androidx.activity.ComponentDialog
import androidx.annotation.StyleRes
import androidx.lifecycle.LifecycleOwner
import com.g985892345.android.utils.view.bind.BindView

/**
 * 带有 Lifecycle 的 Dialog
 *
 * @author 985892345
 * @date 2023/10/26 19:06
 */
abstract class GxrBaseDialog : ComponentDialog, GxrBaseUi {

  constructor(context: Context) : super(context)

  constructor(context: Context, @StyleRes themeResId: Int) : super(context, themeResId)

  override val rootView: View
    get() = this.window!!.decorView

  override fun getViewLifecycleOwner(): LifecycleOwner = this

  final override fun <T : View> Int.view(): BindView<T> = BindView(this, this@GxrBaseDialog)

  // 是否已经创建了 ContentView
  private var mHasContentViewChanged = false

  // doOnContentViewChanged 添加的回调
  private val mOnCreateContentViewAction = ArrayList<(rootView: View) -> Any?>()

  final override fun doOnCreateContentView(action: (rootView: View) -> Any?) {
    if (mHasContentViewChanged) {
      if (action.invoke(rootView) != null) {
        mOnCreateContentViewAction.add(action)
      }
    } else mOnCreateContentViewAction.add(action)
  }

  override fun onContentChanged() {
    super.onContentChanged()
    if (mHasContentViewChanged) throw IllegalStateException("不允许多次调用 setContentView")
    mHasContentViewChanged = true
    val iterator = mOnCreateContentViewAction.iterator()
    while (iterator.hasNext()) {
      if (iterator.next().invoke(rootView) == null) {
        iterator.remove()
      }
    }
  }
}