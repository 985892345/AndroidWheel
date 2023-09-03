package com.g985892345.utils.simplelistadapter

import com.g985892345.utils.simplelistadapter.adapter.SimpleListAdapter
import com.g985892345.utils.simplelistadapter.data.IAdapterData
import com.g985892345.utils.simplelistadapter.helper.ISimpleListAdapterHelper
import com.g985892345.utils.simplelistadapter.helper.SimpleListHelper

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

  companion object {
    fun newAdapterHelper(): ISimpleListAdapterHelper {
      return SimpleListHelper(SimpleListAdapter())
    }
  }
}