package com.g985892345.android.utils.adapter.simple

import com.g985892345.android.utils.adapter.simple.adapter.SimpleListAdapter
import com.g985892345.android.utils.adapter.simple.data.IAdapterData
import com.g985892345.android.utils.adapter.simple.helper.ISimpleListAdapterHelper
import com.g985892345.android.utils.adapter.simple.helper.SimpleListHelper

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