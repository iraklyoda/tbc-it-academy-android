package com.iraklyoda.categoryapp.presentation.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun Fragment.viewLifecycleScope(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block.invoke(this)
        }
    }
}

fun <T> Fragment.collectLatest(flow: Flow<T>, action: suspend (value: T) -> Unit) {
    viewLifecycleScope {
        flow.collectLatest {
            action.invoke(it)
        }
    }
}

fun <T> Fragment.collect(flow: Flow<T>, action: suspend (value: T) -> Unit) {
    viewLifecycleScope {
        flow.collect {
            action.invoke(it)
        }
    }
}