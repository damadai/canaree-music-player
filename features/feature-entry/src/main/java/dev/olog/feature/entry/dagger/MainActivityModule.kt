package dev.olog.feature.entry.dagger

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dev.olog.feature.entry.MainActivity
import dev.olog.feature.entry.MainActivityViewModel
import dev.olog.feature.entry.widgets.CanareeBottomNavigationView
import dev.olog.feature.presentation.base.dagger.ViewModelKey
import dev.olog.lib.media.MediaProvider

@Module
internal abstract class MainActivityModule {

    @Binds
    internal abstract fun provideFragmentActivity(instance: MainActivity): FragmentActivity

    @Binds
    internal abstract fun provideMusicGlue(instance: MainActivity): MediaProvider

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun provideViewModel(impl: MainActivityViewModel): ViewModel

    // TODO probably won't work, use a real subcomponent
    @ContributesAndroidInjector
    internal abstract fun provideBottomSheetFragment(): CanareeBottomNavigationView

}