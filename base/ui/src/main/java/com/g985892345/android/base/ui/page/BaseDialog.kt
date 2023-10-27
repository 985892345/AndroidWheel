package com.g985892345.android.base.ui.page

import android.content.Context
import android.view.View
import androidx.activity.ComponentDialog
import androidx.annotation.StyleRes
import androidx.lifecycle.LifecycleOwner

/**
 * .
 *
 * @author 985892345
 * @date 2023/10/26 19:06
 */
abstract class BaseDialog : ComponentDialog, BaseUi {

  constructor(context: Context) : super(context)

  constructor(context: Context, @StyleRes themeResId: Int) : super(context, themeResId)

  override val rootView: View
    get() = this.window!!.decorView

  override fun getViewLifecycleOwner(): LifecycleOwner = this

}