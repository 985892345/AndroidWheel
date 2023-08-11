package com.g985892345.utils.simplelistadapter

import android.content.res.Resources
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.g985892345.utils.simplelistadapter.controller.IAdapterData
import com.g985892345.utils.simplelistadapter.controller.IAdapterItemController
import com.g985892345.utils.simplelistadapter.controller.ISimpleList
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

/**
 * .
 *
 * @author 985892345
 * 2023/7/15 10:39
 */
class SimpleListAdapter internal constructor(
  controller: List<IAdapterItemController<*, *>>
) : ListAdapter<IAdapterData, RecyclerView.ViewHolder>(
  object : DiffUtil.ItemCallback<IAdapterData>() {
    override fun areItemsTheSame(oldItem: IAdapterData, newItem: IAdapterData): Boolean {
      return oldItem.javaClass == newItem.javaClass && oldItem.stableId == newItem.stableId
    }

    override fun areContentsTheSame(oldItem: IAdapterData, newItem: IAdapterData): Boolean {
      return oldItem.unstableEquals(newItem)
    }
  }
), ISimpleList {

  private val mControllerByViewType = SparseArray<IAdapterItemController<out IAdapterData, *>>()
  private val mControllerByDataClass = HashMap<Class<*>, IAdapterItemController<*, *>>()

  init {
    setHasStableIds(true)
    controller.forEach {
      mControllerByDataClass[it.dataClass] = it
    }
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
    return controller.onCreate(view)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val data = getItem(position)
    val controller = mControllerByDataClass[data.javaClass]
      ?: throw IllegalStateException(
        "未注册 ${data.javaClass.simpleName} 数据类对应的 IAdapterItemController"
      )
    controller.onBind(data, holder)
  }

  override fun getItemViewType(position: Int): Int {
    val data = getItem(position)
    val controller = mControllerByDataClass[data.javaClass]
      ?: throw IllegalStateException(
        "未注册 ${data.javaClass.simpleName} 数据类对应的 IAdapterItemController"
      )
    val layoutId = controller.layoutId
    val oldController = mControllerByViewType[layoutId]
    if (oldController == null) {
      mControllerByViewType[layoutId] = controller
    } else {
      if (oldController !== controller) {
        throw IllegalStateException("同一种类型的 item 只允许注册一个 IAdapterItemCOntroller 实例")
      }
    }
    return layoutId
  }

  override fun getItemId(position: Int): Long {
    return getItem(position).stableId
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

  override fun get(position: Int): IAdapterData? {
    return mNewList?.get(position)
  }

  override fun findController(data: IAdapterData): IAdapterItemController<*, *>? {
    return mControllerByDataClass[data.javaClass]
  }
}