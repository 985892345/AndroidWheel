package com.g985892345.androidwheel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.g985892345.androidwheel.controller.TestController
import com.g985892345.androidwheel.data.TestData
import com.g985892345.utils.simplelistadapter.ISimpleList

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val rv = findViewById<RecyclerView>(R.id.rv)

    val adapterHelper = ISimpleList.newAdapterHelper()
    rv.adapter = adapterHelper.adapter
    rv.layoutManager = LinearLayoutManager(this)

    adapterHelper.addController(TestController())

    adapterHelper.submit(
      listOf(
        TestData("1", "a", 10),
        TestData("2", "b", 20),
        TestData("3", "c", 30),
        TestData("4", "d", 40),
        TestData("5", "e", 50),
        TestData("6", "f", 60),
        TestData("7", "g", 70),
        TestData("8", "h", 80),
        TestData("9", "i", 90),
      )
    )

    ItemTouchHelper(object : ItemTouchHelper.Callback() {
      override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
      ): Int {
        return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0)
      }

      override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
      ): Boolean {
        adapterHelper.move(viewHolder.layoutPosition, target.layoutPosition)
        return true
      }

      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
      }
    }).apply {

    }.attachToRecyclerView(rv)
  }
}