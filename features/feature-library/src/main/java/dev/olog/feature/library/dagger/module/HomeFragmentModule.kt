package dev.olog.feature.library.dagger.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.olog.feature.library.home.HomeFragmentViewModel
import dev.olog.feature.presentation.base.dagger.ViewModelKey

@Module
internal abstract class HomeFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeFragmentViewModel::class)
    internal abstract fun provideViewModel(viewModel: HomeFragmentViewModel): ViewModel

}