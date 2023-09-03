package com.g985892345.androidwheel.data

import com.g985892345.utils.simplelistadapter.data.IAdapterData

/**
 * .
 *
 * @author 985892345
 * 2023/9/3 15:50
 */
data class TestData(
  val stuNum: String,
  val stuName: String,
  val age: Int
) : IAdapterData {
  override val stableId: Long = IAdapterData.getHashStableId(stuNum, stuName)
}