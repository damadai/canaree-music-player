package dev.olog.feature.entry

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import dev.olog.feature.presentation.base.extensions.dip
import dev.olog.scrollhelper.ScrollHelper
import dev.olog.feature.presentation.base.extensions.findViewByIdNotRecursive
import dev.olog.navigation.screens.FragmentScreen

internal class SuperCerealScrollHelper(
    private val activity: FragmentActivity
) : ScrollHelper(
    activity = activity,
    fullScrollTop = false,
    fullScrollBottom = false,
    toolbarHeight = activity.dip(R.dimen.toolbar),
    tabLayoutHeight = activity.dip(R.dimen.tab),
    bottomSheetHeight = activity.dip(R.dimen.sliding_panel_peek),
    bottomNavigationHeight = activity.dip(R.dimen.bottom_navigation_height),
    restoreState = true
) {

//    override fun applyInsetsToList(fragment: Fragment, list: RecyclerView, toolbar: View?, tabLayout: View?) {
//        super.applyInsetsToList(fragment, list, toolbar, tabLayout)
//        if (fragment.tag?.startsWith(FragmentScreen.DETAIL.tag) == true){
//             apply only top padding
//            list.updatePadding(top = 0)
//        }

//        if (fragment is FolderTreeFragment){ TODO folder tree fragment has a viewpager tag
//            val crumbsWrapper = fragment.requireView().findViewById<View>(R.id.crumbsWrapper)
//            if (crumbsWrapper.marginTop < 1){
//                 margin not set yet
//                fragment.requireView().doOnPreDraw {
//                    crumbsWrapper.setMargin(top = toolbar!!.height + tabLayout!!.height)
//                    list.updatePadding(top = list.paddingTop + crumbsWrapper!!.height)
//                }
//            }
//        }
//    }

    override fun findBottomNavigation(): View? {
        return activity.findViewById(R.id.bottomNavigation)
    }

    override fun findBottomSheet(): View? {
        return activity.findViewById(R.id.slidingPanel)
    }

    override fun findFab(fragment: Fragment): View? {
        return fragment.requireView().findViewById(R.id.fab)
    }

    override fun findRecyclerView(fragment: Fragment): RecyclerView? {
        var recyclerView = fragment.requireView().findViewByIdNotRecursive<RecyclerView>(R.id.list)
        if (recyclerView == null && fragment.tag == FragmentScreen.SETTINGS.tag) {
            // preferences fragment has and internal list called `recycler_view`
            recyclerView = fragment.requireView().findViewByIdNotRecursive(R.id.recycler_view)
        }
        return recyclerView
    }

    override fun findTabLayout(fragment: Fragment): View? {
        val view : View? = when {
            isViewPagerChildTag(fragment.tag) -> {
                // search toolbar and tab layout in parent fragment
                fragment.parentFragment?.view
            }
            else -> fragment.view
        }
        return view?.findViewByIdNotRecursive(R.id.tabLayout)
    }

    override fun findToolbar(fragment: Fragment): View? {
        if (fragment.tag == FragmentScreen.QUEUE.tag){
            // for some reason when drag and drop in queue fragment, the queue became crazy
            return null
        }
        val view : View? = when {
            isViewPagerChildTag(fragment.tag) -> {
                // search toolbar and tab layout in parent fragment
                fragment.parentFragment?.view
            }
            fragment.tag == FragmentScreen.SETTINGS.tag -> fragment.parentFragment?.view
            else -> fragment.view
        }
        return view?.findViewByIdNotRecursive(R.id.toolbar)
    }

    override fun findViewPager(fragment: Fragment): View? {
        val tag = fragment.tag
        if (tag == FragmentScreen.LIBRARY_TRACKS.tag || tag == FragmentScreen.LIBRARY_PODCAST.tag) {
            return fragment.view?.findViewByIdNotRecursive(R.id.viewPager)
        }
        return null
    }

    override fun shouldSkipFragment(fragment: Fragment): Boolean {
        if (isViewPagerChildTag(fragment.tag)){
            return false
        }
//        if (fragment.tag == OfflineLyricsFragment.TAG) { TODO
//            return true
//        }
        return isPlayerTag(fragment.tag) || !hasFragmentOwnership(fragment.tag)
    }

    private fun isViewPagerChildTag(tag: String?) = tag?.startsWith("android:switcher:") == true

    private fun hasFragmentOwnership(tag: String?) = tag?.startsWith(FragmentScreen.OWNERSHIP) == true

    private fun isPlayerTag(tag: String?) = tag?.startsWith(FragmentScreen.PLAYER.tag) == true
}