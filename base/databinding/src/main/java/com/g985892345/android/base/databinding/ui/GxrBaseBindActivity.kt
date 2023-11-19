package com.g985892345.android.base.databinding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.g985892345.android.base.ui.page.GxrBaseActivity
import com.g985892345.android.extensions.android.isDebuggableBuild
import com.g985892345.android.extensions.android.lazyUnlock
import com.g985892345.android.extensions.android.toast
import com.g985892345.jvm.generics.GenericsUtils.getGenericClass
import com.g985892345.android.base.ui.page.GxrBaseFragment
import java.lang.reflect.Method

/**
 *
 * 该类封装了 DataBind，可直接使用 [binding] 获得
 *
 * ## 一、获取 ViewModel 的规范写法
 * 请查看该父类 [GxrBaseFragment]
 *
 *
 *
 *
 *
 * # 更多封装请往父类和接口查看
 * @author 985892345
 * @email 2767465918@qq.com
 * @data 2021/6/2
 */
abstract class GxrBaseBindActivity<VB : ViewBinding> : GxrBaseActivity() {
  
  companion object {
    // VB inflate() 缓存。key 为 javaClass，value 为 VB 的 inflate 方法
    private val VB_METHOD_BY_CLASS = hashMapOf<Class<out GxrBaseBindActivity<*>>, Method>()
  }
  
  /**
   * 用于在调用 [setContentView] 之前的方法, 可用于设置一些主题或窗口的东西, 放这里不会报错
   */
  @CallSuper
  open fun onSetContentViewBefore() = Unit
  
  protected val binding: VB by lazyUnlock {
    val binding = createBind()
    if (binding is ViewDataBinding) {
      // ViewBinding 是 ViewBind 和 DataBind 共有的父类
      binding.lifecycleOwner = getViewLifecycleOwner()
    }
    binding
  }
  
  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    onSetContentViewBefore()
    super.setContentView(binding.root)
    // 注意：这里已经 setContentView()，请不要自己再次调用，否则 ViewBinding 会失效
  }


  /**
   * 创建 binding
   *
   * 目前采用反射创建，如果为了性能，可以重写后使用如下方式；
   * ```
   * override fun createBind(): XXXBinding {
   *   return XXXBinding.invoke(layoutInflater)
   * }
   * ```
   */
  open fun createBind(): VB {
    val method = VB_METHOD_BY_CLASS.getOrPut(javaClass) {
      getGenericClass<VB, ViewBinding>(javaClass).getMethod(
        "inflate",
        LayoutInflater::class.java,
      )
    }
    @Suppress("UNCHECKED_CAST")
    return method.invoke(null, layoutInflater) as VB
  }

  
  @Deprecated(
    "打个标记，因为使用了 ViewBinding，防止你忘记删除这个",
    level = DeprecationLevel.ERROR, replaceWith = ReplaceWith("")
  )
  override fun setContentView(layoutResID: Int) {
    super.setContentView(layoutResID)
  }
  
  @Deprecated(
    "打个标记，因为使用了 ViewBinding，防止你忘记删除这个",
    level = DeprecationLevel.ERROR, replaceWith = ReplaceWith("")
  )
  override fun setContentView(view: View) {
    super.setContentView(view)
  }
}