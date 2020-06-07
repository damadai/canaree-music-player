package dev.olog.msc

import dev.olog.feature.app.shortcuts.ShortcutsActivity
import dev.olog.feature.service.floating.FloatingWindowService
import dev.olog.feature.service.music.MusicService
import dev.olog.msc.appwidgets.WidgetColored
import dev.olog.feature.entry.MainActivity
import dev.olog.presentation.playlist.chooser.PlaylistChooserActivity
import dev.olog.core.Classes
import org.junit.Assert
import org.junit.Test

class ClassTest {

    @Test
    fun checkClassExistence(){
        // activities
        Assert.assertEquals(Classes.ACTIVITY_MAIN, MainActivity::class.java.name)
        Assert.assertEquals(Classes.ACTIVITY_SHORTCUTS, ShortcutsActivity::class.java.name)
        Assert.assertEquals(Classes.ACTIVITY_PLAYLIST_CHOOSER, PlaylistChooserActivity::class.java.name)
        // services
        Assert.assertEquals(Classes.SERVICE_MUSIC, MusicService::class.java.name)
        Assert.assertEquals(Classes.SERVICE_FLOATING, FloatingWindowService::class.java.name)
        //widgets
        Assert.assertEquals(Classes.WIDGET_COLORED, WidgetColored::class.java.name)
    }

}