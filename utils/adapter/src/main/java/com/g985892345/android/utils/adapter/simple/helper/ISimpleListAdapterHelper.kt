package com.g985892345.android.utils.adapter.simple.helper

import androidx.recyclerview.widget.RecyclerView
import com.g985892345.android.utils.adapter.simple.controller.AdapterItemController
import com.g985892345.android.utils.adapter.simple.ISimpleList
import com.g985892345.android.utils.adapter.simple.data.IAdapterData

/**
 * .
 *
 * @author 985892345
 * 2023/9/3 11:21
 */
interface ISimpleListAdapterHelper : ISimpleList {

  val adapter: RecyclerView.Adapter<*>

  var delegate: ISimpleList?

  fun addController(controller: AdapterItemController<*, *>): ISimpleListAdapterHelper

  fun get(position: Int): IAdapterData?

  fun getViewHolder(position: Int, action: ViewHolderCallback)

  fun addDataFlitter(flitter: (List<IAdapterData>) -> List<IAdapterData>)

  fun findController(dataClass: Class<out IAdapterData>): AdapterItemController<*, *>?

  fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver)

  fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver)

  fun interface ViewHolderCallback {
    fun callback(holder: RecyclerView.ViewHolder)
  }
}