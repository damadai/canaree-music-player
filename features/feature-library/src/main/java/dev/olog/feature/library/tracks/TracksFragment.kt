package dev.olog.feature.library.tracks

import android.os.Bundle
import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import dev.olog.domain.entity.sort.SortType
import dev.olog.feature.library.R
import dev.olog.feature.presentation.base.DottedDividerDecorator
import dev.olog.feature.presentation.base.activity.BaseFragment
import dev.olog.feature.presentation.base.extensions.argument
import dev.olog.feature.presentation.base.extensions.scrollToPositionWithOffset
import dev.olog.feature.presentation.base.extensions.withArguments
import dev.olog.feature.presentation.base.model.PresentationId
import dev.olog.feature.presentation.base.widget.fastscroller.WaveSideBarView
import dev.olog.lib.media.MediaProvider
import dev.olog.navigation.Navigator
import dev.olog.scrollhelper.layoutmanagers.OverScrollLinearLayoutManager
import dev.olog.shared.TextUtils
import dev.olog.shared.lazyFast
import kotlinx.android.synthetic.main.fragment_tracks.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class TracksFragment : BaseFragment() {

    companion object {

        const val PODCAST = "podcast"

        fun newInstance(podcast: Boolean): TracksFragment {
            return TracksFragment().withArguments(
                PODCAST to podcast
            )
        }

    }

    private val isPodcast by argument<Boolean>(PODCAST)

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    @Inject
    internal lateinit var navigator: Navigator

    private val viewModel: TracksFragmentViewModel by viewModels { factory }

    private val adapter by lazyFast {
        TrackFragmentAdapter(viewModel, navigator, requireActivity() as MediaProvider)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.layoutManager = OverScrollLinearLayoutManager(requireContext())
        list.adapter = adapter
        list.setHasFixedSize(true)

        val decorator = DottedDividerDecorator(requireContext(), listOf(R.layout.item_tab_shuffle))
        list.addItemDecoration(decorator)

        viewModel.data
            .onEach { adapter.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.sidebarLetters
            .onEach { sidebar.updateLetters(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        sidebar.scrollableLayoutId = if (isPodcast) {
            R.layout.item_podcast
        } else {
            R.layout.item_track
        }

        if (isPodcast) {
            viewModel.podcastPositions
                .onEach { adapter.updatePodcastPositions(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onResume() {
        super.onResume()
        sidebar.setListener(letterTouchListener)
    }

    override fun onPause() {
        super.onPause()
        sidebar.setListener(null)
    }

    override fun onCurrentPlayingChanged(mediaId: PresentationId.Track) {
        adapter.onCurrentPlayingChanged(mediaId)
    }

    private val letterTouchListener = WaveSideBarView.OnTouchLetterChangeListener { letter ->
        list.stopScroll()

        val scrollableItem = sidebar.scrollableLayoutId

        val position = when (letter) {
            TextUtils.MIDDLE_DOT -> RecyclerView.NO_POSITION
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
        if (position != RecyclerView.NO_POSITION) {
            list.scrollToPositionWithOffset(position, 0)
        }
    }

    private fun getCurrentSorting(item: TracksModel): String {
        require(item is TracksModel.Item)
        if (item.isPodcast) {
            return item.title
        }
        val sortOrder = viewModel.getAllTracksSortOrder()
        return when (sortOrder.type) {
            SortType.ARTIST -> item.artist
            SortType.ALBUM -> item.album
            else -> item.title
        }
    }

    override fun provideLayoutId(): Int = R.layout.fragment_tracks
}