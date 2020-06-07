package dev.olog.presentation.popup.album

import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentActivity
import dev.olog.domain.MediaId
import dev.olog.domain.entity.track.Album
import dev.olog.domain.entity.track.Song
import dev.olog.domain.interactor.playlist.AddToPlaylistUseCase
import dev.olog.domain.interactor.playlist.GetPlaylistsUseCase
import dev.olog.domain.schedulers.Schedulers
import dev.olog.feature.presentation.base.model.PresentationId
import dev.olog.feature.presentation.base.model.artistPresentationId
import dev.olog.feature.presentation.base.model.presentationId
import dev.olog.feature.presentation.base.model.toDomain
import dev.olog.lib.media.MediaProvider
import dev.olog.presentation.*
import dev.olog.navigation.Navigator
import dev.olog.presentation.popup.AbsPopup
import dev.olog.presentation.popup.AbsPopupListener
import javax.inject.Inject

internal class AlbumPopupListener @Inject constructor(
    activity: FragmentActivity,
    private val navigator: Navigator,
    private val mediaProvider: MediaProvider,
    getPlaylistBlockingUseCase: GetPlaylistsUseCase,
    addToPlaylistUseCase: AddToPlaylistUseCase,
    private val schedulers: Schedulers

) : AbsPopupListener(
    getPlaylistBlockingUseCase = getPlaylistBlockingUseCase,
    addToPlaylistUseCase = addToPlaylistUseCase,
    schedulers = schedulers,
    activity = activity
) {

    private lateinit var album: Album
    private var song: Song? = null

    fun setData(container: View?, album: Album, song: Song?): AlbumPopupListener {
        this.container = container
        this.album = album
        this.song = song
        return this
    }

    private fun getMediaId(): PresentationId {
        if (song != null) {
            return album.presentationId.playableItem(song!!.id)
        } else {
            return album.presentationId
        }
    }

    override fun onMenuItemClick(menuItem: MenuItem): Boolean {

        val itemId = menuItem.itemId

        onPlaylistSubItemClick(activity, itemId, getMediaId(), album.songs, album.title)

        when (itemId) {
            AbsPopup.NEW_PLAYLIST_ID -> toCreatePlaylist()
            R.id.play -> playFromMediaId()
            R.id.playShuffle -> playShuffle()
            R.id.addToFavorite -> addToFavorite()
            R.id.playLater -> playLater()
            R.id.playNext -> playNext()
            R.id.delete -> delete()
            R.id.viewArtist -> viewArtist()
            R.id.viewAlbum -> viewAlbum(navigator, album.presentationId)
            R.id.viewInfo -> viewInfo(navigator, getMediaId())
            R.id.share -> share(activity, song!!)
            R.id.setRingtone -> setRingtone(navigator, getMediaId(), song!!)
//            R.id.addHomeScreen -> AppShortcuts.instance(activity, schedulers) TODO
//                .addDetailShortcut(getMediaId().toDomain(), album.title)
        }

        return true
    }

    private fun toCreatePlaylist() {
        if (song == null) {
            navigator.toCreatePlaylistDialog(getMediaId().toDomain(), album.songs, album.title)
        } else {
            navigator.toCreatePlaylistDialog(getMediaId().toDomain(), -1, song!!.title)
        }
    }

    private fun playFromMediaId() {
        val mediaId = getMediaId().toDomain()
        require(mediaId is MediaId.Track)
        mediaProvider.playFromMediaId(mediaId, null, null)
    }

    private fun playShuffle() {
        val mediaId = getMediaId().toDomain()
        require(mediaId is MediaId.Category)
        mediaProvider.shuffle(mediaId, null)
    }

    private fun playLater() {
        if (song == null) {
            navigator.toPlayLater(getMediaId().toDomain(), album.songs, album.title)
        } else {
            navigator.toPlayLater(getMediaId().toDomain(), -1, song!!.title)
        }
    }

    private fun playNext() {
        if (song == null) {
            navigator.toPlayNext(getMediaId().toDomain(), album.songs, album.title)
        } else {
            navigator.toPlayNext(getMediaId().toDomain(), -1, song!!.title)
        }
    }


    private fun addToFavorite() {
        if (song == null) {
            navigator.toAddToFavoriteDialog(getMediaId().toDomain(), album.songs, album.title)
        } else {
            navigator.toAddToFavoriteDialog(getMediaId().toDomain(), -1, song!!.title)
        }
    }

    private fun delete() {
        if (song == null) {
            navigator.toDeleteDialog(getMediaId().toDomain(), album.songs, album.title)
        } else {
            navigator.toDeleteDialog(getMediaId().toDomain(), -1, song!!.title)
        }
    }

    private fun viewArtist() {
        navigator.toDetailFragment(activity, album.artistPresentationId.toDomain(), container)
    }


}