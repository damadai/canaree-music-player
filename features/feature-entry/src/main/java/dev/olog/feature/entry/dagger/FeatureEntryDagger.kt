package dev.olog.feature.entry.dagger

import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import dev.olog.core.dagger.FeatureScope
import dev.olog.feature.entry.MainActivity

class FeatureEntryDagger {

    @Subcomponent(modules = [MainActivityModule::class])
    @FeatureScope
    internal interface Graph : AndroidInjector<MainActivity> {

        @Subcomponent.Factory
        interface Factory : AndroidInjector.Factory<MainActivity>

    }

    @Module(subcomponents = [Graph::class])
    abstract class AppModule {

        @Binds
        @IntoMap
        @ClassKey(MainActivity::class)
        internal abstract fun injectorFactory(factory: Graph.Factory): AndroidInjector.Factory<*>

    }


}