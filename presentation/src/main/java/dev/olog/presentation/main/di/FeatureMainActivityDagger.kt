package dev.olog.presentation.main.di

import dagger.Subcomponent
import dagger.android.AndroidInjector
import dev.olog.core.dagger.FeatureScope
import dev.olog.feature.entry.MainActivity
import dev.olog.presentation.createplaylist.di.CreatePlaylistFragmentInjector
import dev.olog.presentation.dialogs.DialogModule
import dev.olog.presentation.recentlyadded.di.RecentlyAddedFragmentInjector
import dev.olog.presentation.relatedartists.di.RelatedArtistFragmentInjector

class FeatureMainActivityDagger {

    @Subcomponent(
        modules = [
//        // fragments
            RecentlyAddedFragmentInjector::class,
            RelatedArtistFragmentInjector::class,
            CreatePlaylistFragmentInjector::class,

            DialogModule::class
        ]
    )
    @FeatureScope
    internal interface Graph : AndroidInjector<MainActivity> {

        @Subcomponent.Factory
        interface Factory : AndroidInjector.Factory<MainActivity>

    }


}