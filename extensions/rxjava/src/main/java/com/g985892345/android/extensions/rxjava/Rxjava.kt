@file:Suppress("HasPlatformType")

package com.g985892345.android.extensions.rxjava

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers


/**
 * 不是很推荐你使用它，虽然很方便，但你知道这个的原理吗？
 */
fun <T : Any> Observable<T>.setSchedulers(
  subscribeOn: Scheduler = Schedulers.io(),
  unsubscribeOn: Scheduler = Schedulers.io(),
  observeOn: Scheduler = AndroidSchedulers.mainThread()
): Observable<T> = subscribeOn(subscribeOn)
  .unsubscribeOn(unsubscribeOn)
  .observeOn(observeOn)

