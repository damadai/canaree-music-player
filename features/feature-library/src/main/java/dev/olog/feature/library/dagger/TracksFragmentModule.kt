package dev.olog.feature.library.dagger

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dev.olog.feature.library.tracks.TracksFragment
import dev.olog.feature.library.tracks.TracksFragmentViewModel
import dev.olog.feature.presentation.base.dagger.ViewModelKey

@Module
internal abstract class TracksFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(TracksFragmentViewModel::class)
    internal abstract fun provideViewModel(viewModel: TracksFragmentViewModel): ViewModel

    companion object {

        @Provides
        fun provideIsPodcast(fragment: TracksFragment): Boolean {
            return fragment.requireArguments().getBoolean(TracksFragment.PODCAST)
        }

    }

}