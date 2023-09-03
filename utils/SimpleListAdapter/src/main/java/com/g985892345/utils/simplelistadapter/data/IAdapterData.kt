package com.g985892345.utils.simplelistadapter.data

import java.util.Objects

/**
 * .
 *
 * @author 985892345
 * 2023/8/11 22:33
 */
interface IAdapterData {

  /**
   * 如果一个学生的数据类拥有以下字段：
   * 1. 学号
   * 2. 姓名
   * 3. 年龄
   *
   * 则该方法应该返回 学号 和 姓名 的 hash 值，因为他们是稳定不变的
   *
   * ```
   * // 可以使用提供的 getHashStableId 方法
   * // 但是请注意：对于稳定不变的字段，应该只需要计算一次，所以重写时以变量的形式重写
   * val stableId: Long = getHashStableId(stuNum, StuName)
   * ```
   */
  val stableId: Long

  /**
   * 强制性要求重写，用于判断所有字段是否相等
   */
  override fun equals(other: Any?): Boolean

  companion object : IAdapterData {
    fun getHashStableId(vararg any: Any): Long {
      return when {
        any.isEmpty() -> 0L
        any.size == 1 -> any.hashCode().toLong()
        else -> Objects.hash(any).toLong()
      }
    }

    override val stableId: Long
      get() = 0

    override fun equals(other: Any?): Boolean {
      return other === this
    }
  }
}