package com.g985892345.extensions.android

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun LifecycleOwner.launch(block: suspend CoroutineScope.() -> Unit): Job {
  return lifecycleScope.launch(block = block)
}