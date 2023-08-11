package com.g985892345.utils.simplelistadapter.controller

/**
 * .
 *
 * @author 985892345
 * 2023/8/11 22:33
 */
interface IAdapterData {
  val stableId: Long
  fun unstableEquals(data: IAdapterData): Boolean
}