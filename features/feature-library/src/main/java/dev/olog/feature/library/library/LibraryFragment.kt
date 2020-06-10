package dev.olog.feature.library.library

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dev.olog.feature.library.R
import dev.olog.feature.library.tracks.TracksFragment
import dev.olog.feature.presentation.base.activity.BaseFragment
import dev.olog.navigation.screens.FragmentScreen
import dev.olog.navigation.screens.LibraryPage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class LibraryFragment : BaseFragment() {

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    private val viewModel: LibraryFragmentViewModel by viewModels { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentLibraryPage
            .onEach { navigate(it) }
            .launchIn(lifecycleScope)
    }

    private fun navigate(page: LibraryPage) {
        // TODO
        val fragment = TracksFragment.newInstance(false)
        val tag = "${FragmentScreen.OWNERSHIP}.library.tracks"

        childFragmentManager.beginTransaction()
            .replace(R.id.content, fragment, tag)
            .commit()
    }

    override fun provideLayoutId(): Int = R.layout.fragment_library
}