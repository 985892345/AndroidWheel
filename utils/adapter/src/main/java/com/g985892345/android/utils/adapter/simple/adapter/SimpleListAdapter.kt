package com.g985892345.android.utils.adapter.simple.adapter

import android.content.res.Resources
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.g985892345.android.utils.adapter.simple.ISimpleList
import com.g985892345.android.utils.adapter.R
import com.g985892345.android.utils.adapter.simple.data.IAdapterData
import com.g985892345.android.utils.adapter.simple.controller.AdapterItemController
import com.g985892345.android.utils.adapter.simple.helper.ISimpleListAdapterHelper
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

/**
 * .
 *
 * @author 985892345
 * 2023/7/15 10:39
 */
class SimpleListAdapter internal constructor(
) : ListAdapter<IAdapterData, RecyclerView.ViewHolder>(itemCallback), ISimpleList {

  private val mControllerByViewType = SparseArray<AdapterItemController<out IAdapterData, *>>()
  private val mControllerByDataClass = HashMap<Class<*>, AdapterItemController<*, *>>()
  private val mControllers = mutableListOf<AdapterItemController<*, *>>()

  init {
    setHasStableIds(true) // 设置 stableId 后可以优化 notifyDataSetChanged
  }

  private var mOldList: List<IAdapterData>? = null
  private var mNewList: MutableList<IAdapterData>? = null

  override fun submitList(list: List<IAdapterData>?) {
    mOldList = currentList
    mNewList = list?.toMutableList()
    super.submitList(mNewList)
  }

  override fun submitList(list: List<IAdapterData>?, commitCallback: Runnable?) {
    mOldList = currentList
    mNewList = list?.toMutableList()
    super.submitList(mNewList, commitCallback)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val view = try {
      LayoutInflater.from(parent.context).inflate(viewType, parent, false)
    } catch (e: Resources.NotFoundException) {
      throw IllegalArgumentException(
        "viewType 只能为 layoutId，你是否修改了 getItemViewType 方法?", e
      )
    }
    val controller = mControllerByViewType[viewType]
    view.setTag(R.id.id_controller_by_viewHolder, controller)
    return controller.onCreate(view)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    onBindViewHolder(holder, position, emptyList())
  }

  override fun onBindViewHolder(
    holder: RecyclerView.ViewHolder,
    position: Int,
    payloads: List<Any>
  ) {
    var isOnBind = false
    payloads.forEach {
      when (it) {
        is ISimpleListAdapterHelper.ViewHolderCallback -> it.callback(holder)
        else -> isOnBind = true
      }
    }
    if (payloads.isEmpty() || isOnBind) {
      val data = getItem(position)
      holder.controller.onBind(data, holder, payloads)
    }
  }

  override fun getItemViewType(position: Int): Int {
    val data = getItem(position)
    val controller = mControllerByDataClass[data.javaClass]
      ?: throw IllegalStateException(
        "未注册 ${data.javaClass.name} 数据类对应的 Controller"
      )
    val layoutId = controller.layoutId
    val oldController = mControllerByViewType[layoutId]
    if (oldController == null) {
      mControllerByViewType[layoutId] = controller
    } else {
      if (oldController !== controller) {
        throw IllegalStateException("同一种类型的 item 只允许注册一个 Controller 实例")
      }
    }
    return layoutId
  }

  override fun getItemId(position: Int): Long {
    return getItem(position).stableId
  }

  fun addController(controller: AdapterItemController<*, *>) {
    mControllerByDataClass[controller.dataClass]?.let { oldController ->
      throw IllegalStateException("一个 dataClass 只允许绑定一个 Controller, " +
          "controller1 = ${oldController.javaClass.name}, " +
          "controller2 = ${controller.javaClass.name}, " +
          "dataClass = ${controller.dataClass.name}")
    }
    mControllerByDataClass[controller.dataClass] = controller
    mControllers.add(controller)
  }

  override fun submit(list: List<IAdapterData>) {
    submitList(list)
  }

  override fun insert(position: Int, data: IAdapterData) {
    mNewList?.let {
      it.add(position, data)
      notifyItemInserted(position)
    }
  }

  override fun remove(position: Int) {
    mNewList?.let {
      it.removeAt(position)
      notifyItemRemoved(position)
    }
  }

  override fun update(position: Int) {
    mNewList?.let {
      notifyItemChanged(position)
    }
  }

  override fun change(position: Int, newData: IAdapterData) {
    mNewList?.let {
      it[position] = newData
      notifyItemChanged(position)
    }
  }

  override fun move(from: Int, to: Int) {
    mNewList?.let {
      val temp = it[from]
      it[from] = it[to]
      it[to] = temp
      notifyItemMoved(from, to)
    }
  }

  fun get(position: Int): IAdapterData? {
    return mNewList?.get(position)
  }

  fun findController(dataClass: Class<out IAdapterData>): AdapterItemController<*, *>? {
    return mControllerByDataClass[dataClass]
  }

  override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
    super.onViewRecycled(holder)
    holder.controller.onViewRecycled(holder)
  }

  override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
    return holder.controller.onFailedToRecycleView(holder)
  }

  override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
    super.onViewAttachedToWindow(holder)
    holder.controller.onViewAttachedToWindow(holder)
  }

  override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
    super.onViewDetachedFromWindow(holder)
    holder.controller.onViewDetachedFromWindow(holder)
  }

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    mControllers.forEach {
      it.onAttachedToRecyclerView(recyclerView)
    }
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    mControllers.forEach {
      it.onDetachedFromRecyclerView(recyclerView)
    }
  }

  private val RecyclerView.ViewHolder.controller: AdapterItemController<*, *>
    get() = itemView.getTag(R.id.id_controller_by_viewHolder) as AdapterItemController<*, *>

  companion object {
    private val itemCallback = object : DiffUtil.ItemCallback<IAdapterData>() {
      override fun areItemsTheSame(oldItem: IAdapterData, newItem: IAdapterData): Boolean {
        return oldItem.javaClass == newItem.javaClass && oldItem.stableId == newItem.stableId
      }

      override fun areContentsTheSame(oldItem: IAdapterData, newItem: IAdapterData): Boolean {
        return oldItem == newItem
      }
    }
  }
}