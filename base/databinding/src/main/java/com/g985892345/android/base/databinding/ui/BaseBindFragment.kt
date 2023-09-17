package com.g985892345.android.base.databinding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.g985892345.android.base.ui.page.BaseFragment
import com.g985892345.android.extensions.android.isDebuggableBuild
import com.g985892345.android.extensions.android.mainHandler
import com.g985892345.android.extensions.android.toast
import com.g985892345.jvm.generics.GenericsUtils.getGenericClass
import java.lang.reflect.Method

/**
 *
 * 该类封装了 DataBind，可直接使用 [binding] 获得
 *
 * ## 零、使用 DataBinding 需要打开开关
 * ```
 * // 在你模块的 build.gradle.kts 中调用
 * useDataBinding()
 *
 * // 如果你只依赖 DataBinding 而不开启 DataBinding 这个功能，可以给上述函数传入 true 参数
 * ```
 *
 * ## 一、获取 ViewModel 的规范写法
 * 请查看该父类 [BaseFragment]
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
abstract class BaseBindFragment<VB : ViewBinding> : BaseFragment() {
  
  companion object {
    // VB inflate() 缓存。key 为 javaClass，value 为 VB 的 inflate 方法
    private val VB_METHOD_BY_CLASS = hashMapOf<Class<out BaseBindFragment<*>>, Method>()
  }

  @CallSuper
  abstract override fun onViewCreated(view: View, savedInstanceState: Bundle?)
  
  /**
   * 由于 View 的生命周期与 Fragment 不匹配，
   * 所以在 [onDestroyView] 后需要取消对 [binding] 的引用
   */
  private var _binding: VB? = null
  protected val binding: VB
    get() = _binding!!
  
  @CallSuper
  @Suppress("UNCHECKED_CAST")
  @Deprecated(
    "不建议重写该方法，请使用 onCreateViewBefore() 代替",
    ReplaceWith("onCreateViewBefore(container, savedInstanceState)"),
    DeprecationLevel.WARNING
  )
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = createBind(inflater, container, savedInstanceState)
    if (_binding is ViewDataBinding) {
      // ViewBinding 是 ViewBind 和 DataBind 共有的父类
      (binding as ViewDataBinding).lifecycleOwner = viewLifecycleOwner
    } else {
      // 目前掌邮更建议使用 DataBind，因为 ViewBind 是白名单模式，默认所有 xml 生成类，严重影响编译速度
      // 但 DataBind 不是很推荐使用双向绑定，因为 xml 中写代码以后很难维护
      if (isDebuggableBuild) {
        toast("更推荐使用 DataBind (Fragment: ${this::class.simpleName})")
      }
    }
    onCreateViewBefore(container, savedInstanceState)
    return binding.root
  }

  /**
   * 创建 binding
   *
   * 目前采用反射创建，如果为了性能，可以重写后使用如下方式；
   * ```
   * override fun createBind(
   *   inflater: LayoutInflater,
   *   container: ViewGroup?,
   *   savedInstanceState: Bundle?
   * ): XXXBinding {
   *   return XXXBinding.invoke(inflater, container, false)
   * }
   * ```
   */
  open fun createBind(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): VB {
    val method = VB_METHOD_BY_CLASS.getOrPut(javaClass) {
      getGenericClass<VB, ViewBinding>(javaClass).getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
      )
    }
    @Suppress("UNCHECKED_CAST")
    return method.invoke(null, inflater, container, false) as VB
  }
  
  /**
   * 在 [onCreateView] 中返回 View 前回调
   */
  @CallSuper
  open fun onCreateViewBefore(
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ) = Unit

  @CallSuper
  override fun onDestroyView() {
    super.onDestroyView()
    mainHandler.post {
      // 因为 binding 需要在 onDestroyView() 中置空
      // 但是置空是在父类中操作，会导致比子类先调用 (除非你把 super 写在末尾)
      // 所以为了优雅，可以发送一个 post，它会在 onDestroyView() 后回调的
      _binding = null
    }
  }
}