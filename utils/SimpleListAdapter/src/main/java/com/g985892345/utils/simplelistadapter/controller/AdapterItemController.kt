package com.g985892345.utils.simplelistadapter.controller

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.g985892345.utils.simplelistadapter.data.IAdapterData
import com.g985892345.utils.simplelistadapter.utils.GenericityUtils
import com.g985892345.utils.simplelistadapter.viewholder.SimpleListViewHolder

/**
 * .
 *
 * @author 985892345
 * 2023/8/11 22:33
 */
abstract class AdapterItemController<Data : IAdapterData, VH : SimpleListViewHolder>(
  @LayoutRes val layoutId: Int
) {

  /**
   * Data 具体类型
   *
   * 默认使用反射获取
   */
  open val dataClass: Class<Data> by lazy {
    GenericityUtils.getGenericClass<Data, IAdapterData>(javaClass)
  }

  abstract fun onCreate(view: View): VH

  abstract fun onBind(data: @UnsafeVariance Data, holder: @UnsafeVariance VH, payloads: List<Any>)

  open fun onViewRecycled(holder: @UnsafeVariance VH) = Unit

  open fun onFailedToRecycleView(holder: @UnsafeVariance VH): Boolean = false

  open fun onViewAttachedToWindow(holder: @UnsafeVariance VH) = Unit

  open fun onViewDetachedFromWindow(holder: @UnsafeVariance VH) = Unit

  open fun onAttachedToRecyclerView(recyclerView: RecyclerView) = Unit

  open fun onDetachedFromRecyclerView(recyclerView: RecyclerView) = Unit

}