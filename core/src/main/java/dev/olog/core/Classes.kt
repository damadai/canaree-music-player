package dev.olog.core

// TODO remove this
object Classes {
    const val ACTIVITY_MAIN = "dev.olog.feature.entry.MainActivity"
    const val ACTIVITY_SHORTCUTS = "dev.olog.feature.app.shortcuts.ShortcutsActivity"
    const val ACTIVITY_PLAYLIST_CHOOSER = "dev.olog.presentation.playlist.chooser.PlaylistChooserActivity"

    const val SERVICE_MUSIC = "dev.olog.feature.service.music.MusicService"
    const val SERVICE_FLOATING = "dev.olog.feature.service.floating.FloatingWindowService"

    const val WIDGET_COLORED = "dev.olog.msc.appwidgets.WidgetColored"

    @JvmStatic
    val widgets: List<Class<*>> by lazy {
        listOf(
            Class.forName(WIDGET_COLORED)
        )
    }

}