package com.g985892345.androidwheel.controller

import android.view.View
import android.widget.TextView
import com.g985892345.androidwheel.R
import com.g985892345.androidwheel.data.TestData
import com.g985892345.utils.simplelistadapter.controller.AdapterItemController
import com.g985892345.utils.simplelistadapter.viewholder.SimpleListViewHolder

/**
 * .
 *
 * @author 985892345
 * 2023/9/3 15:49
 */
class TestController : AdapterItemController<TestData, TestController.TestViewHolder>(
  R.layout.list_item
) {
  class TestViewHolder(itemView: View) : SimpleListViewHolder(itemView) {
    val tvStuNum = itemView.findViewById<TextView>(R.id.tv_stuNum)
    val tvStuName = itemView.findViewById<TextView>(R.id.tv_stuName)
    val tvAge = itemView.findViewById<TextView>(R.id.tv_age)
  }

  override fun onCreate(view: View): TestViewHolder {
    return TestViewHolder(view)
  }

  override fun onBind(data: TestData, holder: TestViewHolder, payloads: List<Any>) {
    holder.apply {
      tvStuNum.text = data.stuNum
      tvStuName.text = data.stuName
      tvAge.text = data.age.toString()
    }
  }
}