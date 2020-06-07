package dev.olog.feature.entry

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dev.olog.feature.presentation.base.CanChangeStatusBarColor
import dev.olog.feature.presentation.base.FloatingWindow
import dev.olog.feature.presentation.base.activity.HasSlidingPanel
import dev.olog.feature.presentation.base.extensions.isExpanded
import dev.olog.feature.presentation.base.extensions.removeLightStatusBar
import dev.olog.feature.presentation.base.extensions.setLightStatusBar
import dev.olog.shared.android.theme.themeManager
import dev.olog.core.isMarshmallow
import dev.olog.shared.lazyFast
import java.lang.ref.WeakReference
import javax.inject.Inject

internal class StatusBarColorBehavior @Inject constructor(
    fragmentActivity: FragmentActivity
) : FragmentManager.OnBackStackChangedListener {

    private val observer = object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            val activity = activityRef.get() ?: return

            if (!isMarshmallow()){
                return
            }

            slidingPanel?.addBottomSheetCallback(slidingPanelListener)
            activity.supportFragmentManager.addOnBackStackChangedListener(this@StatusBarColorBehavior)
        }

        override fun onPause(owner: LifecycleOwner) {
            val activity = activityRef.get() ?: return

            if (!isMarshmallow()){
                return
            }

            slidingPanel?.removeBottomSheetCallback(slidingPanelListener)
            activity.supportFragmentManager.removeOnBackStackChangedListener(this@StatusBarColorBehavior)
        }
    }

    private val activityRef = WeakReference(fragmentActivity)

    private val slidingPanel: BottomSheetBehavior<*>? by lazyFast {
        val activity = activityRef.get() ?: return@lazyFast null
        (activity as HasSlidingPanel).getSlidingPanel()
    }

    init {
        fragmentActivity.lifecycle.addObserver(observer)
    }

    override fun onBackStackChanged() {
        val activity = activityRef.get() ?: return

        if (!isMarshmallow()){
            return
        }

        val fragment = searchFragmentWithLightStatusBar(activity)
        if (fragment == null){
            activity.window.setLightStatusBar()
        } else {
            when {
                fragment is FloatingWindow -> fragment.adjustStatusBarColor()
                slidingPanel?.isExpanded() == true -> activity.window.setLightStatusBar()
                else -> fragment.adjustStatusBarColor()
            }
        }
    }

    private fun searchFragmentWithLightStatusBar(activity: FragmentActivity): CanChangeStatusBarColor? {
        val fm = activity.supportFragmentManager
        val backStackEntryCount = fm.backStackEntryCount - 1
        if (backStackEntryCount > -1) {
            val entry = fm.getBackStackEntryAt(backStackEntryCount)
            val fragment = fm.findFragmentByTag(entry.name)
            if (fragment is CanChangeStatusBarColor) {
                return fragment
            }
        }
        return null
    }

    private val slidingPanelListener = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
        }

        @SuppressLint("SwitchIntDef")
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            val activity = activityRef.get() ?: return

            when (newState) {
                BottomSheetBehavior.STATE_EXPANDED -> {
                    val playerAppearance = (activity.themeManager.playerAppearance)
                    if (playerAppearance.isFullscreen || playerAppearance.isBigImage) {
                        activity.window.removeLightStatusBar()
                    } else {
                        activity.window.setLightStatusBar()
                    }
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    searchFragmentWithLightStatusBar(activity)?.adjustStatusBarColor() ?: activity.window.setLightStatusBar()
                }
            }
        }
    }

}