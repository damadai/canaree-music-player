package dev.olog.feature.library.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import dev.olog.feature.library.R
import dev.olog.feature.presentation.base.SetupNestedList
import dev.olog.feature.presentation.base.activity.BaseFragment
import dev.olog.feature.presentation.base.adapter.ObservableAdapterNew
import dev.olog.navigation.Navigator
import dev.olog.scrollhelper.layoutmanagers.OverScrollLinearLayoutManager
import dev.olog.shared.lazyFast
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class HomeFragment : BaseFragment(), SetupNestedList {

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    @Inject
    internal lateinit var navigator: Navigator

    private val viewModel: HomeFragmentViewModel by viewModels { factory }

    private val adapter by lazyFast {
        HomeFragmentAdapter(this)
    }

    private val lastAlbumsAdapter by lazyFast {
        HomeFragmentNestedAdapter(navigator)
    }
    private val lastArtistsAdapter by lazyFast {
        HomeFragmentNestedAdapter(navigator)
    }
    private val newAlbumsAdapter by lazyFast {
        HomeFragmentNestedAdapter(navigator)
    }
    private val newArtistsAdapter by lazyFast {
        HomeFragmentNestedAdapter(navigator)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.adapter = adapter
        list.layoutManager = OverScrollLinearLayoutManager(requireContext())
        list.setHasFixedSize(true)

        viewModel.data
            .onEach { adapter.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.albumLastPlayed
            .onEach { lastAlbumsAdapter.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.albumRecentlyAdded
            .onEach { newAlbumsAdapter.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.artistLastPlayed
            .onEach { lastArtistsAdapter.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.artistRecentlyAdded
            .onEach { newArtistsAdapter.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

//        viewModel.observeData(TabCategory.LAST_PLAYED_PODCAST_ARTISTS)
//            .filterIsInstance<List<DisplayableAlbum>>()
//            .onEach { lastArtistsAdapter.submitList(it) }
//            .launchIn(viewLifecycleOwner.lifecycleScope)
//
//        viewModel.observeData(TabCategory.RECENTLY_ADDED_PODCAST_ARTISTS)
//            .filterIsInstance<List<DisplayableAlbum>>()
//            .onEach {  newArtistsAdapter.submitList(it)}
//            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun setupNestedList(layoutId: Int, recyclerView: RecyclerView) {
        when (layoutId) {
            R.layout.item_tab_last_played_album_horizontal_list -> {
                setupHorizontalList(recyclerView, lastAlbumsAdapter)
            }
            R.layout.item_tab_last_played_artist_horizontal_list -> {
                setupHorizontalList(recyclerView, lastArtistsAdapter)
            }
            R.layout.item_tab_new_album_horizontal_list -> {
                setupHorizontalList(recyclerView, newAlbumsAdapter)
            }
            R.layout.item_tab_new_artist_horizontal_list -> {
                setupHorizontalList(recyclerView, newArtistsAdapter)
            }
        }
    }

    private fun setupHorizontalList(list: RecyclerView, adapter: ObservableAdapterNew<*>) {
        val layoutManager = LinearLayoutManager(list.context, HORIZONTAL, false)
        list.layoutManager = layoutManager
        list.adapter = adapter
    }

    override fun provideLayoutId(): Int = R.layout.fragment_home
}