package dev.olog.feature.library.dagger.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.olog.feature.library.library.LibraryFragmentViewModel
import dev.olog.feature.presentation.base.dagger.ViewModelKey

@Module
internal abstract class LibraryFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(LibraryFragmentViewModel::class)
    internal abstract fun provideViewModel(viewModel: LibraryFragmentViewModel): ViewModel

}