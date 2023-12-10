package com.genaku.reducex

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.genaku.reduce.JobSwitcher

class KnotLifecycleObserver(
    private val jobSwitcher: JobSwitcher,
) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        jobSwitcher.start(owner.lifecycleScope)
    }

    override fun onPause(owner: LifecycleOwner) {
        jobSwitcher.stop()
        super.onPause(owner)
    }
}