package com.genaku.reduce.sms

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import java.util.concurrent.atomic.AtomicBoolean

inline fun <T> LifecycleOwner.observeState(
    flow: StateFlow<T>,
    crossinline action: (value: T) -> Unit
) {
    lifecycleScope.launchWhenResumed {
        flow.collect {
            action(it)
        }
    }
}

inline fun <T> LifecycleOwner.observeEvent(
    flow: StateFlow<Event<T>>,
    crossinline action: (value: T) -> Unit
) {
    lifecycleScope.launchWhenResumed {
        flow.collect {
            it.getEventIfNotHandled()?.run {
                action(this)
            }
        }
    }
}

/**
 * Used as a wrapper for data that is exposed via ViewEventFlow that represents an event.
 */
open class Event<out T>(val content: T?) {

    private val hasBeenHandled = AtomicBoolean(false)

    /**
     * Returns the content and prevents its use again.
     */
    fun getEventIfNotHandled(): T? = if (hasBeenHandled.get()) {
        null
    } else {
        hasBeenHandled.set(true)
        content
    }
}