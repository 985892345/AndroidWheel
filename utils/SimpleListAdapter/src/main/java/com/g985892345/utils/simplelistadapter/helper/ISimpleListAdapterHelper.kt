package com.g985892345.utils.simplelistadapter.helper

import androidx.recyclerview.widget.RecyclerView
import com.g985892345.utils.simplelistadapter.controller.AdapterItemController
import com.g985892345.utils.simplelistadapter.ISimpleList
import com.g985892345.utils.simplelistadapter.data.IAdapterData
import com.g985892345.utils.simplelistadapter.viewholder.SimpleListViewHolder

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
    fun call(holder: SimpleListViewHolder)
  }
}