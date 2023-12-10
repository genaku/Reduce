package com.genaku.reducex

import androidx.lifecycle.LifecycleOwner
import com.genaku.reduce.JobSwitcher

fun JobSwitcher.connectTo(owner: LifecycleOwner) {
    owner.lifecycle.addObserver(KnotLifecycleObserver(this))
}