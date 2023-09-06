package com.g985892345.android.utils.adapter.simple.helper

import androidx.recyclerview.widget.RecyclerView
import com.g985892345.android.utils.adapter.simple.ISimpleList
import com.g985892345.android.utils.adapter.simple.adapter.SimpleListAdapter
import com.g985892345.android.utils.adapter.simple.data.IAdapterData
import com.g985892345.android.utils.adapter.simple.controller.AdapterItemController

/**
 * .
 *
 * @author 985892345
 * 2023/8/27 23:13
 */
open class SimpleListHelper(
  override val adapter: SimpleListAdapter
) : ISimpleListAdapterHelper {

  protected val mDataFlitter = arrayListOf<(List<IAdapterData>) -> List<IAdapterData>>()

  override var delegate: ISimpleList? = null

  override fun addController(controller: AdapterItemController<*, *>): ISimpleListAdapterHelper {
    adapter.addController(controller)
    return this
  }

  override fun get(position: Int): IAdapterData? = adapter.get(position)

  override fun getViewHolder(position: Int, action: ISimpleListAdapterHelper.ViewHolderCallback) {
    adapter.notifyItemChanged(position, action)
  }

  override fun addDataFlitter(flitter: (List<IAdapterData>) -> List<IAdapterData>) {
    mDataFlitter.add(flitter)
  }

  override fun findController(dataClass: Class<out IAdapterData>): AdapterItemController<*, *>? =
    adapter.findController(dataClass)

  override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) =
    adapter.registerAdapterDataObserver(observer)

  override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) =
    adapter.unregisterAdapterDataObserver(observer)

  override fun submit(list: List<IAdapterData>) {
    var newList = list
    mDataFlitter.forEach { newList = it.invoke(newList) }
    getImpl().submit(newList)
  }

  override fun insert(position: Int, data: IAdapterData) = getImpl().insert(position, data)

  override fun remove(position: Int) = getImpl().remove(position)

  override fun update(position: Int) = getImpl().update(position)

  override fun change(position: Int, newData: IAdapterData) = getImpl().change(position, newData)

  override fun move(from: Int, to: Int) = getImpl().move(from, to)

  private fun getImpl(): ISimpleList = delegate ?: adapter
}