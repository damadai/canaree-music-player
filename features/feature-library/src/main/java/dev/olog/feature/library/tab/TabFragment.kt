package dev.olog.feature.library.tab

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.core.text.isDigitsOnly
import androidx.core.view.doOnLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionManager
import dev.olog.domain.entity.sort.SortType
import dev.olog.feature.library.R
import dev.olog.feature.library.model.TabCategory
import dev.olog.feature.library.model.toTabCategory
import dev.olog.feature.library.tab.adapter.TabFragmentAdapter
import dev.olog.feature.library.tab.layout.manager.AbsSpanSizeLookup
import dev.olog.feature.library.tab.layout.manager.LayoutManagerFactory
import dev.olog.feature.presentation.base.DottedDividerDecorator
import dev.olog.feature.presentation.base.activity.BaseFragment
import dev.olog.feature.presentation.base.extensions.awaitAnimationEnd
import dev.olog.feature.presentation.base.extensions.getArgument
import dev.olog.feature.presentation.base.extensions.withArguments
import dev.olog.feature.presentation.base.model.DisplayableAlbum
import dev.olog.feature.presentation.base.model.DisplayableItem
import dev.olog.feature.presentation.base.model.DisplayableTrack
import dev.olog.feature.presentation.base.model.PresentationIdCategory
import dev.olog.feature.presentation.base.widget.fastscroller.WaveSideBarView
import dev.olog.navigation.Navigator
import dev.olog.shared.TextUtils
import dev.olog.shared.lazyFast
import kotlinx.android.synthetic.main.fragment_tab.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class TabFragment : BaseFragment() {

    companion object {
        @JvmStatic
        private val TAG = TabFragment::class.java.name
        const val ARGUMENTS_SOURCE = "dataSource"

        @JvmStatic
        fun newInstance(category: PresentationIdCategory): TabFragment {
            return TabFragment().withArguments(
                ARGUMENTS_SOURCE to category
            )
        }
    }

    @Inject
    internal lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by activityViewModels<TabFragmentViewModel> {
        viewModelFactory
    }

    internal val category: TabCategory by lazyFast {
        getArgument<PresentationIdCategory>(ARGUMENTS_SOURCE).toTabCategory()
    }

    private val adapter by lazyFast {
        TabFragmentAdapter(navigator)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val requestedSpanSize = viewModel.getSpanCount(category)
        val gridLayoutManager = LayoutManagerFactory.get(list, category, adapter, requestedSpanSize)
        list.layoutManager = gridLayoutManager
        list.adapter = adapter
        list.setHasFixedSize(true)
        list.addItemDecoration(
            DottedDividerDecorator(
                requireContext(), listOf(R.layout.item_tab_header, R.layout.item_tab_shuffle)
            )
        )

        val scrollableLayoutId = when (category) {
            TabCategory.SONGS -> R.layout.item_tab_song
            TabCategory.PODCASTS -> R.layout.item_tab_podcast
            TabCategory.ARTISTS -> R.layout.item_tab_artist
            else -> R.layout.item_tab_album
        }
        sidebar.scrollableLayoutId = scrollableLayoutId

        viewModel.observeData(category)
            .onEach { list ->
                adapter.submitList(list)
                sidebar.onDataChanged(list)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.observeSpanCount(category)
            .onEach { span ->
                list.awaitAnimationEnd()
                list.doOnLayout {
                    // TODO check
                    TransitionManager.beginDelayedTransition(list)
                    (gridLayoutManager.spanSizeLookup as AbsSpanSizeLookup).requestedSpanSize = span
                    adapter.notifyDataSetChanged()
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        list.adapter = null
    }

    override fun onResume() {
        super.onResume()
        sidebar.setListener(letterTouchListener)
        fab.setOnClickListener { fab ->
//            navigator.toChooseTracksForPlaylistFragment(type, sharedFab)
        }
    }

    override fun onPause() {
        super.onPause()
        sidebar.setListener(null)
        fab.setOnClickListener(null)
    }

    private val letterTouchListener = WaveSideBarView.OnTouchLetterChangeListener { letter ->
        list.stopScroll()

        val scrollableItem = sidebar.scrollableLayoutId

        val position = when (letter) {
            TextUtils.MIDDLE_DOT -> -1
            "#" -> adapter.indexOf {
                if (it.type != scrollableItem) {
                    false
                } else {
                    val sorting = getCurrentSorting(it)
                    if (sorting.isBlank()) false
                    else sorting[0].toUpperCase().toString().isDigitsOnly()
                }
            }
            "?" -> adapter.indexOf {
                if (it.type != scrollableItem) {
                    false
                } else {
                    val sorting = getCurrentSorting(it)
                    if (sorting.isBlank()) false
                    else sorting[0].toUpperCase().toString() > "Z"
                }
            }
            else -> adapter.indexOf {
                if (it.type != scrollableItem) {
                    false
                } else {
                    val sorting = getCurrentSorting(it)
                    if (sorting.isBlank()) false
                    else sorting[0].toUpperCase().toString() == letter
                }
            }
        }
        if (position != -1) {
            val layoutManager = list.layoutManager as GridLayoutManager
            layoutManager.scrollToPositionWithOffset(position, 0)
        }
    }

    private fun getCurrentSorting(item: DisplayableItem): String {
        return when (category) {
            TabCategory.SONGS,
            TabCategory.PODCASTS -> {
                require(item is DisplayableTrack)
                val sortOrder = viewModel.getAllTracksSortOrder()
                when (sortOrder.type) {
                    SortType.ARTIST -> item.artist
                    SortType.ALBUM -> item.album
                    else -> item.title
                }
            }
            TabCategory.ALBUMS -> {
                require(item is DisplayableAlbum)
                val sortOrder = viewModel.getAllAlbumsSortOrder()
                when (sortOrder.type) {
                    SortType.TITLE -> item.title
                    else -> item.subtitle
                }
            }
            else -> {
                require(item is DisplayableAlbum)
                item.title
            }
        }
    }

    override fun provideLayoutId(): Int = R.layout.fragment_tab
}