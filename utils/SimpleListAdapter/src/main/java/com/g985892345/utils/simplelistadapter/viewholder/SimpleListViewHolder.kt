package com.g985892345.utils.simplelistadapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.g985892345.utils.simplelistadapter.R
import com.g985892345.utils.simplelistadapter.controller.AdapterItemController

/**
 * .
 *
 * @author 985892345
 * 2023/9/3 12:17
 */
open class SimpleListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  val controller: AdapterItemController<*, *> =
    itemView.getTag(R.id.controller_by_viewHolder) as AdapterItemController<*, *>


}