package com.g985892345.android.utils.adapter.simple.controller

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.g985892345.android.utils.adapter.simple.data.IAdapterData
import com.g985892345.jvm.generics.GenericsUtils

/**
 * .
 *
 * @author 985892345
 * 2023/8/11 22:33
 */
abstract class AdapterItemController<Data : IAdapterData, VH : RecyclerView.ViewHolder>(
  @LayoutRes val layoutId: Int
) {

  /**
   * Data 具体类型
   *
   * 默认使用反射获取，但这种写法需要子类的父类写明泛型类型，即只允许继承一次
   */
  open val dataClass: Class<Data> by lazy {
    GenericsUtils.getGenericClass<Data, IAdapterData>(javaClass)
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