package dev.olog.msc.music.service.di

import dagger.BindsInstance
import dagger.Component
import dev.olog.msc.app.AppComponent
import dev.olog.msc.dagger.scope.PerService
import dev.olog.msc.music.service.MusicService
import dev.olog.msc.music.service.notification.NotificationModule

fun MusicService.inject() {
    val coreComponent = AppComponent.coreComponent(application)
    DaggerMusicServiceComponent.factory().create(this, coreComponent)
            .inject(this)
}

@Component(modules = [
    MusicServiceModule::class,
    NotificationModule::class
], dependencies = [AppComponent::class])
@PerService
interface MusicServiceComponent {

    fun inject(instance: MusicService)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance instance: MusicService, component: AppComponent): MusicServiceComponent

    }
}