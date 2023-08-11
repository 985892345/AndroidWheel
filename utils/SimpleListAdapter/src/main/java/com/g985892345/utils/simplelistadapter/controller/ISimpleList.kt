package com.g985892345.utils.simplelistadapter.controller

import com.g985892345.utils.simplelistadapter.SimpleListAdapter

/**
 * .
 *
 * @author 985892345
 * 2023/8/11 22:33
 */
interface ISimpleList {
  fun submit(list: List<IAdapterData>)
  fun insert(position: Int, data: IAdapterData)
  fun remove(position: Int)
  fun update(position: Int)
  fun change(position: Int, newData: IAdapterData)
  fun move(from: Int, to: Int)
  fun get(position: Int): IAdapterData?
  fun findController(data: IAdapterData): IAdapterItemController<*, *>?

  companion object {
    fun newAdapter(
      controllers: List<IAdapterItemController<*, *>>
    ): SimpleListAdapter {
      return SimpleListAdapter(controllers)
    }
  }
}