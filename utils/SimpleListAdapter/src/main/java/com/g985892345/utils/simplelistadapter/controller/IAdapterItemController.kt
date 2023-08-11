package com.g985892345.utils.simplelistadapter.controller

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * .
 *
 * @author 985892345
 * 2023/8/11 22:33
 */
abstract class IAdapterItemController<Data : IAdapterData, VH : RecyclerView.ViewHolder>(
  @LayoutRes val layoutId: Int,
  val dataClass: Class<Data>
) {
  abstract fun onCreate(view: View): VH
  abstract fun onBind(data: @UnsafeVariance Data, holder: @UnsafeVariance VH)
}