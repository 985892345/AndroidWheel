package com.g985892345.jvm.exception

import java.io.PrintWriter
import java.io.StringWriter

/**
 *
 *
 * @author 985892345 (Guo Xiangrui)
 * @email guo985892345@foxmail.com
 * @date 2022/8/28 14:31
 */

/**
 * 收集异常中最有用的信息
 */
fun Throwable.collectUsefulStackTrace(): String {
  val writer = StringWriter()
  val printWriter = PrintWriter(writer)
  printStackTrace(printWriter)
  var firstLine = Int.MAX_VALUE
  // 只保留：at 方法名(类名.kt:行号)
  val regex = Regex("(?<=at )([a-zA-Z\$]+\\.)+")
  val s = writer.buffer.replaceLine { lineIndex, old ->
    if (lineIndex < firstLine) {
      // 先找到第一行以 .kt 结尾的
      if (old.contains(".kt:")) {
        firstLine = lineIndex
      }
      old.replace(regex, "")
    } else {
      if (old.contains("\tat ") && !old.contains(".kt:")) {
        // 筛除无用信息
        ""
      } else old.replace(regex, "")
    }
  }
  printWriter.close()
  return s
}

fun CharSequence.replaceLine(action: (lineIndex: Int, old: String) -> String): String {
  val oldList = split("\n")
  val newList = arrayListOf<String>()
  oldList.forEachIndexed { index, old ->
    val new = action.invoke(index, old)
    if (new.isNotEmpty()) {
      newList.add(new)
    }
  }
  return newList.joinToString(separator = "\n")
}


