package dev.olog.feature.library.home

import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import dev.olog.domain.entity.track.Album
import dev.olog.domain.entity.track.Artist
import dev.olog.domain.gateway.track.AlbumGateway
import dev.olog.domain.gateway.track.ArtistGateway
import dev.olog.domain.schedulers.Schedulers
import dev.olog.feature.library.R
import dev.olog.feature.presentation.base.model.presentationId
import dev.olog.shared.coroutines.mapListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class HomeFragmentViewModel @Inject constructor(
    albumGateway: AlbumGateway,
    artistGateway: ArtistGateway,
    schedulers: Schedulers
) : ViewModel() {

    val albumLastPlayed: Flow<List<HomeItemModel.Album>> = albumGateway
        .observeLastPlayed()
        .mapListItem { it.toModel() }

    val artistLastPlayed: Flow<List<HomeItemModel.Artist>> = artistGateway
        .observeLastPlayed()
        .mapListItem { it.toModel() }

    val albumRecentlyAdded: Flow<List<HomeItemModel.Album>> = albumGateway
        .observeRecentlyAdded()
        .mapListItem { it.toModel() }

    val artistRecentlyAdded: Flow<List<HomeItemModel.Artist>> = artistGateway
        .observeRecentlyAdded()
        .mapListItem { it.toModel() }

    val data: Flow<List<HomeModel>> = combine(
        albumLastPlayed.map { group(it, R.layout.item_tab_last_played_album_horizontal_list) },
        artistLastPlayed.map { group(it, R.layout.item_tab_last_played_album_horizontal_list) },
        albumRecentlyAdded.map { group(it, R.layout.item_tab_new_album_horizontal_list) },
        artistRecentlyAdded.map { group(it, R.layout.item_tab_new_artist_horizontal_list) }
    ) { albumLast, artistLast, albumNew, artistNew ->
        albumLast + artistLast + albumNew + artistNew
    }.flowOn(schedulers.cpu)

    private fun group(
        items: List<HomeItemModel>,
        @LayoutRes listId: Int
    ): List<HomeModel> {
        if (items.isEmpty()) {
            return emptyList()
        }
        return listOf(
            HomeModel.Header("test"),
            HomeModel.HorizontalList(listId)
        )
    }

    private fun Album.toModel(): HomeItemModel.Album {
        return HomeItemModel.Album(
            mediaId = this.presentationId,
            title = this.title,
            artist = this.artist
        )
    }

    private fun Artist.toModel(): HomeItemModel.Artist {
        return HomeItemModel.Artist(
            mediaId = this.presentationId,
            name = this.name
        )
    }

}